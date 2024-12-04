package obsever;

import domain.Attendee;

public class AttendeeObserver implements Observer {
    private final Attendee attendee;

    public AttendeeObserver(Attendee attendee) {
        this.attendee = attendee;
    }

    @Override
    public void update(String message) {
        System.out.println("Notification for " + attendee.getName() + ": " + message);
    }
}
