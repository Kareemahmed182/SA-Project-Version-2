package controller;

import dto.SessionDTO;
import dto.AttendeeDTO;
import service.CertificateService;
import service.FeedbackService;
import service.SessionService;
import service.AttendeeService;

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

    // Register a new attendee
    public AttendeeDTO registerAttendee(String name, String email) {
        return attendeeService.registerAttendee(name, email);
    }

    // Retrieve all sessions
    public List<SessionDTO> getAllSessions() {
        return sessionService.getAllSessions();
    }

    // Register an attendee for a session
    public void registerForSession(int sessionId, int attendeeId) {
        sessionService.registerAttendeeForSession(sessionId, attendeeId);
    }

    // Retrieve registered sessions for an attendee
    public List<SessionDTO> getRegisteredSessionsForAttendee(int attendeeId) {
        return sessionService.getRegisteredSessionsForAttendee(attendeeId);
    }

    // Submit feedback for a session
    public void submitFeedback(int attendeeId, String comments, int rating) {
        feedbackService.submitFeedback(attendeeId, comments, rating);
    }

    // Generate a certificate
    public String generateCertificate(int attendeeId, String conferenceName) {
        return certificateService.generateCertificate(attendeeId, conferenceName);
    }
}
