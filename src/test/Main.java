package test;

import view.LoginPage;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Launch the LoginPage on the Event Dispatch Thread
        SwingUtilities.invokeLater(LoginPage::new);
    }
}
