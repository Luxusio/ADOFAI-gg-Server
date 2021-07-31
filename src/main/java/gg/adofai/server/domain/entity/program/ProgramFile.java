package gg.adofai.server.domain.entity.program;


import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.LAZY;

@Entity @Table(name = "program_file")
@Getter
public class ProgramFile {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "program_file_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "program_update_id")
    @NotNull private ProgramUpdate programUpdate;

    @NotNull @NotEmpty private String url;

}
