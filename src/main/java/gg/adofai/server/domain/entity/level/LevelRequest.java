package gg.adofai.server.domain.entity.level;

import gg.adofai.server.domain.entity.member.Person;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity @Table(name = "level_request")
@Getter
public class LevelRequest {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "level_request_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "level_id")
    @NotNull private Level level;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "person_id")
    @NotNull private Person person;

    private Double expectDifficulty;

    @NotEmpty private String comment;

    @NotNull private LocalDateTime date;

}
