package gg.adofai.server.domain.entity.member;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity @Table(name = "permission")
@Getter
public class Permission {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permission_id")
    private Long id;

    @NotNull @NotEmpty private String name;

}
