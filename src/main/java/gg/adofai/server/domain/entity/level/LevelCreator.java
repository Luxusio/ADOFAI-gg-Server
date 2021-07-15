package gg.adofai.server.domain.entity.level;

import gg.adofai.server.domain.entity.member.Person;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.LAZY;

@Entity @Table(name = "level_creator")
@Getter
public class LevelCreator {

    @Id @GeneratedValue
    @Column(name = "level_creator_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "level_id")
    @NotNull private Level level;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "person_id")
    @NotNull private Person person;

    public static LevelCreator createLevelCreator(Level level, Person person) {
        LevelCreator levelCreator = new LevelCreator();
        levelCreator.level = level;
        levelCreator.person = person;

        return levelCreator;
    }

}
