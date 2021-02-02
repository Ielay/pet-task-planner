package pet.taskplanner.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pet.taskplanner.entity.User;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author lelay
 * @since 01.02.2021
 */
class UserDAOImplTest extends DAOTest {

    @Autowired
    private UserDAOImpl userDAO;

    @Test
    void createUser_shouldAddNewRecordToUsersTable_ifDataIsCorrect() throws SQLException {
        final String userEmail = "ololo_97_real@yandex.ru";
        User user = new User();
        user.setNickname("ololo_97");
        user.setEmail(userEmail);
        user.setPassword("not_encoded_pass");

        boolean created = userDAO.createUser(user);

        assertTrue(created, "Should be created");
        User foundUser = userDAO.getUser(userEmail);
        assertNotNull(foundUser);
    }

    @Test
    void getUser_shouldGetUserByEmail_ifItExistsInDatabase() throws SQLException {
        final String userEmail = "ivan.ivanov@mail.ru";

        User foundUser = userDAO.getUser(userEmail);

        assertEquals(1, foundUser.getId());
        assertEquals("ivannn1983", foundUser.getNickname());
        assertEquals(userEmail, foundUser.getEmail());
        assertEquals("qwerty", foundUser.getPassword());
    }
}
