package dto;

import java.time.LocalDateTime;

public class SessionDTO {
    private final String sessionId;
    private final String name;
    private final String speaker;
    private final String room;
    private final LocalDateTime dateTime;

    public SessionDTO(String sessionId, String name, String speaker, String room, LocalDateTime dateTime) {
        this.sessionId = sessionId;
        this.name = name;
        this.speaker = speaker;
        this.room = room;
        this.dateTime = dateTime;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getName() {
        return name;
    }

    public String getSpeaker() {
        return speaker;
    }

    public String getRoom() {
        return room;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
