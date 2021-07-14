package gg.adofai.server.domain.entity.level;


import gg.adofai.server.domain.entity.member.Person;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity @Table(name = "song_artist")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SongArtist {

    @Id @GeneratedValue
    @Column(name = "song_artist_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "song_id")
    private Song song;

    public static SongArtist createSongArtist(Person person) {
        SongArtist songArtist = new SongArtist();
        songArtist.person = person;

        return songArtist;
    }

}
