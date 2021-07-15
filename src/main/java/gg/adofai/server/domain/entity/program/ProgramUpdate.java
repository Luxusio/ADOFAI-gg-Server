package gg.adofai.server.domain.entity.program;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
    @NotNull
    private Program program;

    @NotEmpty private String version;

    @NotNull private String description;

    @NotNull private LocalDateTime date;

    @NotNull private Integer likes;

    @NotNull private Integer dislikes;

}
