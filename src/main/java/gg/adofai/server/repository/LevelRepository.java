package gg.adofai.server.repository;

import gg.adofai.server.domain.entity.level.Level;
import gg.adofai.server.domain.entity.level.Song;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class LevelRepository {

    private final EntityManager em;

    public void save(Level level) {
        em.persist(level);
    }

    public void merge(Level level) {
        em.merge(level);
    }

    public Level findOne(Long id) {
        return em.find(Level.class, id);
    }

    public List<Level> findAll() {
        return em.createQuery("select l from Level l", Level.class)
                .getResultList();
    }

    public List<Level> findAll(List<Long> ids) {
        return em.createQuery("select l from Level l where l.level_id = :ids", Level.class)
                .setParameter("ids", ids)
                .getResultList();
    }

    public Level findByTitle(String title) {
        List<Level> result = findByTitles(List.of(title));
        return result.isEmpty() ? null : result.get(0);
    }

    public List<Level> findByTitles(List<String> titles) {
        return em.createQuery("select l from Level l where l.title = :titles", Level.class)
                .setParameter("titles", titles)
                .getResultList();
    }

}
