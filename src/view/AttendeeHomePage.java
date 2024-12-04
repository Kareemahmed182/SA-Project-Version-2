package view;

import controller.ConferenceManager;
import dto.SessionDTO;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AttendeeHomePage extends JFrame {
    private final ConferenceManager manager;

    public AttendeeHomePage(ConferenceManager manager) {
        this.manager = manager;

        setTitle("Attendee Home Page");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Welcome to the Attendee Home Page", SwingConstants.CENTER);
        add(welcomeLabel, BorderLayout.NORTH);

        JPanel sessionPanel = new JPanel(new BorderLayout());
        sessionPanel.setBorder(BorderFactory.createTitledBorder("Available Sessions"));

        List<SessionDTO> sessions = manager.getAllSessions();
        DefaultListModel<String> sessionListModel = new DefaultListModel<>();
        for (SessionDTO session : sessions) {
            sessionListModel.addElement(session.getName() + " by " + session.getSpeaker());
        }

        JList<String> sessionList = new JList<>(sessionListModel);
        sessionPanel.add(new JScrollPane(sessionList), BorderLayout.CENTER);

        JButton registerButton = new JButton("Register for Session");
        registerButton.addActionListener(e -> {
            int selectedIndex = sessionList.getSelectedIndex();
            if (selectedIndex != -1) {
                SessionDTO selectedSession = sessions.get(selectedIndex);
                int sessionId = Integer.parseInt(selectedSession.getSessionId()); // Convert String to int
                manager.registerForSession(sessionId, 1); // Replace `1` with actual attendee ID
                JOptionPane.showMessageDialog(this, "You have registered for " + selectedSession.getName());
            } else {
                JOptionPane.showMessageDialog(this, "Please select a session.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        sessionPanel.add(registerButton, BorderLayout.SOUTH);
        add(sessionPanel, BorderLayout.CENTER);

        setVisible(true);
    }
}
