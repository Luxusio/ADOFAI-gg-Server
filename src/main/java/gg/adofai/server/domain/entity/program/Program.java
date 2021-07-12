package gg.adofai.server.domain.entity.program;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "program")
@Getter
public class Program {

    @Id @GeneratedValue
    @Column(name = "program_id")
    private Long id;

    @NotEmpty private String title;

    @NotEmpty private String simpleDescription;

    @NotEmpty private String description;

    @NotEmpty private LocalDateTime date;

    @NotEmpty private LocalDateTime lastEdit;

    @NotEmpty private Integer looks;

    @NotEmpty private Integer likes;

    @NotEmpty private Integer dislikes;

    @NotEmpty private Integer comments;

    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProgramCreator> programCreators = new ArrayList<>();

    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProgramUpdate> programUpdates = new ArrayList<>();

}
