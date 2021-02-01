package pet.taskplanner.mapper;

import pet.taskplanner.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author lelay
 * @since 01.02.2021
 */
public class UserMapper {

    public User mapFromResultSet(ResultSet rs) throws SQLException {
        if (!rs.next()) {
            return null;
        }

        User user = new User();
        user.setId(rs.getLong("id"));
        user.setNickname(rs.getString("nickname"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));

        return user;
    }
}
