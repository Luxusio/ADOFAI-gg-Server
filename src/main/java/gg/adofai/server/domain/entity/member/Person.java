package gg.adofai.server.domain.entity.member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity @Table(name = "person")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Person {

    @Id @GeneratedValue
    @Column(name = "person_id")
    private Long id;

    @NotEmpty
    @Column(unique = true)
    private String name;

    public static Person createPerson(String name) {
        Person person = new Person();
        person.name = name;

        return person;
    }

}
