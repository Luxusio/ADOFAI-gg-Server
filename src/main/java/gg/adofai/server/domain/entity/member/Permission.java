package gg.adofai.server.domain.entity.member;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity @Table(name = "permission")
@Getter
public class Permission {

    @Id @GeneratedValue
    @Column(name = "permission_id")
    private Long id;

    @NotEmpty private String name;

}
