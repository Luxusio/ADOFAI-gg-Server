package gg.adofai.server.repository;

import gg.adofai.server.GlobalPropertySource;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class InitRepository {

    private final EntityManager em;

    private final GlobalPropertySource globalPropertySource;

    // https://stackoverflow.com/questions/1912813/truncate-all-tables-in-a-mysql-database-in-one-command
    @SuppressWarnings("unchecked")
    public void resetDB() {
        String databaseName = globalPropertySource.getUrl();
        databaseName = databaseName.substring(databaseName.lastIndexOf('/') + 1);

        List<String> tables = em.createNativeQuery("SELECT table_name FROM information_schema.tables" +
                " where table_schema=?;")
                .setParameter(1, databaseName).getResultList();

        em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0;").executeUpdate();
        for (String table: tables) {
            if (!table.equals("hibernate_sequence")) {
                em.createNativeQuery("TRUNCATE TABLE " + table + ";")
                        .executeUpdate();
            }

            em.createNativeQuery("ALTER TABLE " + table + " AUTO_INCREMENT = 0;")
                    .executeUpdate();
        }
        em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1;").executeUpdate();
        System.out.println("tables = " + String.join(", ", tables));

    }

}
