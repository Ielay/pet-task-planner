package pet.taskplanner.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pet.taskplanner.entity.Task;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author lelay
 * @since 07.02.2021
 */
public class TaskDAOImplTest extends DAOTest {

    @Autowired
    private TaskDAOImpl taskDAO;

    @Test
    void createTask_shouldAddNewRecordToUsersTable_ifDataIsCorrect() throws InvocationTargetException, SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        final long userId = 1L;
        Task newTask = new Task();
        newTask.setHeader("Develop my own entity mapper");
        newTask.setDescription(null);
        newTask.setBeginTime(OffsetDateTime.now());
        newTask.setDeadline(OffsetDateTime.now().plusDays(1));
        newTask.setFinished(false);
        newTask.setExpired(false);
        newTask.setUserId(userId);

        taskDAO.createTask(newTask);

        List<Task> tasks = taskDAO.getTasks(userId);
        assertTrue(tasks.contains(newTask));
    }

    @Test
    void getTasks_shouldAllTasksOfUserByItsId_ifUserHasTasks() throws InvocationTargetException, SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        final long userId = 1;

        List<Task> tasks = taskDAO.getTasks(userId);

        assertEquals(3, tasks.size());
    }
}
