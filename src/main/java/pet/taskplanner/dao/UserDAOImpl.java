package pet.taskplanner.dao;

import pet.taskplanner.entity.User;
import pet.taskplanner.mapper.EntityMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author lelay
 * @since 01.02.2021
 */
public class UserDAOImpl implements UserDAO {

    private final EntityMapper entityMapper = new EntityMapper();

    private final DataSource ds;

    public UserDAOImpl(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public boolean createUser(User newUser) throws SQLException {
        try (var conn = ds.getConnection();
             var stat = conn.prepareStatement("INSERT INTO users(nickname, email, password) VALUES (?, ?, ?)")) {
            stat.setString(1, newUser.getNickname());
            stat.setString(2, newUser.getEmail());
            stat.setString(3, newUser.getPassword());

            return stat.executeUpdate() == 1;
        }
    }

    @Override
    public User getUser(String email) throws Exception {
        try (var conn = ds.getConnection();
             var stat = conn.prepareStatement("SELECT * FROM users WHERE email = ?")) {
            stat.setString(1, email);

            ResultSet rs = stat.executeQuery();

            return entityMapper.map(rs, User.class);
        }
    }
}
