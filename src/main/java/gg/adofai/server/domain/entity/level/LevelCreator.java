package gg.adofai.server.domain.entity.level;

import gg.adofai.server.domain.entity.member.Person;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

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
    @JoinColumn(name = "person_id")
    @NotEmpty private Person person;

}
