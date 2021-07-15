package gg.adofai.server.repository;

import gg.adofai.server.GlobalPropertySource;
import lombok.RequiredArgsConstructor;
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
    public void resetDB() {
        String databaseName = globalPropertySource.getUrl();
        databaseName = databaseName.substring(databaseName.lastIndexOf('/') + 1);
        Query results = em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0;\n" +
                "\n" +
                "SELECT @str \\:= CONCAT('TRUNCATE TABLE ', table_schema, '.', TABLE_NAME, ';')\n" +
                "  FROM information_schema.tables\n" +
                "  WHERE table_type   = 'BASE TABLE'\n" +
                "  AND table_schema IN ('" + databaseName + "');\n" +
                "\n" +
                "PREPARE stmt FROM @str;\n" +
                "EXECUTE stmt;\n" +
                "DEALLOCATE PREPARE stmt;\n" +
                "\n" +
                "SET FOREIGN_KEY_CHECKS = 1;").setMaxResults(0);
        System.out.println("results = " + results);
    }

}
