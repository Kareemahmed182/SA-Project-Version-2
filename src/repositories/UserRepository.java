package repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import data_handlers.DataHandler;
import data_handlers.FilePaths;
import domain.User;
import enums.Role;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private final List<User> users;
    private final DataHandler<User> dataHandler;

    public UserRepository() {
        this.dataHandler = new DataHandler<>(FilePaths.USERS_FILE);
        this.users = new ArrayList<>(dataHandler.readData(new TypeReference<>() {})); // Load users from JSON file
    }

    public User authenticate(String username, String password) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    public void registerAttendee(String username, String password) {
        User newAttendee = new User(username, password, Role.ATTENDEE);
        users.add(newAttendee); // Add the new user to the in-memory list
        saveUsers(); // Persist the updated list of users to the JSON file
    }

    private void saveUsers() {
        try {
            dataHandler.writeData(users); // Persist the list to the JSON file
            System.out.println("Users saved to file: " + users.size());
            System.out.println("Saving users to: " + new File(FilePaths.USERS_FILE).getAbsolutePath());
        } catch (Exception e) {
            System.err.println("Error saving users to file: " + e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users); // Return a mutable copy of the users list
    }

}
