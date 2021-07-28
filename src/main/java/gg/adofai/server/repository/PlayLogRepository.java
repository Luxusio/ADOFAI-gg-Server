package gg.adofai.server.repository;

import gg.adofai.server.domain.entity.level.PlayLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PlayLogRepository {

    private final EntityManager em;

    public void save(PlayLog song) {
        em.persist(song);
    }

    public PlayLog findOne(Long id) {
        return em.find(PlayLog.class, id);
    }

    public List<PlayLog> findAll() {
        return em.createQuery("select p from PlayLog p", PlayLog.class)
                .getResultList();
    }

    public List<PlayLog> findByPlayerName(String name) {
        return em.createQuery(
        "select pl from PlayLog pl" +
                " fetch join pl.person p" +
                " where p.name = :name", PlayLog.class)
                .setParameter("name", name)
                .getResultList();
    }

    public List<PlayLog> findByLevelId(Long id) {
        return em.createQuery(
                "select pl from PlayLog pl" +
                        " fetch join pl.level l" +
                        " where l.level_id = :levelId", PlayLog.class)
                .setParameter("levelId", id)
                .getResultList();
    }

}
