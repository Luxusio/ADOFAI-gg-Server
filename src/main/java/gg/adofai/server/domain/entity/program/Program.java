package gg.adofai.server.domain.entity.program;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "program")
@Getter
public class Program {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "program_id")
    private Long id;

    @NotEmpty private String title;

    @NotNull
    private String simpleDescription;

    @NotNull private String description;

    @NotNull private LocalDateTime date;

    @NotNull private LocalDateTime lastEdit;

    @NotNull private Integer looks;

    @NotNull private Integer likes;

    @NotNull private Integer dislikes;

    @NotNull private Integer comments;

    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProgramCreator> programCreators = new ArrayList<>();

    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProgramUpdate> programUpdates = new ArrayList<>();

}
