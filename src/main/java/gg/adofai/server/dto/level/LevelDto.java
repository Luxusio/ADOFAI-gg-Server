package gg.adofai.server.dto.level;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;


@Getter @ToString
public class LevelDto {

    private final String title;
    private final Double difficulty;
    private final List<String> creators = new ArrayList<>();

    private final Double minBpm;
    private final Double maxBpm;

    private final Long tiles;
    private final Integer comments;
    private final Integer likes;

    private final List<String> tags = new ArrayList<>();

    @QueryProjection
    public LevelDto(String title, Double difficulty, Double minBpm, Double maxBpm, Long tiles, Integer comments, Integer likes) {
        this.title = title;
        this.difficulty = difficulty;
        this.minBpm = minBpm;
        this.maxBpm = maxBpm;
        this.tiles = tiles;
        this.comments = comments;
        this.likes = likes;
    }

    public void setCreators(List<String> creators) {
        this.creators.clear();
        this.creators.addAll(creators);
    }

    public void setTags(List<String> tags) {
        this.tags.clear();
        this.tags.addAll(tags);
    }

}
