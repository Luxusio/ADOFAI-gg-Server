package gg.adofai.server.repository;

import gg.adofai.server.domain.entity.level.Level;
import gg.adofai.server.domain.entity.tag.Tags;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TagsRepository {

    private final EntityManager em;

    public void save(Tags tags) {
        em.persist(tags);
    }

    public Tags findOne(Long id) {
        return em.find(Tags.class, id);
    }

    public List<Tags> findAll() {
        return em.createQuery("select t from Tags t", Tags.class)
                .getResultList();
    }

    public List<Tags> findByLocationNId(Class<Object> klass, Long locationId) {
        return em.createQuery("select t from Tags t where t.location = :location and t.locationId = :locationId", Tags.class)
                .setParameter("location", klass.getSimpleName())
                .setParameter("locationId", locationId)
                .getResultList();
    }

    public List<Tags> findByLocation(Class<Object> klass) {
        return em.createQuery("select t from Tags t where t.location = :location", Tags.class)
                .setParameter("location", klass.getSimpleName())
                .getResultList();
    }

}
