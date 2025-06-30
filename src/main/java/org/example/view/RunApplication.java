package org.example.view;

import org.example.view.mainWindow.noe_to_logo;

import javax.swing.*;

public class RunApplication {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            noe_to_logo frame = new noe_to_logo();
            frame.setTitle("Lower Austria Tourist Portal");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
