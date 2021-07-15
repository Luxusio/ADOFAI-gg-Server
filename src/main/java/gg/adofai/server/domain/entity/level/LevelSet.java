package gg.adofai.server.domain.entity.level;


import gg.adofai.server.domain.entity.member.Person;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.LAZY;

@Entity @Table(name = "level_set")
@Getter
public class LevelSet {

    @Id @GeneratedValue
    @Column(name = "level_set_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "level_request_id")
    private LevelRequest levelRequest;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "person_id")
    @NotNull private Person person;

    @NotNull private Double setDifficulty;

}
