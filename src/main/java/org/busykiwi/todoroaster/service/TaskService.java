package org.busykiwi.todoroaster.service;

import org.busykiwi.todoroaster.model.Status;
import org.busykiwi.todoroaster.model.Task;
import org.busykiwi.todoroaster.repository.TaskRepository;

import java.util.List;

public class TaskService {
    TaskRepository taskRepository = new TaskRepository();

    public Task addTask(String title, Status status) {
        Task task = new Task(title, status);
        return taskRepository.saveTask(task);
    }

    public boolean deleteTask(int id) {
        return taskRepository.deleteTask(id);
    }

    public boolean updateTaskStatus(int id, Status status) {
        return taskRepository.updateTask(id, status);
    }

    public List<Task> getAllTasks() {
        return taskRepository.getTasks();
    }

    public int taskCount() {
        return taskRepository.tasksCount();
    }
}
