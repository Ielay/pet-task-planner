package pet.taskplanner.dao;

import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pet.taskplanner.entity.Task;
import pet.taskplanner.mapper.EntityMapper;

import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author lelay
 * @since 05.02.2021
 */
public class TaskDAOImpl implements TaskDAO {

    private final EntityMapper entityMapper = new EntityMapper();

    private final DataSource ds;

    public TaskDAOImpl(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public boolean createTask(Task newTask) throws SQLException {
        try (var conn = ds.getConnection();
             var stat = conn.prepareStatement("INSERT INTO tasks " +
                     "(header, description, begin_time, deadline, finished, expired, user_id) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)")) {
            stat.setString(1, newTask.getHeader());
            stat.setString(2, newTask.getDescription());
            stat.setObject(3, newTask.getBeginTime());
            stat.setObject(4, newTask.getDeadline());
            stat.setBoolean(5, newTask.getFinished());
            stat.setBoolean(6, newTask.getExpired());
            stat.setLong(7, newTask.getUserId());

            return stat.executeUpdate() == 1;
        }
    }

    @Override
    public @NotNull List<Task> getTasks(long userId) throws SQLException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        try (var conn = ds.getConnection();
             var stat = conn.prepareStatement("SELECT * FROM tasks WHERE user_id = ?")) {
            stat.setLong(1, userId);

            ResultSet rs = stat.executeQuery();

            return entityMapper.mapList(rs, Task.class);
        }
    }
}
