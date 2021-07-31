package gg.adofai.server.domain.entity.member.extra;

import gg.adofai.server.domain.entity.member.Member;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.LAZY;

@Entity @Table(name = "likes")
@Getter
public class Likes {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "likes_id")
    private Long id;

    @NotNull @NotEmpty private String location;

    @NotNull private Long locationId;

    @NotNull private Boolean isLike;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    @NotNull private Member member;

    @NotNull private Long ip;



}
