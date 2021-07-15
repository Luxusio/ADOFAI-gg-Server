package gg.adofai.server.service.forum;

import gg.adofai.server.domain.entity.level.Level;
import gg.adofai.server.domain.entity.level.PlayLog;
import gg.adofai.server.domain.entity.level.Song;
import gg.adofai.server.domain.entity.member.Person;
import gg.adofai.server.domain.entity.tag.Tag;
import gg.adofai.server.domain.entity.tag.Tags;
import gg.adofai.server.repository.*;
import gg.adofai.server.service.forum.dto.ForumLevelDto;
import gg.adofai.server.service.forum.dto.ForumPlayLogDto;
import gg.adofai.server.service.forum.dto.ForumTagDto;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.validation.UnexpectedTypeException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static gg.adofai.server.service.forum.PrimaryTypeConverter.safeInteger;
import static gg.adofai.server.service.forum.PrimaryTypeConverter.safeValue;

@Service
@Transactional
public class ForumService {

    public static final String BASE_URL = "https://spreadsheets.google.com";

    public static final String KEY_USER = "1PzLHfWmVWJHrBGnNSsLTsdH0ibdk0hB4MpKHET1nkpU";
    public static final String KEY_ADMIN = "1MOz5cmMpYwpBB95DK1Udcti_8eOrswnxWzFurhAz0yg";

    public static final String GID_ADMIN_LEVEL = "739034057";
    public static final String GID_ADMIN_RANKING = "151952522";
    public static final String GID_ADMIN_PP_WORK = "110445676";
    public static final String GID_ADMIN_TAG = "346297182";

    private final InitRepository initRepository;
    private final PersonRepository personRepository;
    private final SongRepository songRepository;
    private final LevelRepository levelRepository;
    private final TagRepository tagRepository;
    private final TagsRepository tagsRepository;
    private final PlayLogRepository playLogRepository;

    private final WebClient client;

    // Problem : all table uses same auto_increment value. we need to fix it.

    public ForumService(InitRepository initRepository, PersonRepository personRepository, SongRepository songRepository,
                        LevelRepository levelRepository, TagRepository tagRepository, TagsRepository tagsRepository,
                        PlayLogRepository playLogRepository,
                        WebClient.Builder builder) {
        this.initRepository = initRepository;
        this.personRepository = personRepository;
        this.songRepository = songRepository;
        this.levelRepository = levelRepository;
        this.tagRepository = tagRepository;
        this.tagsRepository = tagsRepository;
        this.playLogRepository = playLogRepository;

        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(configure -> configure.defaultCodecs().maxInMemorySize(-1)).build();

        client = builder.exchangeStrategies(exchangeStrategies)
                .baseUrl(BASE_URL).build();
    }

