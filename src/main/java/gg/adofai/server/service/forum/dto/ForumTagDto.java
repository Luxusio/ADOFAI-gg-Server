package gg.adofai.server.service.forum.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.simple.JSONArray;

import static gg.adofai.server.service.forum.JsonConverter.safe;

@Data @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ForumTagDto {

    private String name;

    public static ForumTagDto createForumTagDto(JSONArray jsonArray) {
        if (jsonArray.get(1) == null) return null;

        ForumTagDto dto = new ForumTagDto();
        dto.name = safe(jsonArray.get(1));

        return dto;
    }

}
