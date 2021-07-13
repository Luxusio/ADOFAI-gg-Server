package gg.adofai.server.service.forum.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ForumLevelDto {

    private Long id;
    private String song;
    private List<String> artists = new ArrayList<>();
    private Double level;
    private List<String> creators = new ArrayList<>();
    private String download;
    private String workshop;
    private String video;
    private Boolean epilepsyWarning;
    private Double minBpm;
    private Double maxBpm;
    private Long tiles;
    private List<String> tags = new ArrayList<>();

    public static ForumLevelDto createForumLevelDto(JSONArray jsonArray) {
        if (jsonArray.get(0) == null) return null;

        ForumLevelDto dto = new ForumLevelDto();
        dto.id = get(jsonArray.get(0));
        dto.song = get(jsonArray.get(1));
        dto.artists = toStringList(get(jsonArray.get(2)));
        // localLevel = get(jsonArray.get(3), "v");
        dto.creators = toStringList(get(jsonArray.get(4)));
        // download = get(jsonArray.get(5), "v");
        // video = get(jsonArray.get(6), "v");
        // workshop = get(jsonArray.get(7), "v");
        String epilepsyWarningStr = get(jsonArray.get(8));
        dto.epilepsyWarning = epilepsyWarningStr == null ? null : !epilepsyWarningStr.equals("X");

        String bpmString = get(jsonArray.get(9));
        if (bpmString != null) {
            int idx = bpmString.indexOf('-');
            if (idx != -1) {
                dto.minBpm = Double.parseDouble(bpmString.substring(0, idx).trim());
                dto.maxBpm = Double.parseDouble(bpmString.substring(idx + 1).trim());
            }
            else {
                dto.minBpm = Double.parseDouble(bpmString);
                dto.maxBpm = dto.minBpm;
            }
        }
        else {
            dto.minBpm = null;
            dto.maxBpm = null;
        }

        dto.tiles = get(jsonArray.get(10));

        dto.tags = Stream.of(
                get(jsonArray.get(11)),
                get(jsonArray.get(12)),
                get(jsonArray.get(13)),
                get(jsonArray.get(14)),
                get(jsonArray.get(15)))
                .filter(Objects::nonNull)
                .map(o-> (String) o)
                .map(o-> o.charAt(0) == '#' ? o.substring(1) : o)
                .collect(Collectors.toList());

        dto.level = safeDouble(get(jsonArray.get(16)));
        dto.download = get(jsonArray.get(17));
        dto.workshop = get(jsonArray.get(18));
        dto.video = get(jsonArray.get(19));
        //reserved = get(jsonArray.get(20));
        //discord = get(jsonArray.get(21));

        return dto;
    }

    private static Double safeDouble(Object obj) {
        if (obj instanceof Integer) {
            return (double) (int) obj;
        }
        if (obj instanceof Long) {
            return (double) (long) obj;
        }
        else if (obj instanceof Float) {
            return (double) (float) obj;
        }
        else if (obj instanceof Double) {
            return (double) obj;
        }
        else {
            // TODO : use logger
            System.err.println("obj is not number! obj = " + obj);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    private static<T> T get(Object obj) {
        JSONObject jsonObject = (JSONObject) obj;
        if (jsonObject == null) return null;
        return (T) jsonObject.get("v");
    }

    private static @NonNull List<String> toStringList(String text)  {
        if (text == null || text.isEmpty()) return List.of("");
        return Arrays.stream(text.split("&"))
                .map(String::trim)
                .collect(Collectors.toList());
    }


}
