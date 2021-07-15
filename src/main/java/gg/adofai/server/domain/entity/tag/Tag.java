package gg.adofai.server.domain.entity.tag;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity @Table(name = "tag", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "type", "name" })
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {

    @Id @GeneratedValue
    @Column(name = "tag_id")
    private Long id;

    @NotEmpty private String type;

    @NotEmpty private String name;

    @NotNull
    private Long priority;

    @SuppressWarnings("rawtypes")
    public static Tag createTag(Class type, String name, Long priority) {
        Tag tag = new Tag();
        tag.type = type.getSimpleName();
        tag.name = name;
        tag.priority = priority;

        return tag;
    }

}
