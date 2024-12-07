package repositories;

import data_handlers.SessionDataHandler;
import domain.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SessionRepository {
    private final List<Session> sessions;

    public SessionRepository() {
        this.sessions = new ArrayList<>(SessionDataHandler.loadSessions());
    }

    public void removeAttendeeFromSession(int sessionId, int attendeeId) {
        Session session = sessions.stream()
                .filter(s -> s.getSessionId() == sessionId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Session not found"));
        session.getAttendees().remove((Integer) attendeeId);
        saveSessions();
    }

    public List<Session> getSessionsForAttendee(int attendeeId) {
        return sessions.stream()
                .filter(session -> session.getAttendees().contains(attendeeId))
                .collect(Collectors.toList());
    }

    public List<Session> getAllSessions() {
        return new ArrayList<>(sessions);
    }

    private void saveSessions() {
        SessionDataHandler.saveSessions(sessions);
    }
}
