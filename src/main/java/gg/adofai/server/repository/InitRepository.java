package gg.adofai.server.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class InitRepository {

    private final EntityManager em;

    public void resetDB() {
        em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0;\n" +
                "\n" +
                "SELECT @str := CONCAT('TRUNCATE TABLE ', table_schema, '.', TABLE_NAME, ';')\n" +
                "  FROM information_schema.tables\n" +
                "  WHERE table_type   = 'BASE TABLE'\n" +
                "  AND table_schema IN ('test'test_table);\n" +
                "\n" +
                "PREPARE stmt FROM @str;\n" +
                "EXECUTE stmt;\n" +
                "DEALLOCATE PREPARE stmt;\n" +
                "\n" +
                "SET FOREIGN_KEY_CHECKS = 1;").executeUpdate();
    }

}
