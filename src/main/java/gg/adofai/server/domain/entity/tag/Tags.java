package gg.adofai.server.domain.entity.tag;


import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import static javax.persistence.FetchType.LAZY;

@Entity @Table(name = "tags")
@Getter
public class Tags {

    @Id @GeneratedValue
    @Column(name = "tags_id")
    private Long id;

    @NotEmpty private String location;

    @NotEmpty private Long locationId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

}
