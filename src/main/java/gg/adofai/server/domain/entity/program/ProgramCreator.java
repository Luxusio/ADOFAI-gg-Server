package gg.adofai.server.domain.entity.program;

import gg.adofai.server.domain.entity.member.Member;
import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity @Table(name = "program_creator")
@Getter
public class ProgramCreator {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "program_creator_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "program_id")
    private Program program;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

}
