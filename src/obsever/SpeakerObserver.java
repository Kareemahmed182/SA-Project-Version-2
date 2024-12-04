package obsever;

import domain.Speaker;

public class SpeakerObserver implements Observer {
    private final Speaker speaker;

    public SpeakerObserver(Speaker speaker) {
        this.speaker = speaker;
    }

    @Override
    public void update(String message) {
        System.out.println("Notification for Speaker " + speaker.getName() + ": " + message);
    }
}
