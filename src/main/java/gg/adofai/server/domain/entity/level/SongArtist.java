package gg.adofai.server.domain.entity.level;


import gg.adofai.server.domain.entity.member.Person;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.LAZY;

@Entity @Table(name = "song_artist")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SongArtist {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "song_artist_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "song_id")
    @NotNull private Song song;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "person_id")
    @NotNull private Person person;

    public static SongArtist createSongArtist(Song song, Person person) {
        SongArtist songArtist = new SongArtist();
        songArtist.song = song;
        songArtist.person = person;

        return songArtist;
    }

}
