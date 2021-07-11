package gg.adofai.server.domain.entity.member;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity @Table(name = "login_log")
public class LoginLog {

    @Id @GeneratedValue
    @Column(name = "login_log_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @NotEmpty
    private LocalDateTime date;

    @NotEmpty private Long ip;

    private String way;

}
