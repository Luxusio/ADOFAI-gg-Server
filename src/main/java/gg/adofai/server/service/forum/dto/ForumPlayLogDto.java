package gg.adofai.server.service.forum.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.simple.JSONArray;
import org.springframework.util.NumberUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static gg.adofai.server.service.forum.JsonConverter.safe;
import static gg.adofai.server.service.forum.PrimaryTypeConverter.safeDouble;
import static gg.adofai.server.service.forum.PrimaryTypeConverter.toStringList;

@Data @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ForumPlayLogDto {

    private Long id;
    private LocalDateTime timeStamp;
    private String name;
    private Long userCode;
    private Long mapId;
    private String song;
    private List<String> artists = new ArrayList<>();
    private List<String> creators = new ArrayList<>();
    private Double level;
    private Long tiles;
    private Double ra;
    private Double accuracy;
    private Long speed;
    private Double pp;
    private Long localRank;
    private Long totalRank;
    private Double weighted;
    private String url;

    public static ForumPlayLogDto createForumPlayLogDto(JSONArray jsonArray) {
        if (jsonArray.get(0) == null) return null;

        ForumPlayLogDto dto = new ForumPlayLogDto();
        dto.id = safe(jsonArray.get(0));
        String timeString = safe(jsonArray.get(1));
        dto.timeStamp = timeString == null ? null : LocalDateTime.parse(timeString, DateTimeFormatter.ofPattern("(yyyy,MM,dd,HH,mm,ss)"));
        dto.name = safe(jsonArray.get(2));
        dto.userCode = safe(jsonArray.get(3));
        dto.mapId = safe(jsonArray.get(4));
        dto.song = safe(jsonArray.get(5));
        dto.artists = toStringList(safe(jsonArray.get(6)));
        dto.creators = safe(jsonArray.get(7));
        dto.level = safeDouble(safe(jsonArray.get(8)));
        dto.tiles = safe(jsonArray.get(9));
        String video = safe(jsonArray.get(10));
        dto.ra = safeDouble(safe(jsonArray.get(11)));
        dto.accuracy = safeDouble(safe(jsonArray.get(12)));
        if (dto.accuracy != null) dto.accuracy *= 100;
        Double doubleSpeed = safeDouble(safe(jsonArray.get(13)));
        if (doubleSpeed != null) dto.speed = (long) (doubleSpeed * 100);
        dto.pp = safe(jsonArray.get(14));
        dto.localRank = safe(jsonArray.get(15));
        dto.totalRank = safe(jsonArray.get(16));
        dto.weighted = safe(jsonArray.get(17));
        // reserved = safe(jsonArray.get(18));
        dto.url = safe(jsonArray.get(19));

        return dto;
    }

}
