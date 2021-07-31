package gg.adofai.server.domain.entity.member.extra;

import gg.adofai.server.domain.entity.member.Member;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity @Table(name = "comments")
@Getter
public class Comments {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comments_id")
    private Long id;

    @NotNull @NotEmpty private String location;

    @NotNull private Long locationId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    @NotNull private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    @NotNull private Comments parent;

    @NotNull @NotEmpty private String text;

    @NotNull private LocalDateTime date;

    @NotNull private Integer likes;

    @NotNull private Integer dislikes;

    @NotNull private Long ip;

    @NotNull private Boolean deleted;


}
