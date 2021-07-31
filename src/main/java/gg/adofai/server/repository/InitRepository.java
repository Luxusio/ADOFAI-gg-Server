package gg.adofai.server.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class InitRepository {

    private final EntityManager em;
    private boolean isTesting = false;

    public void setTesting(boolean testing) {
        isTesting = testing;
    }

    // https://stackoverflow.com/questions/1912813/truncate-all-tables-in-a-mysql-database-in-one-command
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void resetDB() {

        List resultList = em.createNativeQuery("SHOW TABLES;").getResultList();
        List<String> tables;
        if (resultList.get(0) instanceof String) {
            tables = (List<String>) resultList;
        }
        else {
            tables = ((List<Object[]>)resultList).stream()
                    .map(objects -> objects[0].toString())
                    .collect(Collectors.toList());
        }


        System.out.println("tables = " + String.join(", ", tables));
        em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0;").executeUpdate();
        for (String table: tables) {
            if (!table.equals("hibernate_sequence")) {
                em.createNativeQuery("TRUNCATE TABLE " + table + ";")
                        .executeUpdate();
            }

            if (!isTesting) em.createNativeQuery("ALTER TABLE " + table + " AUTO_INCREMENT = 0;").executeUpdate();
        }
        em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1;").executeUpdate();

        em.flush();
        em.clear();

    }

}
