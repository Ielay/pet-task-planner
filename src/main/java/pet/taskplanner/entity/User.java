package pet.taskplanner.entity;

import pet.taskplanner.entity.annotation.Column;
import pet.taskplanner.entity.annotation.Table;

import java.util.List;

/**
 * @author lelay
 * @since 01.02.2021
 */
@Table("users")
public class User implements Entity {

    @Column("id")
    private Long id;

    @Column("nickname")
    private String nickname;

    @Column("email")
    private String email;

    @Column("password")
    private String password;

    private List<Task> tasks;

    public User() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
