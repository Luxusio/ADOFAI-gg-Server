package gg.adofai.server.domain.entity.level;

import gg.adofai.server.domain.entity.member.Member;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity @Table(name = "level_request")
@Getter
public class LevelRequest {

    @Id @GeneratedValue
    @Column(name = "level_request_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "level_id")
    private Level level;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private Double expectDifficulty;

    @NotEmpty private String comment;

    @NotEmpty private LocalDateTime date;

}
