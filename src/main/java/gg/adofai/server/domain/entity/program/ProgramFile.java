package gg.adofai.server.domain.entity.program;


import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import static javax.persistence.FetchType.LAZY;

@Entity @Table(name = "program_file")
@Getter
public class ProgramFile {

    @Id @GeneratedValue
    @Column(name = "program_file_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "program_update_id")
    private ProgramUpdate programUpdate;

    @NotEmpty private String url;

}
