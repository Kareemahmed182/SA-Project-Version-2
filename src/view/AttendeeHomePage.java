package view;

import controller.ConferenceManager;
import dto.AttendeeDTO;
import dto.SessionDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AttendeeHomePage extends JFrame {
    private final ConferenceManager manager;
    private final AttendeeDTO attendee;

    public AttendeeHomePage(ConferenceManager manager, AttendeeDTO attendee) {
        this.manager = manager;
        this.attendee = attendee;

        setTitle("Attendee Home Page");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main container
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Welcome label
        JLabel welcomeLabel = new JLabel("Welcome, " + attendee.getName() + "!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        mainPanel.add(welcomeLabel, BorderLayout.NORTH);

        // Tabbed pane for different functionalities
        JTabbedPane tabbedPane = new JTabbedPane();

        // Add tabs
        tabbedPane.addTab("Profile", createProfilePanel());
        tabbedPane.addTab("View All Sessions", createAllSessionsPanel());
        tabbedPane.addTab("Register for a Session", createRegisterSessionPanel());
        tabbedPane.addTab("View Registered Sessions", createRegisteredSessionsPanel());
        tabbedPane.addTab("Feedback", createFeedbackTab());
        tabbedPane.addTab("Certificates", createCertificatesPanel());
        tabbedPane.addTab("Notifications", createNotificationsPanel());
        tabbedPane.addTab("Feedback History", createFeedbackHistoryPanel());

        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        // Set up the frame
        add(mainPanel);
        setVisible(true);
    }

    // Feature 1: Profile Tab
    private JPanel createProfilePanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.add(new JLabel("Name: " + attendee.getName()));
        panel.add(new JLabel("Email: " + attendee.getEmail()));
        panel.add(new JLabel("ID: " + attendee.getId()));
        return panel;
    }

    // Feature 2: View All Sessions Tab with Search and Filters
    private JPanel createAllSessionsPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel headerLabel = new JLabel("Available Sessions", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(headerLabel, BorderLayout.NORTH);

        JTextField searchField = new JTextField();
        searchField.setBorder(BorderFactory.createTitledBorder("Search by Name or Speaker"));

        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Speaker", "Room", "Date"}, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Load sessions into table
        List<SessionDTO> sessions = manager.getAllSessions();
        for (SessionDTO session : sessions) {
            tableModel.addRow(new Object[]{
                    session.getSessionId(),
                    session.getName(),
                    session.getSpeaker(),
                    session.getRoom(),
                    session.getDateTime()
            });
        }

        searchField.addActionListener(e -> {
            String query = searchField.getText().toLowerCase();
            tableModel.setRowCount(0); // Clear table
            for (SessionDTO session : sessions) {
                if (session.getName().toLowerCase().contains(query) || session.getSpeaker().toLowerCase().contains(query)) {
                    tableModel.addRow(new Object[]{
                            session.getSessionId(),
                            session.getName(),
                            session.getSpeaker(),
                            session.getRoom(),
                            session.getDateTime()
                    });
                }
            }
        });

        panel.add(searchField, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    // Feature 3: Register for a Session Tab
    private JPanel createRegisterSessionPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel headerLabel = new JLabel("Register for a Session", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(headerLabel, BorderLayout.NORTH);

        List<SessionDTO> sessions = manager.getAllSessions();
        JComboBox<String> sessionDropdown = new JComboBox<>();
        for (SessionDTO session : sessions) {
            sessionDropdown.addItem(session.getName() + " by " + session.getSpeaker());
        }

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(e -> {
            int selectedIndex = sessionDropdown.getSelectedIndex();
            if (selectedIndex != -1) {
                SessionDTO selectedSession = sessions.get(selectedIndex);
                manager.registerForSession(Integer.parseInt(selectedSession.getSessionId()), attendee.getId());
                JOptionPane.showMessageDialog(this, "Successfully registered for " + selectedSession.getName() + "!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a session.");
            }
        });

        JPanel registerPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        registerPanel.add(sessionDropdown);
        registerPanel.add(registerButton);

        panel.add(registerPanel, BorderLayout.CENTER);
        return panel;
    }
    private JPanel createFeedbackTab() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel headerLabel = new JLabel("Submit Feedback", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(headerLabel, BorderLayout.NORTH);

        // Create feedback form
        JPanel feedbackFormPanel = new JPanel();
        feedbackFormPanel.setLayout(new BoxLayout(feedbackFormPanel, BoxLayout.Y_AXIS));

        // Dropdown for selecting a session
        JLabel sessionLabel = new JLabel("Select Session:");
        JComboBox<String> sessionDropdown = new JComboBox<>();
        List<SessionDTO> registeredSessions = manager.getRegisteredSessionsForAttendee(attendee.getId());
        for (SessionDTO session : registeredSessions) {
            sessionDropdown.addItem(session.getName() + " (Speaker: " + session.getSpeaker() + ")");
        }

        // Rating slider
        JLabel ratingLabel = new JLabel("Rating (1-5):");
        JSlider ratingSlider = new JSlider(1, 5, 3); // Range: 1 to 5, Default: 3
        ratingSlider.setMajorTickSpacing(1);
        ratingSlider.setPaintTicks(true);
        ratingSlider.setPaintLabels(true);

        // Comments text area
        JTextArea commentsArea = new JTextArea(5, 20);
        commentsArea.setBorder(BorderFactory.createTitledBorder("Comments"));

        // Add components to the feedback form
        feedbackFormPanel.add(sessionLabel);
        feedbackFormPanel.add(sessionDropdown);
        feedbackFormPanel.add(ratingLabel);
        feedbackFormPanel.add(ratingSlider);
        feedbackFormPanel.add(commentsArea);

        // Submit button
        JButton submitButton = new JButton("Submit Feedback");
        submitButton.addActionListener(e -> {
            if (sessionDropdown.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Please select a session.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String selectedSession = (String) sessionDropdown.getSelectedItem();
            int sessionIndex = sessionDropdown.getSelectedIndex();
            SessionDTO session = registeredSessions.get(sessionIndex);
            int rating = ratingSlider.getValue();
            String comments = commentsArea.getText();

            try {
                manager.submitFeedback(attendee.getId(), comments, rating);
                JOptionPane.showMessageDialog(this, "Feedback submitted successfully for " + session.getName() + "!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Failed to submit feedback. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(feedbackFormPanel, BorderLayout.CENTER);
        panel.add(submitButton, BorderLayout.SOUTH);

        return panel;
    }

    // Feature 4: View Registered Sessions Tab
    private JPanel createRegisteredSessionsPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel headerLabel = new JLabel("Your Registered Sessions", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(headerLabel, BorderLayout.NORTH);

        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Speaker", "Room", "Date"}, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Load registered sessions into table
        List<SessionDTO> registeredSessions = manager.getRegisteredSessionsForAttendee(attendee.getId());
        for (SessionDTO session : registeredSessions) {
            tableModel.addRow(new Object[]{
                    session.getSessionId(),
                    session.getName(),
                    session.getSpeaker(),
                    session.getRoom(),
                    session.getDateTime()
            });
        }

        JButton cancelButton = new JButton("Cancel Registration");
        cancelButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String sessionId = (String) tableModel.getValueAt(selectedRow, 0);
                // Add cancel logic in manager
                JOptionPane.showMessageDialog(this, "Cancelled registration for session " + sessionId, "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(cancelButton, BorderLayout.SOUTH);
        return panel;
    }

    // Feature 5: Certificates Tab
    private JPanel createCertificatesPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel headerLabel = new JLabel("Your Certificates", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(headerLabel, BorderLayout.NORTH);

        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"Session", "Certificate"}, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Load certificates (mock example for now)
        tableModel.addRow(new Object[]{"Session 1", "Download Link"});
        tableModel.addRow(new Object[]{"Session 2", "Download Link"});

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    // Feature 6: Notifications Tab
    private JPanel createNotificationsPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel headerLabel = new JLabel("Notifications", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(headerLabel, BorderLayout.NORTH);

        JTextArea notificationsArea = new JTextArea();
        notificationsArea.setEditable(false);
        notificationsArea.setText("No new notifications."); // Replace with dynamic notifications
        panel.add(new JScrollPane(notificationsArea), BorderLayout.CENTER);

        return panel;
    }

    // Feature 7: Feedback History Tab
    private JPanel createFeedbackHistoryPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel headerLabel = new JLabel("Your Feedback History", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(headerLabel, BorderLayout.NORTH);

        JTextArea feedbackHistoryArea = new JTextArea();
        feedbackHistoryArea.setEditable(false);
        feedbackHistoryArea.setText("No feedback submitted yet."); // Replace with dynamic feedback
        panel.add(new JScrollPane(feedbackHistoryArea), BorderLayout.CENTER);

        return panel;
    }
}
