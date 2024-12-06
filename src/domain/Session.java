package domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Session {
    private int sessionId;
    private String name;
    private String speaker;
    private LocalDateTime dateTime;
    private String room;
    private List<Integer> attendees;

    // Default constructor (required for Jackson)
    public Session() {
        this.attendees = new ArrayList<>();
    }

    // Parameterized constructor
    public Session(String name, String speaker, LocalDateTime dateTime, String room) {
        this.name = name;
        this.speaker = speaker;
        this.dateTime = dateTime;
        this.room = room;
        this.attendees = new ArrayList<>();
    }

    // Getters and setters
    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public List<Integer> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<Integer> attendees) {
        this.attendees = attendees;
    }
}
