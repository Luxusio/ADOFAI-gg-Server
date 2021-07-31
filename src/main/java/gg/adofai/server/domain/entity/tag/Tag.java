package gg.adofai.server.domain.entity.tag;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity @Table(name = "tag", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "location", "name" })
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    @NotNull @NotEmpty private String location;

    @NotNull @NotEmpty private String name;

    @NotNull
    private Long priority;

    @SuppressWarnings("rawtypes")
    public static Tag createTag(Class location, String name, Long priority) {
        Tag tag = new Tag();
        tag.location = location.getSimpleName();
        tag.name = name;
        tag.priority = priority;

        return tag;
    }

}
