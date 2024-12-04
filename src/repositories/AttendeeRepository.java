package repositories;

import data.AttendeeDataHandler;
import domain.Attendee;
import dto.AttendeeDTO;

import java.util.ArrayList;
import java.util.List;

public class AttendeeRepository {
    private final List<Attendee> attendees;

    public AttendeeRepository() {
        this.attendees = new ArrayList<>(AttendeeDataHandler.loadAttendees()); // Ensure a mutable list
    }

    public AttendeeDTO addAttendee(String name, String email) {
        int newId = attendees.isEmpty() ? 1 : attendees.get(attendees.size() - 1).getId() + 1;
        Attendee newAttendee = new Attendee(newId, name, email);
        attendees.add(newAttendee);
        saveAttendees();
        return new AttendeeDTO(newId, name, email);
    }

    public List<Attendee> getAllAttendees() {
        return new ArrayList<>(attendees); // Return a mutable copy
    }

    private void saveAttendees() {
        AttendeeDataHandler.saveAttendees(attendees); // Persist the list to the JSON file
    }
}
