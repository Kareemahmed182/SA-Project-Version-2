package domain;

public class Certificate {
    private int attendeeId;
    private String conferenceName;

    // Constructor
    public Certificate(int attendeeId, String conferenceName) {
        this.attendeeId = attendeeId;
        this.conferenceName = conferenceName;
    }

    // Getters and Setters
    public int getAttendeeId() {
        return attendeeId;
    }

    public void setAttendeeId(int attendeeId) {
        this.attendeeId = attendeeId;
    }

    public String getConferenceName() {
        return conferenceName;
    }

    public void setConferenceName(String conferenceName) {
        this.conferenceName = conferenceName;
    }
}
