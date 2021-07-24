package gg.adofai.server.dto.level;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public class LevelCreatorDto {

    private final Long levelId;
    private final String name;

    @QueryProjection
    public LevelCreatorDto(Long levelId, String name) {
        this.levelId = levelId;
        this.name = name;
    }

}
