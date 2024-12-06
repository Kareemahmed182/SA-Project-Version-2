package repositories;

import data_handlers.SessionDataHandler;
import domain.Session;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SessionRepository {
    private final List<Session> sessions;

    public SessionRepository() {
        this.sessions = new ArrayList<>(SessionDataHandler.loadSessions());
    }

    public void addSession(String name, String speaker, LocalDateTime dateTime, String room) {
        Session newSession = new Session(name, speaker, dateTime, room); // Uses the parameterized constructor
        sessions.add(newSession);
        saveSessions();
    }


    public void addAttendeeToSession(int sessionId, int attendeeId) {
        Session session = sessions.stream()
                .filter(s -> s.getSessionId() == sessionId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Session not found"));
        session.getAttendees().add(attendeeId);
        saveSessions();
    }

    public List<Session> getSessionsForAttendee(int attendeeId) {
        return sessions.stream()
                .filter(session -> session.getAttendees().contains(attendeeId))
                .collect(Collectors.toList());
    }

    public List<Session> getAllSessions() {
        return new ArrayList<>(sessions); // Return a mutable copy
    }

    private void saveSessions() {
        SessionDataHandler.saveSessions(sessions); // Persist the list to the JSON file
    }
}
