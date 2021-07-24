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
import org.hibernate.jpa.spi.TupleBuilderTransformer;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuple3;
import reactor.util.function.Tuples;

import javax.persistence.Tuple;
import javax.validation.UnexpectedTypeException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
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
        Map<String, Person> personMap;
        Map<String, String> nameConvertMap;
        {
            var result = toPersonMap(levelDtoList, ppWorkDtoList);
            personMap = result.getT1();
            nameConvertMap = result.getT2();
        }
        personMap.forEach((s, person) -> personRepository.save(person));

        // add tag
        Map<String, Tag> tagMap = toTagMap(levelDtoList, tagDtoList);
        tagMap.forEach((s, tag) -> tagRepository.save(tag));

        // add song
        Map<String, Song> songMap = toSongMap(levelDtoList, nameConvertMap, personMap);
        songMap.forEach((s, song) -> songRepository.save(song));

        // add level
        Map<Long, Level> levelMap = toLevelMap(levelDtoList, songMap, nameConvertMap, personMap);
        levelMap.forEach((id, level) -> levelRepository.save(level));

        // add level tag
        toTagsStream(levelDtoList, tagMap).forEach(tagsRepository::save);

        // add play log
        toPlayLogStream(ppWorkDtoList, personMap, nameConvertMap, levelMap).forEach(playLogRepository::save);
    }

    @NotNull
    public Tuple2<Map<String, Person>, Map<String, String>> toPersonMap(List<ForumLevelDto> levelDtoList,
                                                                        List<ForumPlayLogDto> ppWorkDtoList) {

        Map<String, String> nameConvertMap = new HashMap<>();
        Map<String, Person> personMap = Stream.concat(
                levelDtoList.stream().flatMap(forumLevelDto -> Stream.concat(
                        forumLevelDto.getArtists().stream(),
                        forumLevelDto.getCreators().stream())),
                ppWorkDtoList.stream().map(ForumPlayLogDto::getName)
        ).filter(s -> {
            if (nameConvertMap.containsKey(s.toLowerCase())) return false;
            nameConvertMap.put(s.toLowerCase(), s);
            return true;
        }).map(Person::createPerson).collect(Collectors.toMap(Person::getName, p -> p));
        return Tuples.of(personMap, nameConvertMap);
    }

    @NotNull
    private Map<String, Tag> toTagMap(List<ForumLevelDto> levelDtoList, List<ForumTagDto> tagDtoList) {
        return Stream.concat(
                levelDtoList.stream().flatMap(forumLevelDto -> forumLevelDto.getTags().stream()),
                tagDtoList.stream().map(ForumTagDto::getName)
        ).distinct().map(name -> Tag.createTag(Level.class, name, 1L))
                .collect(Collectors.toMap(Tag::getName, t -> t));
    }

    @NotNull
    private Map<String, Song> toSongMap(List<ForumLevelDto> levelDtoList, Map<String, String> nameConvertMap, Map<String, Person> personMap) {
        return levelDtoList.stream()
                .collect(Collectors.groupingBy(dto-> Song.getNameWithArtists(dto.getSong(), dto.getArtists()))).values().stream()
                .map(forumLevelDtoList -> {
                    ForumLevelDto forumLevelDto = forumLevelDtoList.get(0);
                    String song = safeValue(forumLevelDto.getSong(), "");

                    Double minBpm = safeValue(forumLevelDto.getMinBpm(), 0.0);
                    Double maxBpm = safeValue(forumLevelDto.getMaxBpm(), 0.0);
                    List<Person> artists = forumLevelDtoList.stream()
                            .flatMap(dto -> dto.getArtists().stream())
                            .map(name -> nameConvertMap.get(name.toLowerCase()))
                            .distinct().map(personMap::get).collect(Collectors.toList());

                    return Song.createSong(song, minBpm, maxBpm, artists); })
                .collect(Collectors.toMap(Song::getNameWithArtists, s -> s));
    }

    @NotNull
    private Map<Long, Level> toLevelMap(List<ForumLevelDto> levelDtoList, Map<String, Song> songMap, Map<String, String> nameConvertMap, Map<String, Person> personMap) {
        return levelDtoList.stream().map(forumLevelDto -> {
            Song song = songMap.get(Song.getNameWithArtists(
                    safeValue(forumLevelDto.getSong(), ""), forumLevelDto.getArtists()));
            List<Person> levelCreators = forumLevelDto.getCreators().stream()
                    .map(name -> nameConvertMap.get(name.toLowerCase()))
                    .map(personMap::get).collect(Collectors.toList());

            return Level.createLevel(
                    forumLevelDto.getId(), song, forumLevelDto.getSong(), "", forumLevelDto.getLevel(),
                    0.0, safeValue(forumLevelDto.getTiles(), 0L), forumLevelDto.getEpilepsyWarning(),
                    safeValue(forumLevelDto.getVideo(), " "),
                    safeValue(forumLevelDto.getDownload(), " "),
                    forumLevelDto.getWorkshop(), true, LocalDateTime.now(),
                    LocalDateTime.now(), 0, 0, 0, 0, levelCreators);
        }).collect(Collectors.toMap(Level::getId, l->l));
    }

    @NotNull
    private Stream<Tags> toTagsStream(List<ForumLevelDto> levelDtoList, Map<String, Tag> tagMap) {
        return levelDtoList.stream().flatMap(forumLevelDto ->
                forumLevelDto.getTags().stream().distinct()
                        .map(tagMap::get)
                        .map(tag -> Tags.createTags(tag, forumLevelDto.getId())));
    }

    @NotNull
    private Stream<PlayLog> toPlayLogStream(List<ForumPlayLogDto> ppWorkDtoList, Map<String, Person> personMap, Map<String, String> nameConvertMap, Map<Long, Level> levelMap) {
        return ppWorkDtoList.stream()
                .map(dto -> {
                    Person player = personMap.get(nameConvertMap.get(dto.getName().toLowerCase()));
                    Level level = levelMap.get(dto.getMapId());

                    return PlayLog.createPlayLog(player, level,
                            dto.getTimeStamp(), safeInteger(dto.getSpeed()), dto.getAccuracy(), dto.getPp(),
                            dto.getUrl(), "", true);
                });
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
