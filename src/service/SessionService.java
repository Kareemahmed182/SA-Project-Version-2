package service;

import dto.SessionDTO;
import repositories.SessionRepository;

import java.util.List;
import java.util.stream.Collectors;

public class SessionService {
    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public void cancelSessionRegistration(int attendeeId, int sessionId) {
        sessionRepository.removeAttendeeFromSession(sessionId, attendeeId);
    }

    public List<SessionDTO> getRegisteredSessionsForAttendee(int attendeeId) {
        return sessionRepository.getSessionsForAttendee(attendeeId).stream()
                .map(session -> new SessionDTO(
                        String.valueOf(session.getSessionId()),
                        session.getName(),
                        session.getSpeaker(),
                        session.getRoom(),
                        session.getDateTime()))
                .collect(Collectors.toList());
    }

    public List<SessionDTO> getAllSessions() {
        return sessionRepository.getAllSessions().stream()
                .map(session -> new SessionDTO(
                        String.valueOf(session.getSessionId()),
                        session.getName(),
                        session.getSpeaker(),
                        session.getRoom(),
                        session.getDateTime()))
                .collect(Collectors.toList());
    }
}
