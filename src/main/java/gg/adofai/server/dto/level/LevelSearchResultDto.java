package gg.adofai.server.dto.level;

import lombok.Getter;

import java.util.List;

@Getter
public class LevelSearchResultDto {

    private List<LevelDto> results;
    private Long count;

}
