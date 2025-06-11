package org.example.usefullStuff.components;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimpleTableApp {


    public static void main(String[] args) {
        // JFrame erstellen
        JFrame frame = new JFrame("Simple JTable Example");
        frame.setSize(500, 400);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // JTable Daten und Spalten definieren
        String[] columnNames = {"ID", "Name", "Ort"};
        Object[][] data = {
                {1, "Hotel Alpha", "Wien"},
                {2, "Hotel Beta", "Graz"},
                {3, "Hotel Gamma", "Salzburg"}
        };

        // JTable und Modell erstellen
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 20, 440, 200);

        // Button erstellen
        JButton button = new JButton("Zeile bearbeiten");
        button.setBounds(20, 240, 200, 30);

        // Button-ActionListener hinzufügen
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    // Zeileninhalt abrufen
                    Object id = model.getValueAt(selectedRow, 0);
                    Object name = model.getValueAt(selectedRow, 1);
                    Object ort = model.getValueAt(selectedRow, 2);

                    // JDialog erstellen
                    JDialog dialog = new JDialog(frame, "Zeile bearbeiten", true);
                    dialog.setSize(300, 250);
                    dialog.setLayout(null);

                    // Labels und Textfelder
                    JLabel idLabel = new JLabel("ID:");
                    idLabel.setBounds(20, 20, 80, 25);
                    JTextField idField = new JTextField(id.toString());
                    idField.setBounds(100, 20, 150, 25);
                    idField.setEditable(false);

                    JLabel nameLabel = new JLabel("Name:");
                    nameLabel.setBounds(20, 60, 80, 25);
                    JTextField nameField = new JTextField(name.toString());
                    nameField.setBounds(100, 60, 150, 25);

                    JLabel ortLabel = new JLabel("Ort:");
                    ortLabel.setBounds(20, 100, 80, 25);
                    JTextField ortField = new JTextField(ort.toString());
                    ortField.setBounds(100, 100, 150, 25);

                    // Ändern-Button
                    JButton updateButton = new JButton("Ändern");
                    updateButton.setBounds(100, 150, 100, 30);

                    updateButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Änderungen übernehmen
                            model.setValueAt(nameField.getText(), selectedRow, 1);
                            model.setValueAt(ortField.getText(), selectedRow, 2);

                            System.out.println("Geänderte Zeile: ID=" + id + ", Name=" + nameField.getText() + ", Ort=" + ortField.getText());

                            dialog.dispose();
                        }
                    });

                    // Komponenten zum Dialog hinzufügen
                    dialog.add(idLabel);
                    dialog.add(idField);
                    dialog.add(nameLabel);
                    dialog.add(nameField);
                    dialog.add(ortLabel);
                    dialog.add(ortField);
                    dialog.add(updateButton);

                    dialog.setVisible(true);

                } else {
                    JOptionPane.showMessageDialog(frame,
                            "Keine Zeile ausgewählt.",
                            "Fehler",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // Komponenten zum Frame hinzufügen
        frame.add(scrollPane);
        frame.add(button);

        // Frame sichtbar machen
        frame.setVisible(true);
    }
}
