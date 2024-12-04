package repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import data.DataHandler;
import data.FilePaths;
import domain.User;
import enums.Role;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private final List<User> users;
    private final DataHandler<User> dataHandler;

    public UserRepository() {
        this.dataHandler = new DataHandler<>(FilePaths.USERS_FILE);
        this.users = new ArrayList<>(dataHandler.readData(new TypeReference<>() {})); // Pass TypeReference for List<User>
    }

    public User authenticate(String username, String password) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    public void registerAttendee(String username, String password) {
        User newAttendee = new User(username, password, Role.ATTENDEE);
        users.add(newAttendee); // Add to the in-memory list
        saveUsers(); // Persist changes to the JSON file
    }

    private void saveUsers() {
        dataHandler.writeData(users); // Persist the list to the JSON file
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users); // Return a mutable copy
    }
}
