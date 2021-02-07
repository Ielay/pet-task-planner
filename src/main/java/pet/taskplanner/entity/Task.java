package pet.taskplanner.entity;

import pet.taskplanner.entity.annotation.Column;
import pet.taskplanner.entity.annotation.Table;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * @author lelay
 * @since 05.02.2021
 */
@Table("tasks")
public class Task implements Entity {

    @Column("id")
    private Long id;

    @Column("header")
    private String header;

    @Column("description")
    private String description;

    @Column("begin_time")
    private OffsetDateTime beginTime;

    @Column("deadline")
    private OffsetDateTime deadline;

    @Column("finished")
    private Boolean finished;

    @Column("expired")
    private Boolean expired;

    @Column("user_id")
    private Long userId;

    public Task() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OffsetDateTime getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(OffsetDateTime beginTime) {
        this.beginTime = beginTime;
    }

    public OffsetDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(OffsetDateTime deadline) {
        this.deadline = deadline;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public Boolean getExpired() {
        return expired;
    }

    public void setExpired(Boolean expired) {
        this.expired = expired;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    //ignore 'id' field
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (!header.equals(task.header)) return false;
        if (description != null ? !description.equals(task.description) : task.description != null) return false;
        //to check equality of OffsetDateTime 'isEqual' should be used
        if (!beginTime.isEqual(task.beginTime)) return false;
        //same as for 'beginTime'
        if (deadline != null ? !deadline.isEqual(task.deadline) : task.deadline != null) return false;
        if (finished != null ? !finished.equals(task.finished) : task.finished != null) return false;
        if (expired != null ? !expired.equals(task.expired) : task.expired != null) return false;
        return userId.equals(task.userId);
    }

    @Override
    public int hashCode() {
        int result = header.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + beginTime.hashCode();
        result = 31 * result + (deadline != null ? deadline.hashCode() : 0);
        result = 31 * result + (finished != null ? finished.hashCode() : 0);
        result = 31 * result + (expired != null ? expired.hashCode() : 0);
        result = 31 * result + userId.hashCode();
        return result;
    }
}
