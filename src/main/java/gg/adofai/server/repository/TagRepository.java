package gg.adofai.server.repository;

import gg.adofai.server.domain.entity.tag.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TagRepository {

    private final EntityManager em;

    public void save(Tag tag) {
        em.persist(tag);
    }

    public Tag findOne(Long id) {
        return em.find(Tag.class, id);
    }

    public List<Tag> findAll() {
        return em.createQuery("select t from Tag t", Tag.class)
                .getResultList();
    }

    public Tag findByName(String name) {
        List<Tag> result = findByNames(List.of(name));
        return result.isEmpty() ? null : result.get(0);
    }

    public List<Tag> findByNames(List<String> names) {
        return em.createQuery("select t from Tag t where t.name = :names", Tag.class)
                .setParameter("names", names)
                .getResultList();
    }

}
