package gg.adofai.server.repository;

import gg.adofai.server.domain.entity.member.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PersonRepository {

    private final EntityManager em;

    public void save(Person person) {
        em.persist(person);
    }

    public Person findOne(Long id) {
        return em.find(Person.class, id);
    }

    public List<Person> findAll() {
        return em.createQuery("select p from Person p", Person.class)
                .getResultList();
    }

    public Person findByName(String name) {
        List<Person> result = findByNames(List.of(name));
        return result.isEmpty() ? null : result.get(0);
    }

    public List<Person> findByNames(List<String> names) {
        return em.createQuery("select p from Person p where p.name in :names", Person.class)
                .setParameter("names", names)
                .getResultList();
    }

}
