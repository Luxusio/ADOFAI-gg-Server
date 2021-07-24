package gg.adofai.server.dto.level;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Getter @ToString
public class LevelDto {

    private final Long id;
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
    public LevelDto(Long id, String title, Double difficulty, Double minBpm, Double maxBpm, Long tiles, Integer comments, Integer likes) {
        this.id = id;
        this.title = title;
        this.difficulty = difficulty;
        this.minBpm = minBpm;
        this.maxBpm = maxBpm;
        this.tiles = tiles;
        this.comments = comments;
        this.likes = likes;
    }

    public void setCreators(List<LevelCreatorDto> creators) {
        if (creators == null) return;
        this.creators.clear();
        this.creators.addAll(creators.stream()
                .map(LevelCreatorDto::getName)
                .collect(Collectors.toList()));
    }

    public void setTags(List<LevelTagsDto> tags) {
        if (tags == null) return;
        this.tags.clear();
        this.tags.addAll(tags.stream()
                .map(LevelTagsDto::getName)
                .collect(Collectors.toList()));
    }

}
