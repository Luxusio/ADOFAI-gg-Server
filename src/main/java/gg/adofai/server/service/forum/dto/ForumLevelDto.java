package gg.adofai.server.service.forum.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static gg.adofai.server.service.forum.JsonConverter.safe;
import static gg.adofai.server.service.forum.PrimaryTypeConverter.safeDouble;
import static gg.adofai.server.service.forum.PrimaryTypeConverter.toStringList;

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
        dto.id = safe(jsonArray.get(0));
        dto.song = safe(jsonArray.get(1));
        dto.artists = toStringList(safe(jsonArray.get(2)));
        // localLevel = get(jsonArray.get(3), "v");
        dto.creators = toStringList(safe(jsonArray.get(4)));
        // download = get(jsonArray.get(5), "v");
        // video = get(jsonArray.get(6), "v");
        // workshop = get(jsonArray.get(7), "v");
        String epilepsyWarningStr = safe(jsonArray.get(8));
        dto.epilepsyWarning = epilepsyWarningStr == null ? null : !epilepsyWarningStr.equals("X");

        String bpmString = safe(jsonArray.get(9));
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

        dto.tiles = safe(jsonArray.get(10));

        dto.tags = Stream.of(
                safe(jsonArray.get(11)),
                safe(jsonArray.get(12)),
                safe(jsonArray.get(13)),
                safe(jsonArray.get(14)),
                safe(jsonArray.get(15)))
                .filter(Objects::nonNull)
                .map(o-> (String) o)
                .map(o-> o.charAt(0) == '#' ? o.substring(1) : o)
                .collect(Collectors.toList());

        dto.level = safeDouble(safe(jsonArray.get(16)));
        dto.download = safe(jsonArray.get(17));
        dto.workshop = safe(jsonArray.get(18));
        dto.video = safe(jsonArray.get(19));
        //reserved = get(jsonArray.get(20));
        //discord = get(jsonArray.get(21));

        return dto;
    }




}
