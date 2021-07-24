package gg.adofai.server.repository.level;

import com.querydsl.jpa.impl.JPAQueryFactory;
import gg.adofai.server.domain.entity.level.Level;
import gg.adofai.server.domain.entity.level.QLevel;
import gg.adofai.server.domain.entity.level.Song;
import gg.adofai.server.domain.entity.member.Person;
import gg.adofai.server.dto.level.LevelDto;
import gg.adofai.server.dto.level.LevelSearchCondition;
import gg.adofai.server.dto.level.LevelSearchResultDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.List;

import static gg.adofai.server.domain.entity.level.QLevel.level;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class LevelQueryRepositoryTest {

    @Autowired LevelQueryRepository levelQueryRepository;
    @Autowired
    EntityManager em;
    @Autowired
    JPAQueryFactory queryFactory;

    //@BeforeEach
    void initDatabase() {
        Person camellia = Person.createPerson("camellia");
        Person plum = Person.createPerson("plum");

        Person ruren = Person.createPerson("Ruren");
        Person xiZnYng = Person.createPerson("XiZnYng");
        Person bWen = Person.createPerson("BWen");
        Person herny = Person.createPerson("herny");
        Person optimum_p = Person.createPerson("Optimum_P");

        em.persist(camellia);
        em.persist(plum);
        em.persist(ruren);
        em.persist(xiZnYng);
        em.persist(bWen);
        em.persist(herny);

        Song ns = Song.createSong("[ns]", 222.5, 266.0, List.of(camellia));
        Song secretBoss = Song.createSong("SECRET BOSS", 220.0, 220.0, List.of(camellia));
        Song killerBeast = Song.createSong("KillerBeast", 160.0, 220.0, List.of(camellia));

        Song r = Song.createSong("R", 180.0, 180.0, List.of(plum));
        Song timeline = Song.createSong("timeline", 180.0, 180.0, List.of(plum));
        Song selfmadeDisaster = Song.createSong("Selfmade Disaster", 180.0, 180.0, List.of(plum));

        em.persist(ns);
        em.persist(secretBoss);
        em.persist(killerBeast);
        em.persist(r);
        em.persist(timeline);
        em.persist(selfmadeDisaster);

        Level levelNs = Level.createLevel(1L, ns, "[ns]", "", 20.0, 2.5, 0L, false, "", "", null, false, false,
                LocalDateTime.now(), LocalDateTime.now(), 0, 0, 0, 0, List.of(ruren));
        Level levelSecretBoss = Level.createLevel(2L, secretBoss, "SECRET BOSS", "", 18.0, 2.5, 0L, false, "", "", null, false, false,
                LocalDateTime.now(), LocalDateTime.now(), 0, 0, 0, 0, List.of(optimum_p));
        Level levelSecretBossEx = Level.createLevel(3L, secretBoss, "SECRET BOSS (ex)", "", 20.0, 2.5, 0L, false, "", "", null, false, false,
                LocalDateTime.now(), LocalDateTime.now(), 0, 0, 0, 0, List.of(xiZnYng));
        Level levelR = Level.createLevel(4L, r, "R", "", 20.0, 2.5, 0L, false, "", "", null, false, false,
                LocalDateTime.now(), LocalDateTime.now(), 0, 0, 0, 0, List.of(bWen));
        Level levelKillerBeast = Level.createLevel(5L, killerBeast, "KillerBeast", "", 20.0, 2.5, 0L, false, "", "", null, false, false,
                LocalDateTime.now(), LocalDateTime.now(), 0, 0, 0, 0, List.of(bWen));
        Level levelSelfmadeDisaster = Level.createLevel(6L, selfmadeDisaster, "Selfmade Disaster (Hyper ver)", "", 20.0, 2.5, 0L, false, "", "", null, false, false,
                LocalDateTime.now(), LocalDateTime.now(), 0, 0, 0, 0, List.of(bWen));
        Level levelSelfmadeDisasterEasy = Level.createLevel(7L, selfmadeDisaster, "Selfmade Disaster (easy ver)", "", 20.0, 2.5, 0L, false, "", "", null, false, false,
                LocalDateTime.now(), LocalDateTime.now(), 0, 0, 0, 0, List.of(bWen));
        Level levelTimeline = Level.createLevel(8L, timeline, "timeline", "", 20.0, 2.5, 0L, false, "", "", null, false, false,
                LocalDateTime.now(), LocalDateTime.now(), 0, 0, 0, 0, List.of(herny));

        em.persist(levelNs);
        em.persist(levelSecretBoss);
        em.persist(levelSecretBossEx);
        em.persist(levelR);
        em.persist(levelKillerBeast);
        em.persist(levelSelfmadeDisaster);
        em.persist(levelSelfmadeDisasterEasy);
        em.persist(levelTimeline);

    }


    @Test
    void testSearchData() {

        LevelSearchCondition condition = new LevelSearchCondition();
        condition.setOffset(0L);
        condition.setAmount(50L);

        LevelSearchResultDto resultDto = levelQueryRepository.searchLevel(condition);

        System.out.println("resultDto = " + resultDto);
        for (LevelDto result : resultDto.getResults()) {
            System.out.println("result = " + result);
        }
    }

}