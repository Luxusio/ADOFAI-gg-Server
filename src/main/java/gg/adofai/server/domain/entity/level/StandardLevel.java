package gg.adofai.server.domain.entity.level;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.LAZY;

@Entity @Table(name = "standard_level")
@Getter
public class StandardLevel {

    @Id @GeneratedValue
    @Column(name = "standard_level_id")
    private Long id;

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "level_id")
    @NotNull private Level level;

    @NotNull private Boolean easiestLevel;

    @NotNull private Double difficulty;

    @NotNull private Integer idx;

}
