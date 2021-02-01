package pet.taskplanner.dao;

import pet.taskplanner.entity.User;
import pet.taskplanner.mapper.UserMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author lelay
 * @since 01.02.2021
 */
public class UserDAOImpl implements UserDAO {

    private final UserMapper userMapper = new UserMapper();

    private final DataSource ds;

    public UserDAOImpl(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public boolean createUser(User newUser) throws SQLException {
        try (Connection conn = ds.getConnection();
             PreparedStatement stat = conn.prepareStatement("INSERT INTO users VALUES (?, ?, ?, ?)")) {
            stat.setLong(1, newUser.getId());
            stat.setString(2, newUser.getNickname());
            stat.setString(3, newUser.getEmail());
            stat.setString(4, newUser.getPassword());

            return stat.executeUpdate() == 1;
        }
    }

    @Override
    public User getUser(String email) throws SQLException {
        try (Connection conn = ds.getConnection();
             PreparedStatement stat = conn.prepareStatement("SELECT * FROM users WHERE email = ?")) {
            stat.setString(1, email);

            ResultSet rs = stat.executeQuery();

            return userMapper.mapFromResultSet(rs);
        }
    }
}
