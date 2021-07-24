package gg.adofai.server.dto.level;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class LevelTagsDto {

    private final Long levelId;
    private final String name;

    @QueryProjection
    public LevelTagsDto(Long levelId, String name) {
        this.levelId = levelId;
        this.name = name;
    }

}