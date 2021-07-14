package gg.adofai.server.domain.entity.level;

import gg.adofai.server.domain.entity.member.Person;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static javax.persistence.FetchType.LAZY;

@Entity @Table(name = "level")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Level {

    @Id @GeneratedValue
    @Column(name = "level_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "song_id")
    private Song song;

    @NotEmpty
    private String title;

    @NotEmpty
    private String description;

    @NotEmpty
    private Double difficulty;

    @NotEmpty
    private Double detailDifficulty;

    @NotEmpty
    private Long tile;

    @NotEmpty
    private Boolean epilepsyWarning;

    @NotEmpty
    private String video;

    @NotEmpty
    private String file;

    private String workshop;

    @NotEmpty
    private Boolean isShown;

    @NotEmpty private LocalDateTime date;

    @NotEmpty private LocalDateTime lastUpdate;

    @NotEmpty
    private Integer look;

    @NotEmpty
    private Integer likes;

    @NotEmpty
    private Integer dislikes;

    @NotEmpty
    private Integer comments;

    @OneToMany(mappedBy = "level", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LevelCreator> levelCreators = new ArrayList<>();

    public static Level createLevel(Long id, Song song, String title, String description, Double difficulty, Double detailDifficulty,
                 Long tile, Boolean epilepsyWarning, String video, String file, String workshop, Boolean isShown,
                 LocalDateTime date, LocalDateTime lastUpdate,
                 Integer look, Integer likes, Integer dislikes, Integer comments, List<Person> levelCreators) {
        Level level = new Level();

        level.id = id;
        level.song = song;
        level.title = title;
        level.description = description;
        level.difficulty = difficulty;
        level.detailDifficulty = detailDifficulty;
        level.tile = tile;
        level.epilepsyWarning = epilepsyWarning;
        level.video = video;
        level.file = file;
        level.workshop = workshop;
        level.isShown = isShown;
        level.date = date;
        level.lastUpdate = lastUpdate;
        level.look = look;
        level.likes = likes;
        level.dislikes = dislikes;
        level.comments = comments;
        level.levelCreators.addAll(levelCreators.stream().map(LevelCreator::createLevelCreator).collect(Collectors.toList()));

        return level;
    }

}
