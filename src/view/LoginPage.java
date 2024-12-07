package view;

import controller.ConferenceManager;
import dto.AttendeeDTO;
import repositories.*;
import service.*;
import domain.User;
import enums.Role;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends JFrame {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final UserRepository userRepository;
    private final AttendeeService attendeeService;

    public LoginPage() {
        userRepository = new UserRepository();
        attendeeService = new AttendeeService(new AttendeeRepository());

        setTitle("Login Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Login to Conference Management", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        usernameField = new JTextField();
        passwordField = new JPasswordField();

        formPanel.add(usernameLabel);
        formPanel.add(usernameField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register as Attendee");

        loginButton.addActionListener(new LoginButtonListener());
        registerButton.addActionListener(new RegisterButtonListener());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        add(titleLabel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            User user = userRepository.authenticate(username, password);
            if (user != null) {
                JOptionPane.showMessageDialog(LoginPage.this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                openHomePage(user);
            } else {
                JOptionPane.showMessageDialog(LoginPage.this, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void openHomePage(User user) {
            SessionService sessionService = new SessionService(new SessionRepository());
            FeedbackService feedbackService = new FeedbackService(new FeedbackRepository());
            CertificateService certificateService = new CertificateService(new CertificateRepository());

            ConferenceManager manager = new ConferenceManager(sessionService, feedbackService, certificateService);

            if (user.getRole() == Role.ADMIN) {
                new AdminHomePage(manager);
            } else if (user.getRole() == Role.ATTENDEE) {
                AttendeeDTO attendee = attendeeService.getAttendeeByUsername(user.getUsername());
                if (attendee != null) {
                    new AttendeeHomePage(manager, attendee);
                } else {
                    JOptionPane.showMessageDialog(LoginPage.this, "Attendee not found!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if (user.getRole() == Role.SPEAKER) {
                new SpeakerHomePage(manager);
            }
        }
    }

    private class RegisterButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(LoginPage.this, "Please fill in both fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            userRepository.registerAttendee(username, password);
            JOptionPane.showMessageDialog(LoginPage.this, "Registration successful! You can now log in.", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
