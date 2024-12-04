package obsever;

import java.util.ArrayList;
import java.util.List;

public class NotificationCenter {
        private final List<Observer> observers = new ArrayList<>();

    // Register an observer
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    // Remove an observer
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    // Notify all observers
    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
}
