package gg.adofai.server.service.forum;

import gg.adofai.server.domain.entity.member.Person;
import gg.adofai.server.repository.InitRepository;
import gg.adofai.server.repository.PersonRepository;
import manifold.ext.rt.api.Jailbreak;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ForumServiceSpringTest {

    @Autowired ForumService forumService;

    @Autowired PersonRepository personRepository;

    @Autowired InitRepository initRepository;

    @Autowired
    EntityManager em;

    @BeforeEach
    void init() {
        initRepository.setTesting(true);
    }

    @AfterEach
    void finish() {
        initRepository.setTesting(false);
    }


    @Test
    void testInitDatabase() {
        // given

        // when
        forumService.updateAllData();

        // then
        List<Person> personList = personRepository.findAll();
        assertNotEquals(0, personList.size());

    }


}
