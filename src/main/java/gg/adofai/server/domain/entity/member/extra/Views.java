package gg.adofai.server.domain.entity.member.extra;

import gg.adofai.server.domain.entity.member.Member;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity @Table(name = "views")
@Getter
public class Views {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "views_id")
    private Long id;

    @NotNull @NotEmpty private String location;

    @NotNull private Long locationId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @NotNull private LocalDateTime date;

    @NotNull private Integer likes;

    @NotNull
    private Long ip;


}
