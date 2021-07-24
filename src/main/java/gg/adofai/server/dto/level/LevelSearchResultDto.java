package gg.adofai.server.dto.level;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class LevelSearchResultDto {

    private final List<LevelDto> results;
    private final Long count;

}
