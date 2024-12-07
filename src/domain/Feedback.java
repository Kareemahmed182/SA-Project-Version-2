package domain;

public class Feedback {
    private int attendeeId;
    private String sessionName; // Include session name if needed
    private String comments;
    private int rating; // 1 to 5

    // No-argument constructor (required for Jackson)
    public Feedback() {
    }

    // Constructor
    public Feedback(int attendeeId, String sessionName, String comments, int rating) {
        this.attendeeId = attendeeId;
        this.sessionName = sessionName;
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

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
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
