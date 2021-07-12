package gg.adofai.server.domain.entity.member.extra;

import gg.adofai.server.domain.entity.member.Member;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity @Table(name = "views")
@Getter
public class Views {

    @Id @GeneratedValue
    @Column(name = "views_id")
    private Long id;

    @NotEmpty private String location;

    @NotEmpty private Long locationId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @NotEmpty private LocalDateTime date;

    @NotEmpty private Integer likes;

    @NotEmpty private Long ip;


}
