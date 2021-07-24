package gg.adofai.server.domain.entity.tag;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.LAZY;

@Entity @Table(name = "tags", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "location", "locationId", "tag_id" })
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tags {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tags_id")
    private Long id;

    @NotNull private Long locationId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    public static Tags createTags(Tag tag, Long locationId) {
        Tags tags = new Tags();
        tags.tag = tag;
        tags.locationId = locationId;

        return tags;
    }

}
