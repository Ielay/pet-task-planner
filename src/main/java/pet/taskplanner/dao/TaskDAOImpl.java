package pet.taskplanner.dao;

import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.Nullable;
import pet.taskplanner.entity.Task;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

/**
 * @author lelay
 * @since 05.02.2021
 */
public class TaskDAOImpl implements TaskDAO {

    private final DataSource ds;

    public TaskDAOImpl(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public boolean createTask(Task newTask) throws SQLException {
        try (var conn = ds.getConnection();
             var stat = conn.prepareStatement("INSERT INTO tasks VALUES (?, ?, ?, ?, ?, ?, ?)")) {
//            stat.setLong(1, newTask.);
        }
        return true;
    }

    @Override
    public @Nullable List<Task> getTasks(long userId) {
        throw new NotImplementedException();
    }
}
