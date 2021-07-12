package gg.adofai.server.domain.entity.level;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import static javax.persistence.FetchType.LAZY;

@Entity @Table(name = "standard_level")
@Getter
public class StandardLevel {

    @Id @GeneratedValue
    @Column(name = "standard_level_id")
    private Long id;

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "level_id")
    @NotEmpty private Level level;

    @NotEmpty private Boolean easiestLevel;

    @NotEmpty private Double difficulty;

    @NotEmpty private Integer idx;

}
