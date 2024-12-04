package service;

import domain.Feedback;
import dto.FeedbackDTO;
import repositories.FeedbackRepository;

import java.util.List;
import java.util.stream.Collectors;

public class FeedbackService {
    private final FeedbackRepository repository;

    // Constructor
    public FeedbackService(FeedbackRepository repository) {
        this.repository = repository;
    }

    // Submit feedback
    public void submitFeedback(int attendeeId, String comments, int rating) {
        Feedback feedback = new Feedback(attendeeId, comments, rating);
        repository.addFeedback(feedback);
    }

    // Get all feedback
    public List<FeedbackDTO> getAllFeedback() {
        return repository.getAllFeedback().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Helper: Convert domain object to DTO
    private FeedbackDTO toDTO(Feedback feedback) {
        return new FeedbackDTO(feedback.getAttendeeId(), feedback.getComments(), feedback.getRating());
    }
}
