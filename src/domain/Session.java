package domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Session {
    private static int idCounter = 1;
    private final int sessionId;
    private final String name;
    private final String speaker;
    private final LocalDateTime dateTime;
    private final String room;
    private final List<Integer> attendees;

    public Session(String name, String speaker, LocalDateTime dateTime, String room) {
        this.sessionId = idCounter++;
        this.name = name;
        this.speaker = speaker;
        this.dateTime = dateTime;
        this.room = room;
        this.attendees = new ArrayList<>();
    }

    public int getSessionId() {
        return sessionId;
    }

    public String getName() {
        return name;
    }

    public String getSpeaker() {
        return speaker;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getRoom() {
        return room;
    }

    public List<Integer> getAttendees() {
        return attendees;
    }
}
