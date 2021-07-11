package gg.adofai.server.domain.entity.member;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity @Table(name="member")
@Getter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    @NotEmpty private Person person;

    @NotEmpty private String loginId;

    @NotEmpty private String loginPassword;

    @NotEmpty private String description;

    @NotEmpty private LocalDateTime registerDate;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberPermission> memberPermissions = new ArrayList<>();

}
