package pet.taskplanner.dao;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pet.taskplanner.entity.Task;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author lelay
 * @since 05.02.2021
 */
public interface TaskDAO {

    boolean createTask(Task newTask) throws SQLException;

    @NotNull List<Task> getTasks(long userId) throws SQLException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException;
}
