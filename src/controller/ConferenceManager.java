package controller;

import dto.AttendeeDTO;
import dto.SessionDTO;
import service.AttendeeService;
import service.CertificateService;
import service.FeedbackService;
import service.SessionService;

import java.time.LocalDateTime;
import java.util.List;

public class ConferenceManager {
    private final AttendeeService attendeeService;
    private final SessionService sessionService;
    private final FeedbackService feedbackService;
    private final CertificateService certificateService;

    public ConferenceManager(AttendeeService attendeeService, SessionService sessionService, FeedbackService feedbackService, CertificateService certificateService) {
        this.attendeeService = attendeeService;
        this.sessionService = sessionService;
        this.feedbackService = feedbackService;
        this.certificateService = certificateService;
    }

    public AttendeeDTO registerAttendee(String name, String email) {
        return attendeeService.registerAttendee(name, email);
    }

    public List<AttendeeDTO> getAllAttendees() {
        return attendeeService.getAllAttendees();
    }

    public List<SessionDTO> getAllSessions() {
        return sessionService.getAllSessions();
    }

    public SessionDTO createSession(String name, String speaker, LocalDateTime dateTime, String room) {
        sessionService.createSession(name, speaker, dateTime, room);
        return new SessionDTO(
                String.valueOf(sessionService.getLastSessionId()), // Add this in service if necessary
                name,
                speaker,
                room,
                dateTime
        );
    }

    public void registerForSession(int sessionId, int attendeeId) {
        sessionService.registerAttendeeForSession(sessionId, attendeeId);
    }

    public void submitFeedback(int attendeeId, String comments, int rating) {
        feedbackService.submitFeedback(attendeeId, comments, rating);
    }

    public String generateCertificate(int attendeeId, String conferenceName) {
        return certificateService.generateCertificate(attendeeId, conferenceName);
    }
}
