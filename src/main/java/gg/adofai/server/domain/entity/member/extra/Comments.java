package gg.adofai.server.domain.entity.member.extra;

import gg.adofai.server.domain.entity.member.Member;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity @Table(name = "comments")
@Getter
public class Comments {

    @Id @GeneratedValue
    @Column(name = "comments_id")
    private Long id;

    @NotEmpty private String location;

    @NotEmpty private Long locationId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    @NotEmpty private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    @NotEmpty private Comments parent;

    @NotEmpty private String text;

    @NotEmpty private LocalDateTime date;

    @NotEmpty private Integer likes;

    @NotEmpty private Integer dislikes;

    @NotEmpty private Long ip;

    @NotEmpty private Boolean deleted;


}
