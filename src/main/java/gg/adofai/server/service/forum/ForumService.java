package gg.adofai.server.service.forum;

import gg.adofai.server.service.forum.dto.ForumLevelDto;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ForumService {

    private static final String BASE_URL = "https://spreadsheets.google.com";
    //const val BASE_URL = "https://docs.google.com"

    private static final String KEY_USER = "1PzLHfWmVWJHrBGnNSsLTsdH0ibdk0hB4MpKHET1nkpU";
    private static final String KEY_ADMIN = "1MOz5cmMpYwpBB95DK1Udcti_8eOrswnxWzFurhAz0yg";

    private static final String GID_ADMIN_LEVEL = "739034057";
    private static final String GID_ADMIN_RANKING = "151952522";
    private static final String GID_ADMIN_PP_WORK = "110445676";
    private static final String GID_ADMIN_TAG = "346297182";

    private final WebClient client;

    public ForumService(WebClient.Builder builder) {
        client = builder.baseUrl(BASE_URL).build();
    }

    public void updateLevelData() {
        List<ForumLevelDto> levelDtoList = getData(KEY_ADMIN, GID_ADMIN_LEVEL).stream()
                .map(ForumLevelDto::createForumLevelDto)
                .collect(Collectors.toList());
    }

    private List<JSONArray> getData(String key, String gid) {
        try {
            String result = client.get()
                    .uri("tq?key=" + key + "&gid=" + gid)
                    .retrieve().bodyToMono(String.class)
                    .block();
            if (result == null) throw new IOException("failed to get data (key=" + key + ", gid=" + gid + ")");
            result = GoogleSheetConverter.toSafeJsonString(result);
            return GoogleSheetConverter.toRowDataList(result);
        } catch (ParseException | IOException e) {
            // TODO: use logger
            e.printStackTrace();
            return List.of();
        }
    }



}
