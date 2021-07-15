package gg.adofai.server.domain.entity.level;


import gg.adofai.server.domain.entity.member.Person;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity @Table(name = "play_log")
@Getter
public class PlayLog {

    @Id @GeneratedValue
    @Column(name = "play_log_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "person_id")
    @NotNull
    private Person person;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "level_id")
    @NotNull private Level level;

    @NotNull private LocalDateTime date;

    @NotNull private Integer speed;

    @NotNull private Double accuracy;

    @NotNull private Double playPoint;

    @NotEmpty private String url;

    @NotNull private String description;

    @NotNull private Boolean accept;

    @NotNull private Integer looks;

    @NotNull private Integer likes;

    @NotNull private Integer dislikes;

    public static PlayLog createPlayLog(Person person, Level level, LocalDateTime date, Integer speed, Double accuracy, Double playPoint, String url, String description, Boolean accept) {
        PlayLog playLog = new PlayLog();
        playLog.person = person;
        playLog.level = level;
        playLog.date = date;
        playLog.speed = speed;
        playLog.accuracy = accuracy;
        playLog.playPoint = playPoint;
        playLog.url = url;
        playLog.description = description;
        playLog.accept = accept;
        playLog.looks = 0;
        playLog.likes = 0;
        playLog.dislikes = 0;

        return playLog;
    }

}
