package service;

import repositories.FeedbackRepository;

import java.util.List;
import java.util.stream.Collectors;

public class FeedbackService {
    private final FeedbackRepository feedbackRepository;

    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public List<String[]> getFeedbackForAttendee(int attendeeId) {
        return feedbackRepository.getFeedbackByAttendee(attendeeId).stream()
                .map(feedback -> new String[]{
                        feedback.getSessionName(),
                        String.valueOf(feedback.getRating()),
                        feedback.getComments()})
                .collect(Collectors.toList());
    }

    public void submitFeedback(int attendeeId, String sessionName, String comments, int rating) {
        feedbackRepository.addFeedback(attendeeId, sessionName, comments, rating); // Pass sessionName
    }

}
