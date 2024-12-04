package view;

import controller.ConferenceManager;

import javax.swing.*;

public class SpeakerHomePage extends JFrame {
    private final ConferenceManager manager;

    public SpeakerHomePage(ConferenceManager manager) {
        this.manager = manager;

        setTitle("Speaker Home Page");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JLabel welcomeLabel = new JLabel("Welcome to the Speaker Home Page");
        welcomeLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(welcomeLabel);

        // Add speaker-specific UI components here...

        setVisible(true);
    }
}
