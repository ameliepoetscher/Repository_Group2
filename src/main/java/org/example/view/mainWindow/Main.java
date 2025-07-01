package org.example.view.mainWindow;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Zeige NOE-TO Logo-Startfenster
        SwingUtilities.invokeLater(() -> {
            noe_to_logo frame = new noe_to_logo();
            frame.setTitle("Lower Austria Tourist Portal");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
