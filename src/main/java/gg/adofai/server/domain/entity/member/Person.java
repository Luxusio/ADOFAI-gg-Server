package gg.adofai.server.domain.entity.member;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity @Table(name = "person")
@Getter
public class Person {

    @Id @GeneratedValue
    @Column(name = "person_id")
    private Long id;

    @NotEmpty
    private String name;

}
