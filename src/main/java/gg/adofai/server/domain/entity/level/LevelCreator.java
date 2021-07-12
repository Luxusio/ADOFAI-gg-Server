package gg.adofai.server.domain.entity.level;

import gg.adofai.server.domain.entity.member.Member;
import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity @Table(name = "level_creator")
@Getter
public class LevelCreator {

    @Id @GeneratedValue
    @Column(name = "level_creator_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "level_id")
    private Level level;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

}
