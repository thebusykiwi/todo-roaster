package org.busykiwi.todoroaster.service;

import org.busykiwi.todoroaster.model.Status;
import org.busykiwi.todoroaster.model.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskService {
    private List<Task> tasks = new ArrayList<>();
    private int idCount = 1;

    public Task addTask(String title, Status status) {
        Task task = new Task(idCount++, title, status);
        tasks.add(task);
        return task;
    }

    public boolean deleteTask(int id) {
        return tasks.removeIf(t -> t.getId() == id);
    }

    public boolean updateTaskStatus(int id, Status status) {
        Optional<Task> task = tasks.stream()
                .filter(t -> t.getId() == id)
                .findFirst();
        if (task.isPresent()) {
            task.get().setStatus(status);
            task.get().setUpdatedAt(LocalDateTime.now());
            return true;
        }
        return false;
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    public int taskCount() {
        return tasks.size();
    }
}
