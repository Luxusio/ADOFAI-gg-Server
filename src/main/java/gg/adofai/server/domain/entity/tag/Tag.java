package gg.adofai.server.domain.entity.tag;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity @Table(name = "tag")
@Getter
public class Tag {

    @Id @GeneratedValue
    @Column(name = "tag_id")
    private Long id;

    @NotEmpty private String type;

    @NotEmpty private String name;

    @NotEmpty private Long priority;

}
