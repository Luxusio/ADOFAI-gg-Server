package gg.adofai.server.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class InitRepositoryTest {

    @Autowired InitRepository initRepository;

    @Test
    void testResetTable() {
        // given

        // when
        assertDoesNotThrow(()->initRepository.resetDB());

        // then

    }

}