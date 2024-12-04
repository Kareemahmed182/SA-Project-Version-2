package domain;

import java.util.List;

public class Speaker {
    private String name;
    private String bio;
    private List<Integer> sessionIds; // List of sessions the speaker is part of

    // Constructor
    public Speaker(String name, String bio) {
        this.name = name;
        this.bio = bio;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public List<Integer> getSessionIds() {
        return sessionIds;
    }

    public void setSessionIds(List<Integer> sessionIds) {
        this.sessionIds = sessionIds;
    }
}
