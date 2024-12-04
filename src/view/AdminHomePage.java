package view;

import controller.ConferenceManager;

import javax.swing.*;

public class AdminHomePage extends JFrame {
    private final ConferenceManager manager;

    public AdminHomePage(ConferenceManager manager) {
        this.manager = manager;

        setTitle("Admin Home Page");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JLabel welcomeLabel = new JLabel("Welcome to the Admin Home Page");
        welcomeLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(welcomeLabel);

        // Add admin-specific UI components here...

        setVisible(true);
    }
}
