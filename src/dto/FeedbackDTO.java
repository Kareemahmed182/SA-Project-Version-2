package dto;

public class FeedbackDTO {
    private int attendeeId;
    private String comments;
    private int rating;

    // Constructor
    public FeedbackDTO(int attendeeId, String comments, int rating) {
        this.attendeeId = attendeeId;
        this.comments = comments;
        this.rating = rating;
    }

    // Getters and Setters
    public int getAttendeeId() {
        return attendeeId;
    }

    public void setAttendeeId(int attendeeId) {
        this.attendeeId = attendeeId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
