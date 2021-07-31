package gg.adofai.server.domain.entity.member;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity @Table(name="member")
@Getter
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    @NotNull private Person person;

    @NotNull @NotEmpty private String loginId;

    @NotNull @NotEmpty private String loginPassword;

    @NotNull private String description;

    @NotNull private LocalDateTime registerDate;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberPermission> memberPermissions = new ArrayList<>();

}