    public void updateAllData() {
        Mono<List<ForumLevelDto>> levelDtoListMono = getData(GID_ADMIN_LEVEL, ForumLevelDto::createForumLevelDto);
        Mono<List<ForumPlayLogDto>> ppWorkDtoListMono = getData(GID_ADMIN_PP_WORK, ForumPlayLogDto::createForumPlayLogDto);
        Mono<List<ForumTagDto>> tagDtoListMono = getData(GID_ADMIN_TAG, ForumTagDto::createForumTagDto);

        List<ForumLevelDto> levelDtoList = safeValue(levelDtoListMono.block(), List.of());
        List<ForumPlayLogDto> ppWorkDtoList = safeValue(ppWorkDtoListMono.block(), List.of());
        List<ForumTagDto> tagDtoList = safeValue(tagDtoListMono.block(), List.of());

        if (levelDtoList.isEmpty()) throw new RuntimeException("levelDtoList is null!!");
        if (ppWorkDtoList.isEmpty()) throw new RuntimeException("ppWorkDtoList is null!!");
        if (tagDtoList.isEmpty()) throw new RuntimeException("tagDtoList is null!!");

        // reset database
        initRepository.resetDB();

        // add person
        Map<String, String> nameConvertMap = new ConcurrentHashMap<>();
        Stream.concat(
                levelDtoList.stream().flatMap(forumLevelDto -> Stream.concat (
                        forumLevelDto.getArtists().stream(),
                        forumLevelDto.getCreators().stream())),
                ppWorkDtoList.stream().map(ForumPlayLogDto::getName)
        ).filter(s -> {
            if (nameConvertMap.containsKey(s.toLowerCase())) return false;
            nameConvertMap.put(s.toLowerCase(), s);
            return true;
        }).map(Person::createPerson)
                .forEach(personRepository::save);

        // add tag
        Stream.concat(
                levelDtoList.stream().flatMap(forumLevelDto -> forumLevelDto.getTags().stream()),
                tagDtoList.stream().map(ForumTagDto::getName)
        ).distinct().map(name->Tag.createTag(Level.class, name, 1L))
                .forEach(tagRepository::save);

        // add song
        levelDtoList.stream()
                .collect(Collectors.groupingBy(ForumLevelDto::getSong)).entrySet().stream()
                .map(listEntry -> {
                    String song = safeValue(listEntry.getKey(), "");
                    ForumLevelDto forumLevelDto = listEntry.getValue().get(0);

                    Double minBpm = safeValue(forumLevelDto.getMinBpm(), 0.0);
                    Double maxBpm = safeValue(forumLevelDto.getMaxBpm(), 0.0);
                    List<String> artistNames = listEntry.getValue().stream()
                            .flatMap(dto -> dto.getArtists().stream())
                            .map(name->nameConvertMap.get(name.toLowerCase()))
                            .distinct().collect(Collectors.toList());

                    List<Person> artists = personRepository.findByNames(artistNames);
                    return Song.createSong(song, minBpm, maxBpm, artists);
                }).forEach(songRepository::save);

        // add level
        levelDtoList.stream().map(forumLevelDto -> {
            Song song = songRepository.findByName(safeValue(forumLevelDto.getSong(), ""));
            List<Person> levelCreators = personRepository.findByNames(forumLevelDto.getCreators());

            return Level.createLevel(
                    forumLevelDto.getId(), song, forumLevelDto.getSong() , "", forumLevelDto.getLevel(),
                    0.0, safeValue(forumLevelDto.getTiles(), 0L), forumLevelDto.getEpilepsyWarning(),
                    safeValue(forumLevelDto.getVideo(), " "),
                    safeValue(forumLevelDto.getDownload(), " "),
                    forumLevelDto.getWorkshop(), true, LocalDateTime.now(),
                    LocalDateTime.now(), 0, 0, 0, 0, levelCreators);
        }).forEach(levelRepository::save);

        // add level tag
        levelDtoList.stream().flatMap(forumLevelDto -> {
            List<Tag> tags = tagRepository.findByNames(forumLevelDto.getTags());
            return tags.stream().map(tag -> Tags.createTags(tag, forumLevelDto.getId()));
        }).forEach(tagsRepository::save);

        // add play log
        Map<String, Person> playerMap = personRepository.findByNames(
                ppWorkDtoList.stream()
                        .map(ForumPlayLogDto::getName)
                        .collect(Collectors.toList())).stream()
                .collect(Collectors.toMap(Person::getName, p->p));

        Map<Long, Level> levelMap = levelRepository.findAll(
                ppWorkDtoList.stream()
                        .map(ForumPlayLogDto::getMapId)
                        .collect(Collectors.toList())).stream()
                .collect(Collectors.toMap(Level::getId, l->l));

        ppWorkDtoList.stream()
                .map(dto-> {
                    Person player = playerMap.get(nameConvertMap.get(dto.getName().toLowerCase()));
                    Level level = levelMap.get(dto.getMapId());

                    return PlayLog.createPlayLog(player, level,
                            dto.getTimeStamp(), safeInteger(dto.getSpeed()), dto.getAccuracy(), dto.getPp(),
                            dto.getUrl(), "", true);
                }).forEach(playLogRepository::save);

    }

    public <R> Mono<List<R>> getData(String gid, Function<? super JSONArray, ? extends R> mapper) {
        return client.get()
                .uri("spreadsheets/d/" + ForumService.KEY_ADMIN + "/gviz/tq?gid=" + gid)
                .retrieve().bodyToMono(String.class)
                .map(GoogleSheetConverter::toSafeJsonString)
                .map(result-> {
                    try {
                        if (result == null) throw new IOException("failed to get data (key=" + ForumService.KEY_ADMIN + ", gid=" + gid + ")");
                        return GoogleSheetConverter.toRowDataList(result);
                    } catch (ParseException | IOException e) {
                        // TODO: use logger
                        System.err.println("result = " + result);
                        e.printStackTrace();
                        return List.of(new JSONArray());
                    }})
                .map(jsonArrays-> jsonArrays.stream()
                        .map(mapper)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList()));
    }

}
