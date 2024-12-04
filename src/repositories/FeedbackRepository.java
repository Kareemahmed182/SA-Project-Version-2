package repositories;

import data.FeedbackDataHandler;
import domain.Feedback;

import java.util.ArrayList;
import java.util.List;

public class FeedbackRepository {
    private final List<Feedback> feedbackList;

    public FeedbackRepository() {
        this.feedbackList = new ArrayList<>(FeedbackDataHandler.loadFeedback()); // Ensure a mutable list
    }

    public void addFeedback(Feedback feedback) {
        feedbackList.add(feedback);
        saveFeedback();
    }

    public List<Feedback> getAllFeedback() {
        return new ArrayList<>(feedbackList); // Return a mutable copy
    }

    private void saveFeedback() {
        FeedbackDataHandler.saveFeedback(feedbackList); // Persist the list to the JSON file
    }
}
