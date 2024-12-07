package repositories;

import data_handlers.FeedbackDataHandler;
import domain.Feedback;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FeedbackRepository {
    private final List<Feedback> feedbackList;

    public FeedbackRepository() {
        this.feedbackList = new ArrayList<>(FeedbackDataHandler.loadFeedback());
    }

    public List<Feedback> getFeedbackByAttendee(int attendeeId) {
        return feedbackList.stream()
                .filter(feedback -> feedback.getAttendeeId() == attendeeId)
                .collect(Collectors.toList());
    }

    public void addFeedback(int attendeeId, String sessionName, String comments, int rating) {
        Feedback feedback = new Feedback(attendeeId, sessionName, comments, rating);
        feedbackList.add(feedback);
        saveFeedback();
    }


    private void saveFeedback() {
        FeedbackDataHandler.saveFeedback(feedbackList);
    }
}
