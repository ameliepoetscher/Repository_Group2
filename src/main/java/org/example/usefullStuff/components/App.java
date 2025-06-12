package org.example.usefullStuff.components;

import javax.swing.*;

public class App 
{
    public App() {
        JFrame frame = new JFrame("Bild s");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        frame.setLayout(null);


        ImageIcon icon = new ImageIcon(App.class.getResource("/bilder/flower.png"));

        JLabel bildLabel = new JLabel(icon);
        JLabel textLabel = new JLabel("Das ist ein Bild");

        // Position festlegen (Größe ergibt sich automatisch aus dem Bild)
        bildLabel.setBounds(50, 50, icon.getIconWidth(), icon.getIconHeight());
      textLabel.setBounds(280, 222, 99, 50);

      frame.add(bildLabel);
      frame.add(textLabel);
        // Bild und Text zur JFrame hinzufügen
        frame.setVisible(true);
    }


    public static void main(String[] args) {
        new App();
    }
}
