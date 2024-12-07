package view;

import controller.ConferenceManager;
import dto.AttendeeDTO;
import dto.SessionDTO;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;

public class ConferenceGUI extends JFrame {
    private final ConferenceManager manager;

    public ConferenceGUI(ConferenceManager manager) {
        this.manager = manager;

        // Set up the frame
        setTitle("Conference Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());

        // Create components
        JPanel menuPanel = new JPanel(new GridLayout(6, 1, 5, 5));
        JButton registerAttendeeButton = new JButton("Register Attendee");
        JButton viewAttendeesButton = new JButton("View All Attendees");
        JButton createSessionButton = new JButton("Create Session");
        JButton viewSessionsButton = new JButton("View All Sessions");
        JButton submitFeedbackButton = new JButton("Submit Feedback");
        JButton generateCertificateButton = new JButton("Generate Certificate");

        // Add action listeners
        registerAttendeeButton.addActionListener(e -> openRegisterAttendeeDialog());
        viewAttendeesButton.addActionListener(e -> displayAllAttendees());
        createSessionButton.addActionListener(e -> openCreateSessionDialog());
        viewSessionsButton.addActionListener(e -> displayAllSessions());
        submitFeedbackButton.addActionListener(e -> openSubmitFeedbackDialog());
        generateCertificateButton.addActionListener(e -> openGenerateCertificateDialog());

        // Add buttons to the panel
        menuPanel.add(registerAttendeeButton);
        menuPanel.add(viewAttendeesButton);
        menuPanel.add(createSessionButton);
        menuPanel.add(viewSessionsButton);
        menuPanel.add(submitFeedbackButton);
        menuPanel.add(generateCertificateButton);

        // Add components to the frame
        add(new JLabel("Conference Management System", SwingConstants.CENTER), BorderLayout.NORTH);
        add(menuPanel, BorderLayout.CENTER);

        // Display the frame
        setVisible(true);
    }

    private void openRegisterAttendeeDialog() {
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();

        Object[] message = {
                "Name:", nameField,
                "Email:", emailField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Register Attendee", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String email = emailField.getText();
            AttendeeDTO attendee = manager.registerAttendee(name, email);
            JOptionPane.showMessageDialog(this, "Registered successfully! ID: " + attendee.getId());
        }
    }

    private void displayAllAttendees() {
        StringBuilder attendeesText = new StringBuilder("Attendees:\n");
        List<AttendeeDTO> attendees = manager.getAllAttendees();

        for (AttendeeDTO attendee : attendees) {
            attendeesText.append("ID: ").append(attendee.getId())
                    .append(", Name: ").append(attendee.getName())
                    .append(", Email: ").append(attendee.getEmail())
                    .append("\n");
        }

        JOptionPane.showMessageDialog(this, attendeesText.toString(), "All Attendees", JOptionPane.INFORMATION_MESSAGE);
    }

    private void openCreateSessionDialog() {
        JTextField nameField = new JTextField();
        JTextField speakerField = new JTextField();
        JTextField roomField = new JTextField();
        JTextField dateField = new JTextField(); // Example: "2025-01-01T10:00"

        Object[] message = {
                "Session Name:", nameField,
                "Speaker:", speakerField,
                "Room:", roomField,
                "Date (e.g., 2025-01-01T10:00):", dateField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Create Session", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText();
                String speaker = speakerField.getText();
                String room = roomField.getText();
                LocalDateTime dateTime = LocalDateTime.parse(dateField.getText());

                SessionDTO session = manager.createSession(name, speaker, dateTime, room);
                JOptionPane.showMessageDialog(this, "Session created successfully! ID: " + session.getSessionId());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void displayAllSessions() {
        StringBuilder sessionsText = new StringBuilder("Sessions:\n");
        List<SessionDTO> sessions = manager.getAllSessions();

        for (SessionDTO session : sessions) {
            sessionsText.append("ID: ").append(session.getSessionId())
                    .append(", Name: ").append(session.getName())
                    .append(", Speaker: ").append(session.getSpeaker())
                    .append(", Room: ").append(session.getRoom())
                    .append(", Date: ").append(session.getDateTime())
                    .append("\n");
        }

        JOptionPane.showMessageDialog(this, sessionsText.toString(), "All Sessions", JOptionPane.INFORMATION_MESSAGE);
    }

    private void openSubmitFeedbackDialog() {
        JTextField attendeeIdField = new JTextField();
        JTextField commentsField = new JTextField();
        JTextField ratingField = new JTextField(); // Example: "1-5"

        Object[] message = {
                "Attendee ID:", attendeeIdField,
                "Comments:", commentsField,
                "Rating (1-5):", ratingField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Submit Feedback", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int attendeeId = Integer.parseInt(attendeeIdField.getText());
                String comments = commentsField.getText();
                int rating = Integer.parseInt(ratingField.getText());

                manager.submitFeedback(attendeeId, comments, rating);
                JOptionPane.showMessageDialog(this, "Feedback submitted successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void openGenerateCertificateDialog() {
        JTextField attendeeIdField = new JTextField();
        JTextField conferenceNameField = new JTextField();

        Object[] message = {
                "Attendee ID:", attendeeIdField,
                "Conference Name:", conferenceNameField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Generate Certificate", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int attendeeId = Integer.parseInt(attendeeIdField.getText());
                String conferenceName = conferenceNameField.getText();

                String result = manager.generateCertificate(attendeeId, conferenceName);
                JOptionPane.showMessageDialog(this, result);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
