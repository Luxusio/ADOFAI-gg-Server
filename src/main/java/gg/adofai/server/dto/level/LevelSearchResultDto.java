package gg.adofai.server.dto.level;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter @ToString
@AllArgsConstructor
public class LevelSearchResultDto {

    private final List<LevelDto> results;
    private final Long count;

}
