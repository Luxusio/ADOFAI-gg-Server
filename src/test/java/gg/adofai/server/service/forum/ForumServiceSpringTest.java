package gg.adofai.server.service.forum;

import gg.adofai.server.domain.entity.member.Person;
import gg.adofai.server.repository.PersonRepository;
import manifold.ext.rt.api.Jailbreak;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ForumServiceSpringTest {

    @Autowired ForumService forumService;

    @Autowired PersonRepository personRepository;

    @Test
    @Commit
    void testInitDatabase() {
        // given

        // when
        forumService.updateAllData();

        // then
        List<Person> personList = personRepository.findAll();
        assertNotEquals(0, personList.size());

    }


}
