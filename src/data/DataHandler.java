package data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class DataHandler<T> {
    private final String filePath;
    private final ObjectMapper objectMapper;

    public DataHandler(String filePath) {
        this.filePath = filePath;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule()); // Handle Java 8+ time types
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Pretty-print JSON
        ensureFileExists();
    }

    public List<T> readData(TypeReference<List<T>> typeReference) {
        try {
            File file = new File(filePath);
            if (file.length() == 0) {
                return List.of(); // Return an empty list if the file is empty
            }
            return objectMapper.readValue(file, typeReference);
        } catch (IOException e) {
            System.err.println("Error reading data from " + filePath + ": " + e.getMessage());
            return List.of();
        }
    }

    public void writeData(List<T> data) {
        try {
            objectMapper.writeValue(new File(filePath), data);
        } catch (IOException e) {
            System.err.println("Error writing data to " + filePath + ": " + e.getMessage());
        }
    }

    private void ensureFileExists() {
        try {
            File file = new File(filePath);
            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs(); // Create directories if needed
            }
            if (file.createNewFile() && file.length() == 0) {
                objectMapper.writeValue(file, List.of()); // Initialize with an empty JSON array
            }
        } catch (IOException e) {
            System.err.println("Error creating file " + filePath + ": " + e.getMessage());
        }
    }
}
