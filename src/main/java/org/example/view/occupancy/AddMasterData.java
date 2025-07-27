package org.example.view.occupancy;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 ** Die Klasse AddMasterData öffnet ein kleines Fenster, in dem man neue Hoteldaten eingeben kann.
 *  * Die Daten werden dann zur Tabelle hinzugefügt.
 *  *
 *  * In diesem Fenster gibt es Textfelder zum Ausfüllen und Schaltflächen zum Speichern oder Abbrechen.
 *  *
 *  * Die Klasse baut auf JDialog auf und sorgt dafür, dass man erst dieses Fenster schließen muss,
 *  * bevor man im Hauptfenster weiterarbeiten kann.
 */
public class AddMasterData extends JDialog { //modaler Dialogfenster

    private final DefaultTableModel tableModel; //Datenmodell der Tabelle, zu der neue Hoteldaten hinzugefügt werden sollen

    private final Map<String, JTextField> fields = new LinkedHashMap<>(); //Map, die Beschriftungen den dazugehörigen Eingabefeldern zuordnet, linkedHashMap für die Reihenfolge

    public AddMasterData(DefaultTableModel model) { // Konstruktor
        this.tableModel = model;

        configureDialog(); // Grundeigenschaften des Dialogs
        createForm(); //erstellt Eingabeformular mit Beschriftungen & Textfelder
        createButtons(); // erzeugt "Speichern" und "Abbrechen"

        pack(); // passt die Größe des Fensters automatisch an den Inhalt an
        setLocationRelativeTo(null); //zentriert Fenster auf Bildschirm
    }

    private void configureDialog() { // Methode
        setTitle("Neues Hotel hinzufügen"); // Titel des Fensters
        setModal(true); // macht den Dilog modal (d.h. setVisible(false))
        setLayout(new GridBagLayout()); // flexibles Layout-Management-System
    }

    private void createForm() { // Methode -> baut Formular dynamisch auf
        GridBagConstraints gbc = new GridBagConstraints(); //Objekt für die Verwaltung von Positionierung und Verhalten von Komponenten (Labels, Textfelder)
        gbc.insets = new Insets(5, 10, 5, 10); //definiert äußeren Abstand für jede Komponente
        gbc.fill = GridBagConstraints.HORIZONTAL; // Komponente solen den verfügbaren horizonatlen Platz in ihrer Zelle ausfüllen
        String[] labels = {"ID:", "Name:", "Adresse:", "Rooms:", "Beds:"}; //Array für Beschriftung der Eingabefelder
        for (int i = 0; i < labels.length; i++) { //durchläuft alle Elemenete des labels-Array
            fields.put(labels[i], addLabelAndTextField(labels[i], i, gbc));
            // eigentliche Aufbau des Formulars -> addLabelAndTextField wird aufgerufen
        }
    }

    private void createButtons() { // erzeugt Schaltfläche "Speichern" und "Abbrechen" und fügt es dem Dialogfenster hinzu
        JPanel buttonPanel = new JPanel(); // Container, um GUI-Komponenten zu gruppieren
        JButton saveButton = new JButton("Speichern"); // erstellt Schaltfläche
        JButton cancelButton = new JButton("Abbrechen"); // erstellt Schaltfläche

        saveButton.addActionListener(this::saveHotel); // beim Klicken dieser Schaltfläche soll eine bestimmte Aktion ausgeführt werden
        // dabei wird saveHotel ausgerufen, die die eingegebenen Daten speichert

        cancelButton.addActionListener(e -> dispose());
        // dispose() wird aufgerufen - Fenster wird geschlossen, ohne dass die Daten gespeichert werden

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        // fügt beide erstellten Schaltflächen dem buttonPanel hinzu

        GridBagConstraints gbc = new GridBagConstraints(); // Obejkt für die Posiitionierung von Komponenten
        gbc.gridx = 0;
        gbc.gridy = fields.size();
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        //PLatzierung des Panels
        add(buttonPanel, gbc); // fügt buttonPanel zum JDialog (Schaltfläche wird zum Hauptcontainer hinzugefüft)
    }

    private JTextField addLabelAndTextField(String labelText, int y, GridBagConstraints gbc) { //Hilfsmethode - TextField wird dem dazugehörigen Label richtig positioniert
        gbc.gridx = 0;
        gbc.gridy = y;
        add(new JLabel(labelText), gbc);
        //Positionierung des Labels

        gbc.gridx = 1;
        JTextField textField = new JTextField(15);
        add(textField, gbc);
        return textField;
        //Positionierung und Erstellung des Textfeldes
    }

    private void saveHotel(ActionEvent e) { //Hotel wird gespeichert, sobald der Nuetezr auf "Speichern" klcikt
        try {
            //liest die Eingaben aus den Textfeldern und entfernt Leerzeichen am Anfang/Ende
            int id = Integer.parseInt(fields.get("ID:").getText().trim());
            String name = fields.get("Name:").getText().trim();
            String address = fields.get("Adresse:").getText().trim();
            int rooms = Integer.parseInt(fields.get("Rooms:").getText().trim());
            int beds = Integer.parseInt(fields.get("Beds:").getText().trim());

            //Fügt neue Daten als Zeile zu einem Tabellenmodell hinzu
            tableModel.addRow(new Object[]{id, name, address, rooms, beds});
            //Dialogfenster wird geschlossen
            dispose();
            //Fehlermeldung als Dialogfenster
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Bitte gib gültige Zahlen für ID, Rooms und Beds ein.",
                    "Ungültige Eingabe",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}