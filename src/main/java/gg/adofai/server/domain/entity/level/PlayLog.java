package gg.adofai.server.domain.entity.level;


import gg.adofai.server.domain.entity.member.Member;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity @Table(name = "play_log")
@Getter
public class PlayLog {

    @Id @GeneratedValue
    @Column(name = "play_log_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    @NotEmpty private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "level_id")
    @NotEmpty private Level level;

    @NotEmpty private LocalDateTime date;

    @NotEmpty private Integer speed;

    @NotEmpty private Double accuracy;

    @NotEmpty private Double playPoint;

    @NotEmpty private String url;

    @NotEmpty private String description;

    @NotEmpty private Boolean accept;

    @NotEmpty private Integer looks;

    @NotEmpty private Integer likes;

    @NotEmpty private Integer dislikes;

}
