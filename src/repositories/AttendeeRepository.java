package repositories;

import data_handlers.AttendeeDataHandler;
import domain.Attendee;

import java.util.ArrayList;
import java.util.List;

public class AttendeeRepository {
    private final List<Attendee> attendees;

    public AttendeeRepository() {
        this.attendees = new ArrayList<>(AttendeeDataHandler.loadAttendees());
    }

    public Attendee addAttendee(String name, String email) {
        int newId = attendees.isEmpty() ? 1 : attendees.get(attendees.size() - 1).getId() + 1;
        Attendee newAttendee = new Attendee(newId, name, email);
        attendees.add(newAttendee);
        saveAttendees();
        return newAttendee;
    }

    public List<Attendee> getAllAttendees() {
        return new ArrayList<>(attendees);
    }

    public Attendee getAttendeeByUsername(String username) {
        return attendees.stream()
                .filter(attendee -> attendee.getName().equals(username))
                .findFirst()
                .orElse(null);
    }

    private void saveAttendees() {
        AttendeeDataHandler.saveAttendees(attendees);
    }
}
