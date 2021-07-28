package gg.adofai.server.repository;

import gg.adofai.server.domain.entity.level.Song;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SongRepository {

    private final EntityManager em;

    public void save(Song song) {
        em.persist(song);
    }

    public Song findOne(Long id) {
        return em.find(Song.class, id);
    }

    public List<Song> findAll() {
        return em.createQuery("select s from Song s", Song.class)
                .getResultList();
    }

    public Song findByName(String name) {
        List<Song> result = findByNames(List.of(name));
        return result.isEmpty() ? null : result.get(0);
    }

    public List<Song> findByNames(List<String> names) {
        return em.createQuery("select s from Song s where s.name = :names", Song.class)
                .setParameter("names", names)
                .getResultList();
    }
}
