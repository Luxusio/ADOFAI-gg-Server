package gg.adofai.server.domain.entity.level;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity @Table(name = "level")
@Getter
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

}
