package pet.taskplanner.dao;

import org.jetbrains.annotations.Nullable;
import pet.taskplanner.entity.User;

import java.sql.SQLException;

/**
 * @author lelay
 * @since 01.02.2021
 */
public interface UserDAO {

    /**
     * @return true if user was saved
     */
    boolean createUser(User newUser) throws SQLException;

    /**
     * @return found user or null if no user was found by specified email
     */
     @Nullable User getUser(String email) throws SQLException;
}
