package gg.adofai.server.domain.entity.level;

import gg.adofai.server.domain.entity.member.Person;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static javax.persistence.FetchType.LAZY;

@Entity @Table(name = "level")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Level {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "level_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "song_id")
    private Song song;

    @Column(length = 1024)
    @NotNull private String title;

    @Column(length = 1024)
    @NotNull private String description;

    @NotNull private Double difficulty;

    @NotNull private Double detailDifficulty;

    @NotNull private Long tile;

    @NotNull private Boolean epilepsyWarning;

    @Column(length = 1024)
    @NotNull @NotEmpty private String video;

    @Column(length = 1024)
    @NotNull @NotEmpty private String file;

    @Column(length = 1024)
    private String workshop;

    @NotNull private Boolean isCensored;

    @NotNull private Boolean isDeleted;

    @NotNull private LocalDateTime date;

    @NotNull private LocalDateTime lastUpdate;

    @NotNull private Integer look;

    @NotNull private Integer likes;

    @NotNull private Integer dislikes;

    @NotNull private Integer comments;

    // TODO : change max length of strings. for example) length=65536

    @OneToMany(mappedBy = "level", cascade = CascadeType.ALL)
    private List<LevelCreator> levelCreators = new ArrayList<>();

    public static Level createLevel(Song song, String title, String description, Double difficulty, Double detailDifficulty,
                 Long tile, Boolean epilepsyWarning, String video, String file, String workshop, Boolean isCensored, Boolean isDeleted,
                 LocalDateTime date, LocalDateTime lastUpdate,
                 Integer look, Integer likes, Integer dislikes, Integer comments, List<Person> levelCreators) {
        Level level = new Level();

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
        level.isCensored = isCensored;
        level.isDeleted = isDeleted;
        level.date = date;
        level.lastUpdate = lastUpdate;
        level.look = look;
        level.likes = likes;
        level.dislikes = dislikes;
        level.comments = comments;
        level.levelCreators.addAll(levelCreators.stream()
                .map(creator-> LevelCreator.createLevelCreator(level, creator))
                .collect(Collectors.toList()));

        return level;
    }

    public static Level createDeletedLevel() {
        return createLevel(null, "", "", 0.0, 0.0, 0L, false, "-", "-", null, true, true,
                LocalDateTime.now(), LocalDateTime.now(), 0, 0, 0, 0, List.of());
    }

}
