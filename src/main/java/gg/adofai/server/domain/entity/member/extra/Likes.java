package gg.adofai.server.domain.entity.member.extra;

import gg.adofai.server.domain.entity.member.Member;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import static javax.persistence.FetchType.LAZY;

@Entity @Table(name = "likes")
@Getter
public class Likes {

    @Id @GeneratedValue
    @Column(name = "likes_id")
    private Long id;

    @NotEmpty private String location;

    @NotEmpty private Long locationId;

    @NotEmpty private Boolean isLike;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    @NotEmpty private Member member;

    @NotEmpty private Long ip;



}
