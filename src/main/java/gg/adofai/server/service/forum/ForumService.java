package gg.adofai.server.service.forum;

import gg.adofai.server.service.forum.dto.ForumLevelDto;
import gg.adofai.server.service.forum.dto.ForumPlayLogDto;
import gg.adofai.server.service.forum.dto.ForumTagDto;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ForumService {

    public static final String BASE_URL = "https://spreadsheets.google.com";
    //public static final String BASE_URL = "https://docs.google.com";

    public static final String KEY_USER = "1PzLHfWmVWJHrBGnNSsLTsdH0ibdk0hB4MpKHET1nkpU";
    public static final String KEY_ADMIN = "1MOz5cmMpYwpBB95DK1Udcti_8eOrswnxWzFurhAz0yg";
// qviz/tq?gid=
    // gviz/tq?gid
    public static final String GID_ADMIN_LEVEL = "739034057";
    public static final String GID_ADMIN_RANKING = "151952522";
    public static final String GID_ADMIN_PP_WORK = "110445676";
    public static final String GID_ADMIN_TAG = "346297182";

    private final WebClient client;

    public ForumService(WebClient.Builder builder) {
        client = builder.baseUrl(BASE_URL).build();
    }

    public void updateAllData() {
        Mono<List<ForumLevelDto>> levelDtoListMono = getData(GID_ADMIN_LEVEL, ForumLevelDto::createForumLevelDto);
        Mono<List<ForumPlayLogDto>> ppWorkDtoListMono = getData(GID_ADMIN_PP_WORK, ForumPlayLogDto::createForumPlayLogDto);
        Mono<List<ForumTagDto>> tagDtoListMono = getData(GID_ADMIN_TAG, ForumTagDto::createForumTagDto);

        List<ForumLevelDto> levelDtoList = levelDtoListMono.block();
        List<ForumPlayLogDto> ppWorkDtoList = ppWorkDtoListMono.block();
        List<ForumTagDto> tagDtoList = tagDtoListMono.block();

    }

//    private List<JSONArray> getData(String key, String gid) {
//        try {
//            String result = client.get()
//                    .uri("tq?key=" + key + "&gid=" + gid)
//                    .retrieve().bodyToMono(String.class)
//                    .block();
//            if (result == null) throw new IOException("failed to get data (key=" + key + ", gid=" + gid + ")");
//            result = GoogleSheetConverter.toSafeJsonString(result);
//            return GoogleSheetConverter.toRowDataList(result);
//        } catch (ParseException | IOException e) {
//            // TODO: use logger
//            e.printStackTrace();
//            return List.of();
//        }
//    }

    public <R> Mono<List<R>> getData(String gid, Function<? super JSONArray, ? extends R> mapper) {
        return client.get()
                //.uri("tq?key=" + ForumService.KEY_ADMIN + "&gid=" + gid)
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
