package gg.adofai.server.domain.entity.level;

import gg.adofai.server.domain.entity.member.MemberPermission;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "song")
@Getter
public class Song {

    @Id @GeneratedValue
    @Column(name = "song_id")
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty private Double minBpm;

    @NotEmpty private Double maxBpm;

    @OneToMany(mappedBy = "song", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SongArtist> artists = new ArrayList<>();

}
