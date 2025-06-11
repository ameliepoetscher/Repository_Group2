package org.example.view.mainWindow;
import javax.swing.*;

public class Main {
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

