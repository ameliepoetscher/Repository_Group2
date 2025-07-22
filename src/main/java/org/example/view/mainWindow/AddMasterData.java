package org.example.view.mainWindow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Die Klasse {@code AddMasterData} ist ein modaler Dialog zum Hinzufügen eines neuen Hotels.
 * Sie bietet eine einfache Eingabemaske für ID, Name, Adresse, Zimmeranzahl und Bettenanzahl
 * und speichert die Daten in einem {@link DefaultTableModel}.
 *
 * Funktionen:
 * - Dynamische Erstellung von Eingabefeldern
 * - Validierung von Zahlenfeldern (ID, Zimmer, Betten)
 * - Buttons zum Speichern oder Abbrechen
 *
 * Konstruktor:
 * - {@link #AddMasterData(DefaultTableModel)}: Baut das UI auf und registriert Button-Aktionen.
 *
 * Hilfsmethode:
 * - {@code addLabelAndTextField(String, int, GridBagConstraints)}: Fügt Label und Textfeld hinzu.
 */
public class AddMasterData extends JDialog { // Standard Dialogfenster geerbt von JDialog

    private final DefaultTableModel tableModel;
    // Eine Map, um die Felder zu speichern und leichter darauf zuzugreifen
    private final Map<String, JTextField> fields = new LinkedHashMap<>();

    public AddMasterData(DefaultTableModel model) {
        this.tableModel = model;

        setTitle("Neues Hotel hinzufügen");
        setModal(true);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Erstellt Labels und Textfelder in einer Schleife
        String[] labels = {"ID:", "Name:", "Adresse:", "Rooms:", "Beds:"};
        for (int i = 0; i < labels.length; i++) {
            JTextField textField = addLabelAndTextField(labels[i], i, gbc);
            fields.put(labels[i], textField);
        }

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Speichern");
        JButton cancelButton = new JButton("Abbrechen");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = labels.length;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(buttonPanel, gbc);

        // Button-Logik
        saveButton.addActionListener(this::saveHotel);
        cancelButton.addActionListener(e -> dispose());

        pack(); // Passt die Fenstergröße an den Inhalt an
        setLocationRelativeTo(null);
    }

    /**
     * Eine Hilfsmethode, die ein Label und ein Textfeld zum Dialog hinzufügt.
     */
    private JTextField addLabelAndTextField(String labelText, int y, GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = y;
        add(new JLabel(labelText), gbc);

        gbc.gridx = 1;
        JTextField textField = new JTextField(15); // Eine Breite von 15 Spalten als Standard
        add(textField, gbc);
        return textField;
    }

    private void saveHotel(ActionEvent e) {
        try {
            int id = Integer.parseInt(fields.get("ID:").getText().trim());
            String name = fields.get("Name:").getText().trim();
            String address = fields.get("Adresse:").getText().trim();
            int rooms = Integer.parseInt(fields.get("Rooms:").getText().trim());
            int beds = Integer.parseInt(fields.get("Beds:").getText().trim());

            // Neue Zeile zur Tabelle hinzufügen
            tableModel.addRow(new Object[]{id, name, address, rooms, beds});
            dispose(); // Fenster schließen
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Bitte gib gültige Zahlen für ID, Rooms und Beds ein.",
                    "Ungültige Eingabe", JOptionPane.ERROR_MESSAGE);
        }
    }
}