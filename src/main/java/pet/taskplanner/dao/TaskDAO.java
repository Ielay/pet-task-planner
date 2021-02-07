package pet.taskplanner.dao;

import org.jetbrains.annotations.Nullable;
import pet.taskplanner.entity.Task;

import java.sql.SQLException;
import java.util.List;

/**
 * @author lelay
 * @since 05.02.2021
 */
public interface TaskDAO {

    boolean createTask(Task newTask) throws SQLException;

    @Nullable List<Task> getTasks(long userId);
}
