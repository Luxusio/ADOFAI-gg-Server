package gg.adofai.server.domain.entity.level;

import gg.adofai.server.domain.entity.member.Person;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity @Table(name = "song")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public static Song createSong(String name, Double minBpm, Double maxBpm, List<Person> artists) {
        Song song = new Song();
        song.name = name;
        song.minBpm = minBpm;
        song.maxBpm = maxBpm;
        song.artists.addAll(artists.mapToList(SongArtist::createSongArtist));

        return song;
    }

}
