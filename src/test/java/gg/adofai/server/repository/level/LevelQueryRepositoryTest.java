package gg.adofai.server.repository.level;

import com.querydsl.jpa.impl.JPAQueryFactory;
import gg.adofai.server.domain.entity.level.Level;
import gg.adofai.server.domain.entity.level.Song;
import gg.adofai.server.domain.entity.member.Person;
import gg.adofai.server.domain.entity.tag.Tag;
import gg.adofai.server.domain.entity.tag.Tags;
import gg.adofai.server.domain.vo.level.LevelSearchCondition;
import gg.adofai.server.domain.vo.level.LevelSortOrder;
import gg.adofai.server.dto.level.LevelSearchResultDto;
import gg.adofai.server.repository.InitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static gg.adofai.server.domain.entity.tag.QTag.tag;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class LevelQueryRepositoryTest {

    @Autowired LevelQueryRepository levelQueryRepository;
    @Autowired EntityManager em;
    @Autowired JPAQueryFactory queryFactory;
    @Autowired InitRepository initRepository;

    @BeforeEach
    void initDatabase() {
        initRepository.setTesting(true);
        initRepository.resetDB();
        
        Person camellia = Person.createPerson("Camellia");
        Person plum = Person.createPerson("Plum");

        Person ruren = Person.createPerson("Ruren");
        Person xiZnYng = Person.createPerson("XiZnYng");
        Person bWen = Person.createPerson("BWen");
        Person herny = Person.createPerson("Herny");
        Person optimum_p = Person.createPerson("Optimum_P");
        Person hyun = Person.createPerson("Hyun");

        em.persist(camellia);
        em.persist(plum);
        em.persist(ruren);
        em.persist(xiZnYng);
        em.persist(bWen);
        em.persist(herny);
        em.persist(optimum_p);
        em.persist(hyun);

        Song ns = Song.createSong("[ns]", 222.5, 266.0, List.of(camellia));
        Song secretBoss = Song.createSong("SECRET BOSS", 220.0, 220.0, List.of(camellia));
        Song killerBeast = Song.createSong("KillerBeast", 160.0, 220.0, List.of(camellia));
        Song tripleCross = Song.createSong("Triple Cross", 200.0, 200.0, List.of(hyun));
        Song r = Song.createSong("R", 180.0, 180.0, List.of(plum));
        Song timeline = Song.createSong("timeline", 180.0, 180.0, List.of(plum));
        Song selfmadeDisaster = Song.createSong("Selfmade Disaster", 180.0, 180.0, List.of(plum));

        em.persist(ns);
        em.persist(secretBoss);
        em.persist(killerBeast);
        em.persist(r);
        em.persist(timeline);
        em.persist(selfmadeDisaster);
        em.persist(tripleCross);

        Level levelNs = Level.createLevel(1L, ns, "[ns]", "", 20.0, 2.5, 0L, false, " ", " ", null, false, false,
                LocalDateTime.now(), LocalDateTime.now(), 0, 0, 0, 0, List.of(ruren));
        Level levelSecretBoss = Level.createLevel(2L, secretBoss, "SECRET BOSS", "", 18.0, 2.5, 0L, false, " ", " ", null, false, false,
                LocalDateTime.now(), LocalDateTime.now(), 0, 0, 0, 0, List.of(optimum_p));
        Level levelSecretBossEx = Level.createLevel(3L, secretBoss, "SECRET BOSS (ex)", "", 20.0, 2.5, 0L, false, " ", " ", null, false, false,
                LocalDateTime.now(), LocalDateTime.now(), 0, 0, 0, 0, List.of(xiZnYng));
        Level levelR = Level.createLevel(4L, r, "R", "", 20.0, 2.5, 0L, false, " ", " ", null, false, false,
                LocalDateTime.now(), LocalDateTime.now(), 0, 0, 0, 0, List.of(bWen));
        Level levelKillerBeast = Level.createLevel(5L, killerBeast, "KillerBeast", "", 20.0, 2.5, 0L, false, " ", " ", null, false, false,
                LocalDateTime.now(), LocalDateTime.now(), 0, 0, 0, 0, List.of(bWen));
        Level levelSelfmadeDisaster = Level.createLevel(6L, selfmadeDisaster, "Selfmade Disaster (Hyper ver)", "", 21.0, 2.5, 0L, false, " ", " ", null, false, false,
                LocalDateTime.now(), LocalDateTime.now(), 0, 0, 0, 0, List.of(bWen));
        Level levelSelfmadeDisasterEasy = Level.createLevel(7L, selfmadeDisaster, "Selfmade Disaster (easy ver)", "", 20.0, 2.5, 0L, false, " ", " ", null, false, false,
                LocalDateTime.now(), LocalDateTime.now(), 0, 0, 0, 0, List.of(bWen));
        Level levelTimeline = Level.createLevel(8L, timeline, "timeline", "", 20.0, 2.5, 0L, false, " ", " ", null, false, false,
                LocalDateTime.now(), LocalDateTime.now(), 0, 0, 0, 0, List.of(herny));
        Level levelTripleCross = Level.createLevel(9L, tripleCross, "triple cross", "", 18.0, 2.5, 0L, false, " ", " ", null, false, false,
                LocalDateTime.now(), LocalDateTime.now(), 0, 0, 0, 0, List.of(herny));
        Level levelTripleCrossExtremelyEasy = Level.createLevel(10L, tripleCross, "triple cross", "", 1.0, 2.5, 0L, false, " ", " ", null, false, false,
                LocalDateTime.now(), LocalDateTime.now(), 0, 0, 0, 0, List.of(herny));

        em.persist(levelNs);
        em.persist(levelSecretBoss);
        em.persist(levelSecretBossEx);
        em.persist(levelR);
        em.persist(levelKillerBeast);
        em.persist(levelSelfmadeDisaster);
        em.persist(levelSelfmadeDisasterEasy);
        em.persist(levelTimeline);
        em.persist(levelTripleCross);
        em.persist(levelTripleCrossExtremelyEasy);

        List.of("개인차", "동시치기", "2+동타", "셋잇단", "다섯잇단", "일곱잇단", "폴리리듬", "스윙", "트레실로", "개박",
                "64+비트", "가감속", "질주", "마법진", "암기", "1분이하", "4분이상", "흰토끼", "배속변경X", "소용돌이X")
                .forEach(tagName->em.persist(Tag.createTag(Level.class, tagName, 0L)));

        autoPersistTags(levelNs, "개박", "암기");
        autoPersistTags(levelSecretBoss, "개인차", "암기", "동시치기");
        autoPersistTags(levelSecretBossEx, "암기", "동시치기", "4분이상");
        autoPersistTags(levelR, "셋잇단", "마법진");
        autoPersistTags(levelKillerBeast, "암기", "질주", "동시치기", "셋잇단", "폴리리듬");
        autoPersistTags(levelSelfmadeDisaster, "질주", "마법진", "64+비트");
        autoPersistTags(levelSelfmadeDisasterEasy, "질주");
        autoPersistTags(levelTimeline, "동시치기", "2+동타", "마법진");
        autoPersistTags(levelTripleCross, "질주");
    }

    private void autoPersistTags(Level level, String... args) {
        long levelId = level.getId();
        queryFactory.selectFrom(tag)
                .where(tag.name.in(args))
                .fetch().stream()
                .map(resultTag -> Tags.createTags(resultTag, levelId))
                .forEach(em::persist);
    }


    private LevelSearchCondition createBaseSearchCondition() {
        LevelSearchCondition condition = new LevelSearchCondition();
        condition.setOffset(0L);
        condition.setAmount(50L);
        return condition;
    }

    @Test
    void testSearchByTag() {
        LevelSearchCondition condition = createBaseSearchCondition();
        condition.setTags(List.of("동시치기", "셋잇단"));

        LevelSearchResultDto resultDto = levelQueryRepository.searchLevel(condition);

        assertTrue(resultDto.getCount() > 0, "Tag result should exist");
    }

    @Test
    void testSearchByQuery() {
        LevelSearchCondition condition = createBaseSearchCondition();
        condition.setQuery("Camellia");
        LevelSearchCondition condition2 = createBaseSearchCondition();
        condition2.setQuery("SECRET BOSS");
        LevelSearchCondition condition3 = createBaseSearchCondition();
        condition3.setQuery("BWen");

        LevelSearchResultDto resultDto = levelQueryRepository.searchLevel(condition);
        LevelSearchResultDto resultDto2 = levelQueryRepository.searchLevel(condition2);
        LevelSearchResultDto resultDto3 = levelQueryRepository.searchLevel(condition3);

        assertTrue(resultDto.getCount() > 0, "camellia artist should exist");
        assertTrue(resultDto2.getCount() > 0, "secret boss level name should exist");
        resultDto2.getResults().forEach(levelDto -> assertTrue(levelDto.getTitle().toLowerCase().contains("secret boss"),
                "level name should ONLY contain secret boss"));
        assertTrue(resultDto3.getCount() > 0, "creator bWen should exists");
    }

    @Test
    void testSearchByLevel() {
        LevelSearchCondition condition = createBaseSearchCondition();
        condition.setMinDifficulty(21.0);
        LevelSearchCondition condition2 = createBaseSearchCondition();
        condition2.setMaxDifficulty(1.0);

        LevelSearchResultDto resultDto = levelQueryRepository.searchLevel(condition);
        LevelSearchResultDto resultDto2 = levelQueryRepository.searchLevel(condition2);

        assertNotEquals(0, resultDto.getCount());
        resultDto.getResults().forEach(levelDto -> assertEquals(21, levelDto.getDifficulty(),
                "difficulty should be only 21"));

        assertNotEquals(0, resultDto2.getCount());
        resultDto2.getResults().forEach(levelDto -> assertEquals(1.0, levelDto.getDifficulty(),
                "difficulty should be only 1.0"));
    }

    @Test
    void testSearchByOffsetAmount() {
        LevelSearchCondition condition = createBaseSearchCondition();
        condition.setOffset(5L);
        condition.setAmount(4L);

        LevelSearchResultDto resultDto = levelQueryRepository.searchLevel(condition);
        assertNotEquals(0, resultDto.getCount());
        assertEquals(4, resultDto.getResults().size());
    }

    @Test
    void testSearchByBpm() {
        LevelSearchCondition condition = createBaseSearchCondition();
        condition.setMaxBpm(200.0);
        LevelSearchCondition condition2 = createBaseSearchCondition();
        condition2.setMinBpm(200.0);
        LevelSearchCondition condition3 = createBaseSearchCondition();
        condition3.setMinBpm(200.0);
        condition3.setMaxBpm(200.0);

        LevelSearchResultDto resultDto = levelQueryRepository.searchLevel(condition);
        LevelSearchResultDto resultDto2 = levelQueryRepository.searchLevel(condition2);
        LevelSearchResultDto resultDto3 = levelQueryRepository.searchLevel(condition3);

        assertNotEquals(0, resultDto.getCount());
        resultDto.getResults().forEach(levelDto -> assertTrue(200 >= levelDto.getMaxBpm(),
                "max bpm should be equal or lower than 200"));

        assertNotEquals(0, resultDto2.getCount());
        resultDto2.getResults().forEach(levelDto -> assertTrue(200 <= levelDto.getMinBpm(),
                "min bpm should be equal or higher than 200"));

        assertNotEquals(0, resultDto3.getCount());
        resultDto3.getResults().forEach(levelDto -> {
            assertEquals(200, levelDto.getMinBpm(),
                    "min bpm should be equal to 200 (" + levelDto + ")");
            assertEquals(200, levelDto.getMaxBpm(),
                    "max bpm should be equal to 200 (" + levelDto + ")");
        });
    }

    @Test
    void testSearchOrder() {
        LevelSearchCondition condition = createBaseSearchCondition();
        condition.setSort(LevelSortOrder.DIFFICULTY_DESC);
        LevelSearchCondition condition2 = createBaseSearchCondition();
        condition2.setSort(LevelSortOrder.RECENT_DESC);

        LevelSearchResultDto resultDto = levelQueryRepository.searchLevel(condition);
        LevelSearchResultDto resultDto2 = levelQueryRepository.searchLevel(condition2);

        assertEquals(21, resultDto.getResults().get(0).getDifficulty(), "hardest level is 21");
        assertEquals(10, resultDto2.getResults().get(0).getId(), "recent level's id is 10");
    }

}