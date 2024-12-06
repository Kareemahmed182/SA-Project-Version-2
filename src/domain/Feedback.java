package domain;

public class Feedback {
    private int attendeeId;
    private String comments;
    private int rating;

    // Default constructor (required for Jackson)
    public Feedback() {
    }

    // Parameterized constructor
    public Feedback(int attendeeId, String comments, int rating) {
        this.attendeeId = attendeeId;
        this.comments = comments;
        this.rating = rating;
    }

    // Getters and setters
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
