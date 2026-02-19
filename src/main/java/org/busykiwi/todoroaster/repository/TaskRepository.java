package org.busykiwi.todoroaster.repository;

import org.busykiwi.todoroaster.config.DatabaseConfig;
import org.busykiwi.todoroaster.model.Status;
import org.busykiwi.todoroaster.model.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository {
    public Task saveTask(Task task) {
        String sql = "INSERT INTO TASKS VALUES (title, status, created_at, updated_at)(?, ?, ?, ?);";

        try (Connection con = DatabaseConfig.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, task.getTitle());
            ps.setString(2, task.getStatus().toString());
            ps.setTimestamp(3, Timestamp.valueOf(task.getCreatedAt()));
            ps.setTimestamp(4, Timestamp.valueOf(task.getUpdatedAt()));

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return task;
    }

    public boolean updateTask(Task task) {
        String sql = "UPDATE TASKS SET STATUS = ?, UPDATED_AT = ? WHERE ID = ?;";

        try (Connection con = DatabaseConfig.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, task.getStatus().toString());
            ps.setTimestamp(2, Timestamp.valueOf(task.getUpdatedAt()));
            ps.setInt(3, task.getId());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public boolean deleteTask(Task task) {
        String sql = "DELETE FROM TASKS WHERE ID = ?;";

        try (Connection con = DatabaseConfig.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, task.getId());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public List<Task> getTasks() {
        String sql = "SELECT * FROM TASKS;";

        List<Task> tasks = new ArrayList<>();
        try (Connection con = DatabaseConfig.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Task task = new Task(
                        rs.getInt("id"),
                        rs.getString("title"),
                        Status.valueOf(rs.getString("status"))
                );

                task.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                task.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public int tasksCount() {
        String sql = "SELECT count(*) FROM TASKS where STATUS = 'DONE';";

        try (Connection con = DatabaseConfig.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
