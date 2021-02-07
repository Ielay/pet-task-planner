package pet.taskplanner.dao;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author lelay
 * @since 01.02.2021
 */
public class TestDataHelper {

    private final DataSource ds;

    public TestDataHelper(DataSource ds) {
        this.ds = ds;
    }

    public void insertAll() throws SQLException {
        try (var conn = ds.getConnection();
             var stat = conn.createStatement()) {
            insertAllIntoUsers(stat);
        }
    }

    private void insertAllIntoUsers(Statement stat) throws SQLException {
        stat.execute(
                "INSERT INTO users (nickname, email, password) VALUES" +
                        "('ivannn1983', 'ivan.ivanov@mail.ru', 'qwerty')," +
                        "('xx_alex_xx', 'alexx@gmail.com', 'abcdef')," +
                        "('qwerty_user', 'qwerty_user@yandex.ru', '123qwe_');"
        );
    }

    public void dropAll() throws SQLException {
        try (var conn = ds.getConnection();
             var stat = conn.createStatement()) {
            stat.execute(
                    "TRUNCATE users RESTART IDENTITY CASCADE;"
            );
        }
    }
}
