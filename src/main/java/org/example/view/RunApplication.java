package org.example.view;

import org.example.view.mainWindow.login;

import javax.swing.*;
import java.util.Locale;


public class RunApplication {
    public static void main(String[] args) {
        //logIn:
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Lower Austria Tourist Portal");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Statt startseite → login
            login loginPanel = new login();
            frame.setContentPane(loginPanel);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

        });






    }
}

