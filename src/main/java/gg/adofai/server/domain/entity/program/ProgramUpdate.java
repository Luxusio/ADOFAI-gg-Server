package gg.adofai.server.domain.entity.program;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity @Table(name = "program")
@Getter
public class ProgramUpdate {

    @Id @GeneratedValue
    @Column(name = "program_update_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "program_id")
    @NotEmpty private Program program;

    @NotEmpty private String version;

    @NotEmpty private String description;

    @NotEmpty private LocalDateTime date;

    @NotEmpty private Integer likes;

    @NotEmpty private Integer dislikes;

}
