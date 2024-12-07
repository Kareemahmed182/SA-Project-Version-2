package controller;

import dto.AttendeeDTO;
import dto.SessionDTO;
import service.CertificateService;
import service.FeedbackService;
import service.SessionService;

import java.util.List;

public class ConferenceManager {
    private final SessionService sessionService;
    private final FeedbackService feedbackService;
    private final CertificateService certificateService;

    public ConferenceManager(SessionService sessionService, FeedbackService feedbackService, CertificateService certificateService) {
        this.sessionService = sessionService;
        this.feedbackService = feedbackService;
        this.certificateService = certificateService;
    }

    public void cancelSessionRegistration(int attendeeId, int sessionId) {
        sessionService.cancelSessionRegistration(attendeeId, sessionId);
    }

    public List<String> getCertificatesForAttendee(int attendeeId) {
        return certificateService.getCertificatesForAttendee(attendeeId);
    }

    public List<String[]> getFeedbackForAttendee(int attendeeId) {
        return feedbackService.getFeedbackForAttendee(attendeeId);
    }

    public List<SessionDTO> getAllSessions() {
        return sessionService.getAllSessions();
    }

    public List<SessionDTO> getRegisteredSessionsForAttendee(int attendeeId) {
        return sessionService.getRegisteredSessionsForAttendee(attendeeId);
    }

    public void submitFeedback(int attendeeId, String sessionName, String comments, int rating) {
        feedbackService.submitFeedback(attendeeId, sessionName, comments, rating); // Pass all parameters
    }
}
