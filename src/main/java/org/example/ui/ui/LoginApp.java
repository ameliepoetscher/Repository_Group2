package org.example.ui.ui;

import org.example.dao.UserDAO;
import org.example.entity.Role;

import javax.swing.*;
import java.awt.*;

public class LoginApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginApp().createLoginUI());
    }

    private void createLoginUI() {
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 250);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2));

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();

        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();

        JLabel roleLabel = new JLabel("Role:");
        JComboBox<Role> roleBox = new JComboBox<>(Role.values());

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        panel.add(userLabel);
        panel.add(userField);
        panel.add(passLabel);
        panel.add(passField);
        panel.add(roleLabel);
        panel.add(roleBox);
        panel.add(loginButton);
        panel.add(registerButton);

        frame.add(panel);
        frame.setVisible(true);

        loginButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());

            if (UserDAO.authenticateAndGetUser(username, password) == null) {
                JOptionPane.showMessageDialog(frame, "Login erfolgreich!");
                frame.dispose();
            } else {
                JOptionPane.showMessageDialog(frame, "Login fehlgeschlagen.", "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        });

        registerButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            Role role = (Role) roleBox.getSelectedItem();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Bitte alle Felder ausfüllen.", "Fehler", JOptionPane.ERROR_MESSAGE);
                return;
            }

            UserDAO.createUser(username, password);
            JOptionPane.showMessageDialog(frame, "Benutzer erstellt!");
        });
    }
}