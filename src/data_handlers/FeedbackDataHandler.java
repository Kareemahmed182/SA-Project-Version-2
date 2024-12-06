package data_handlers;

import com.fasterxml.jackson.core.type.TypeReference;
import domain.Feedback;

import java.util.List;

public class FeedbackDataHandler {
    private static final String FILE_PATH = "data_handlers/feedback.json";
    private static final DataHandler<Feedback> dataHandler = new DataHandler<>(FILE_PATH);

    public static List<Feedback> loadFeedback() {
        return dataHandler.readData(new TypeReference<>() {}); // Pass TypeReference for List<Feedback>
    }

    public static void saveFeedback(List<Feedback> feedbackList) {
        dataHandler.writeData(feedbackList);
    }
}
