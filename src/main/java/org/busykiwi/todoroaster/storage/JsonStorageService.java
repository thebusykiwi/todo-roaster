package org.busykiwi.todoroaster.storage;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.busykiwi.todoroaster.model.Task;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonStorageService {

    private static final String filePath = System.getProperty("user.dir") + File.separator +"tasks.json";
    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
    File file = new File(filePath);

    public List<Task> loadTasks() {
        try {
            if (!file.exists() || file.length() == 0) {
                return new ArrayList<>();
            }
            return objectMapper.readValue(file, new TypeReference<List<Task>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void saveTasks(List<Task> tasks) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValue(file, tasks);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
