package gg.adofai.server.dto.level;

import gg.adofai.server.domain.vo.LevelSortOrder;
import lombok.Data;

import java.util.List;

@Data
public class LevelSearchCondition {

    private Long offset;
    private Long amount;

    private String query;

    private LevelSortOrder sort;

    private Double minDifficulty;
    private Double maxDifficulty;

    private Double minBpm;
    private Double maxBpm;

    private Long minTiles;
    private Long maxTiles;

    private Boolean showNotVerified;
    private Boolean showCensored;

    private List<String> tags;

}
