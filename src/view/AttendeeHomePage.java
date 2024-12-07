package view;

import controller.ConferenceManager;
import dto.AttendeeDTO;
import dto.SessionDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class AttendeeHomePage extends JFrame {
    private final ConferenceManager manager;
    private final AttendeeDTO attendee;

    public AttendeeHomePage(ConferenceManager manager, AttendeeDTO attendee) {
        this.manager = manager;
        this.attendee = attendee;

        setTitle("Attendee Home Page");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main container
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Welcome label
        JLabel welcomeLabel = new JLabel("Welcome, " + attendee.getName() + "!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        mainPanel.add(welcomeLabel, BorderLayout.NORTH);

        // Tabbed pane for attendee functionalities
        JTabbedPane tabbedPane = new JTabbedPane();

        // Add all tabs
        tabbedPane.addTab("Profile", createProfilePanel());
        tabbedPane.addTab("View All Sessions", createAllSessionsPanel());
        tabbedPane.addTab("Your Schedule", createRegisteredSessionsPanel());
        tabbedPane.addTab("Feedback", createFeedbackPanel());
        tabbedPane.addTab("Certificates", createCertificatesPanel());
        tabbedPane.addTab("Notifications", createNotificationsPanel());
        tabbedPane.addTab("Feedback History", createFeedbackHistoryPanel());

        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        // Set up the frame
        add(mainPanel);
        setVisible(true);
    }

    // Tab: Profile
    private JPanel createProfilePanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1));
        panel.add(new JLabel("Name: " + attendee.getName()));
        panel.add(new JLabel("Email: " + attendee.getEmail()));
        panel.add(new JLabel("ID: " + attendee.getId()));
        return panel;
    }

    // Tab: View All Sessions (with Search and Filters)
    private JPanel createAllSessionsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel headerLabel = new JLabel("Available Sessions", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(headerLabel, BorderLayout.NORTH);

        JTextField searchField = new JTextField();
        searchField.setToolTipText("Search by session name or speaker");
        JButton searchButton = new JButton("Search");

        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Speaker", "Room", "Date"}, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        List<SessionDTO> allSessions = manager.getAllSessions();

        searchButton.addActionListener(e -> {
            String query = searchField.getText().toLowerCase();
            List<SessionDTO> filteredSessions = allSessions.stream()
                    .filter(session -> session.getName().toLowerCase().contains(query) || session.getSpeaker().toLowerCase().contains(query))
                    .collect(Collectors.toList());

            tableModel.setRowCount(0);
            for (SessionDTO session : filteredSessions) {
                tableModel.addRow(new Object[]{
                        session.getSessionId(),
                        session.getName(),
                        session.getSpeaker(),
                        session.getRoom(),
                        session.getDateTime()
                });
            }
        });

        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    // Tab: Your Schedule (with Cancel Registration)
    private JPanel createRegisteredSessionsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel headerLabel = new JLabel("Your Schedule", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(headerLabel, BorderLayout.NORTH);

        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Speaker", "Room", "Date"}, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

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
                int sessionId = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());
                manager.cancelSessionRegistration(attendee.getId(), sessionId);
                tableModel.removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "Registration cancelled for session.");
            } else {
                JOptionPane.showMessageDialog(this, "Please select a session to cancel.");
            }
        });

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(cancelButton, BorderLayout.SOUTH);
        return panel;
    }

    // Tab: Feedback Submission
    private JPanel createFeedbackPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel headerLabel = new JLabel("Submit Feedback", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(headerLabel, BorderLayout.NORTH);

        JPanel feedbackFormPanel = new JPanel();
        feedbackFormPanel.setLayout(new BoxLayout(feedbackFormPanel, BoxLayout.Y_AXIS));

        JLabel sessionLabel = new JLabel("Select Session:");
        JComboBox<String> sessionDropdown = new JComboBox<>();
        List<SessionDTO> registeredSessions = manager.getRegisteredSessionsForAttendee(attendee.getId());
        for (SessionDTO session : registeredSessions) {
            sessionDropdown.addItem(session.getName() + " by " + session.getSpeaker());
        }

        JLabel ratingLabel = new JLabel("Rating (1-5):");
        JSlider ratingSlider = new JSlider(1, 5, 3);
        ratingSlider.setMajorTickSpacing(1);
        ratingSlider.setPaintTicks(true);
        ratingSlider.setPaintLabels(true);

        JTextArea commentsArea = new JTextArea(5, 20);
        commentsArea.setBorder(BorderFactory.createTitledBorder("Comments"));

        JButton submitButton = new JButton("Submit Feedback");
        submitButton.addActionListener(e -> {
            if (sessionDropdown.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Please select a session.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int selectedIndex = sessionDropdown.getSelectedIndex();
            SessionDTO selectedSession = registeredSessions.get(selectedIndex);
            int rating = ratingSlider.getValue();
            String comments = commentsArea.getText();

            try {
                manager.submitFeedback(attendee.getId(), selectedSession.getName(), comments, rating); // Pass all arguments
                JOptionPane.showMessageDialog(this, "Feedback submitted for " + selectedSession.getName() + "!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Failed to submit feedback.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });


        feedbackFormPanel.add(sessionLabel);
        feedbackFormPanel.add(sessionDropdown);
        feedbackFormPanel.add(ratingLabel);
        feedbackFormPanel.add(ratingSlider);
        feedbackFormPanel.add(commentsArea);

        panel.add(feedbackFormPanel, BorderLayout.CENTER);
        panel.add(submitButton, BorderLayout.SOUTH);

        return panel;
    }

    // Tab: Certificates (Download Functionality)
    private JPanel createCertificatesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel headerLabel = new JLabel("Your Certificates", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(headerLabel, BorderLayout.NORTH);

        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"Session Name", "Certificate"}, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Fetch certificates dynamically
        List<String> certificates = manager.getCertificatesForAttendee(attendee.getId());
        for (String certificate : certificates) {
            tableModel.addRow(new Object[]{certificate, "Download"});
        }

        JButton downloadButton = new JButton("Download Selected Certificate");
        downloadButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String certificateContent = table.getValueAt(selectedRow, 0).toString();
                try (FileWriter writer = new FileWriter("Certificate_" + selectedRow + ".txt")) {
                    writer.write(certificateContent);
                    JOptionPane.showMessageDialog(this, "Certificate downloaded successfully!");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Failed to download certificate.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a certificate to download.");
            }
        });

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(downloadButton, BorderLayout.SOUTH);
        return panel;
    }

    // Tab: Notifications
    private JPanel createNotificationsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel headerLabel = new JLabel("Notifications", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(headerLabel, BorderLayout.NORTH);

        JTextArea notificationArea = new JTextArea(10, 30);
        notificationArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(notificationArea);

        // Example: Replace with actual logic to fetch notifications
        notificationArea.setText("Notification 1: Session Update\nNotification 2: Reminder");

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    // Tab: Feedback History
    private JPanel createFeedbackHistoryPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel headerLabel = new JLabel("Your Feedback History", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(headerLabel, BorderLayout.NORTH);

        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"Session", "Rating", "Comments"}, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Fetch feedback dynamically
        List<String[]> feedbackHistory = manager.getFeedbackForAttendee(attendee.getId());
        for (String[] feedback : feedbackHistory) {
            tableModel.addRow(feedback);
        }

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
}
