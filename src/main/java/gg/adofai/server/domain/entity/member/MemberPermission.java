package gg.adofai.server.domain.entity.member;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity @Table(name = "member_permission")
public class MemberPermission {

    @Id @GeneratedValue
    @Column(name = "member_permission_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "permission_id")
    private Permission permission;

}
