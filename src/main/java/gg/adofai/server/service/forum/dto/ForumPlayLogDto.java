package gg.adofai.server.service.forum.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.simple.JSONArray;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static gg.adofai.server.service.forum.JsonConverter.safe;
import static gg.adofai.server.service.forum.PrimaryTypeConverter.*;

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
    private Long levelRank;
    private Long totalRank;
    private Long recordCode;
    private Boolean isOverlapped;
    private Long isNew;
    private Double weighted;
    private String url;

    public static ForumPlayLogDto createForumPlayLogDto(JSONArray jsonArray) {
        if (jsonArray.get(0) == null) return null;

        ForumPlayLogDto dto = new ForumPlayLogDto();
        dto.id = safeLong(safe(jsonArray.get(0)));
        String timeString = safe(jsonArray.get(1));
        if (timeString != null) {
            String[] result = timeString.substring(5, timeString.length() - 1).split(",");
            dto.timeStamp = LocalDateTime.of(
                    Integer.parseInt(result[0]), Integer.parseInt(result[1]) + 1, Integer.parseInt(result[2]),
                    Integer.parseInt(result[3]), Integer.parseInt(result[4]), Integer.parseInt(result[5]));
        }
        dto.name = safe(jsonArray.get(2));
        dto.userCode = safeLong(safe(jsonArray.get(3)));
        dto.mapId = safeLong(safe(jsonArray.get(4)));
        dto.song = safe(jsonArray.get(5));
        dto.artists = toStringList(safe(jsonArray.get(6)));
        dto.creators = toStringList(safe(jsonArray.get(7)));
        dto.level = safeDouble(safe(jsonArray.get(8)));
        dto.tiles = safeLong(safe(jsonArray.get(9)));
        //String video = safe(jsonArray.get(10));
        dto.ra = safeDouble(safe(jsonArray.get(11)));
        dto.accuracy = safeDouble(safe(jsonArray.get(12)));
        if (dto.accuracy != null) dto.accuracy *= 100;
        Double doubleSpeed = safeDouble(safe(jsonArray.get(13)));
        if (doubleSpeed != null) dto.speed = (long) (doubleSpeed * 100);
        dto.pp = safeDouble(safe(jsonArray.get(14)));
        dto.localRank = safeLong(safe(jsonArray.get(15)));
        dto.levelRank = safeLong(safe(jsonArray.get(16)));
        dto.totalRank = safeLong(safe(jsonArray.get(17)));
        dto.recordCode = safeLong(safe(jsonArray.get(18)));
        Long isOverLappedLong = safeLong(safe(jsonArray.get(19)));
        if (isOverLappedLong != null) dto.isOverlapped = isOverLappedLong == 1;
        dto.isNew = safeLong(safe(jsonArray.get(20)));
        dto.weighted = safeDouble(safe(jsonArray.get(21)));
        // reserved = safe(jsonArray.get(22));
        dto.url = safe(jsonArray.get(23));

        return dto;
    }

}
