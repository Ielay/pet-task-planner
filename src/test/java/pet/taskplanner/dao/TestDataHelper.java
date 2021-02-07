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
            insertAllIntoTasks(stat);
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

    private void insertAllIntoTasks(Statement stat) throws SQLException {
        stat.execute(
                "INSERT INTO tasks (header, description, begin_time, deadline, finished, expired, user_id) VALUES" +
                "('Buy some food', 'Go to market and buy something to eat', '2016-06-22 00:00:00-07', '2016-06-23 00:00:00-07', false, false, 1)," +
                "('Push up 10 times', null, '2016-06-22 00:00:00-07', '2016-06-23 00:00:00-07', false, false, 1)," +
                "('Go to bed until midnight', null, '2016-06-22 00:00:00-07', '2016-06-23 00:00:00-07', false, false, 1)"
        );
    }

    public void dropAll() throws SQLException {
        try (var conn = ds.getConnection();
             var stat = conn.createStatement()) {
            stat.execute(
                    "TRUNCATE tasks RESTART IDENTITY CASCADE;" +
                    "TRUNCATE users RESTART IDENTITY CASCADE;"
            );
        }
    }
}
