package org.busykiwi.todoroaster;

import org.busykiwi.todoroaster.controller.TaskController;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to the Todo App (prepare yourself)");
        TaskController taskController = new TaskController();
        taskController.start();
    }
}