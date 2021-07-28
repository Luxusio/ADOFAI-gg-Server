package gg.adofai.server.repository;

import gg.adofai.server.domain.entity.level.Level;
import gg.adofai.server.domain.entity.tag.Tag;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class InitRepositoryTest {

    @Autowired InitRepository initRepository;

    @Autowired EntityManager em;

    @BeforeEach
    void init() {
        initRepository.setTesting(true);
    }

    @AfterEach
    void finish() {
        initRepository.setTesting(false);
    }

    @Test
    void testResetTable() {
        // given
        em.persist(Tag.createTag(Level.class, "testTag", 0L));

        // when
        initRepository.resetDB();

        // then
        List<Tag> result = em.createQuery("select t from Tag t", Tag.class)
                .getResultList();

        assertEquals(0, result.size());

    }

}