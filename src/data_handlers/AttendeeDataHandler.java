package data_handlers;

import com.fasterxml.jackson.core.type.TypeReference;
import domain.Attendee;

import java.util.List;

public class AttendeeDataHandler {
    private static final String FILE_PATH = "data_handlers/attendees.json";
    private static final DataHandler<Attendee> dataHandler = new DataHandler<>(FILE_PATH);

    public static List<Attendee> loadAttendees() {
        return dataHandler.readData(new TypeReference<>() {}); // Pass TypeReference for List<Attendee>
    }

    public static void saveAttendees(List<Attendee> attendees) {
        dataHandler.writeData(attendees);
    }
}
