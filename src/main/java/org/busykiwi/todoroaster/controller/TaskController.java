package org.busykiwi.todoroaster.controller;

import org.busykiwi.todoroaster.model.Status;
import org.busykiwi.todoroaster.model.Task;
import org.busykiwi.todoroaster.service.TaskService;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class TaskController {
    private TaskService taskService = new TaskService();
    private final Scanner sc = new Scanner(System.in);
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public void start() {
        int choice;
        int pendingTaskCount = taskService.taskCount();
        System.out.println("Current pending tasks: " + pendingTaskCount);

        while(true) {
            System.out.println("Choose the option:");
            System.out.println("1. Add new task");
            System.out.println("2. Delete task");
            System.out.println("3. Update task status");
            System.out.println("4. List all tasks");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("--------------------");
                System.out.println("| Integers only!!! |");
                System.out.println("--------------------");
                continue;
            }

            switch (choice) {
                case 1:
                    addTask();
                    break;
                case 2:
                    deleteTask();
                    break;
                case 3:
                    updateTaskStatus();
                    break;
                case 4:
                    printTasks();
                    break;
                case 5:
                    saveTask();
                    System.out.println("Thanks for choosing us!");
                    return;
                default:
                    System.out.println("Invalid Choice");
                    break;
            }
        }
    }

    public void addTask() {
        System.out.print("Enter title: ");
        String title = sc.nextLine();
        System.out.print("Enter status (TODO / IN_PROGRESS / DONE): ");
        Status status = readStatus();
        if (status != null) {
            Task task = taskService.addTask(title, status);
            System.out.println("Task added successfully. ID: " + task.getId());
        } else {
            System.out.println("Invalid status");
        }
    }

    public void deleteTask() {
        int id;
        System.out.print("Enter Id: ");
        while (true) {
            try {
                id = Integer.parseInt(sc.nextLine());
                break;
            } catch (Exception e) {
                System.out.println("--------------------");
                System.out.println("| Integers only!!! |");
                System.out.println("--------------------");
            }
        }
        if (taskService.deleteTask(id)) {
            System.out.println("Task deleted successfully");
        } else {
            System.out.println("Task not available");
        }
    }

    public void updateTaskStatus() {
        int id;
        System.out.print("Enter Id: ");
        while (true) {
            try {
                id = Integer.parseInt(sc.nextLine());
                break;
            } catch (Exception e) {
                System.out.println("--------------------");
                System.out.println("| Integers only!!! |");
                System.out.println("--------------------");
            }
        }
        System.out.print("Enter status (TODO / IN_PROGRESS / DONE): ");
        Status status = readStatus();
        if (status != null) {
            if (taskService.updateTaskStatus(id, status)) {
                System.out.println("Task updated successfully");
            } else {
                System.out.println("Task not available");
            };
        } else {
            System.out.println("Invalid status");
        }
    }

    public void printTasks() {
        List<Task> tasks = taskService.getAllTasks();
        if (tasks.isEmpty()) {
            System.out.println("Done for today :)");
            return;
        }
        System.out.println("---------------------------------------------------------------");
        System.out.printf("| %-3s | %-20s | %-11s | %-16s |%n",
                "ID", "Title", "Status", "Last Updated");
        System.out.println("---------------------------------------------------------------");

        for (Task t : tasks) {
            String formattedDate = t.getUpdatedAt().format(formatter);
            System.out.printf("| %-3d | %-20s | %-11s | %-16s |%n",
                    t.getId(),
                    t.getTitle(),
                    t.getStatus(),
                    formattedDate);
        }
        System.out.println("---------------------------------------------------------------");
    }

    public Status readStatus() {
        String status = sc.nextLine();
        try {
            return Status.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public void saveTask() {
        taskService.saveTasks();
    }
}
