package gg.adofai.server.domain.entity.member;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity @Table(name = "login_log")
public class LoginLog {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "login_log_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    @NotNull private Member member;

    @NotNull private LocalDateTime date;

    @NotNull private Long ip;

    private String way;

}
