package org.example.view.occupancy;

import org.example.dao.HotelDAO;
import org.example.entity.Hotel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.LinkedHashMap;
import java.util.Map;

/** In hotel_rep
 ** Die Klasse AddMasterData öffnet ein kleines Fenster, in dem man neue Hoteldaten eingeben kann.
 *  * Die Daten werden dann zur Tabelle hinzugefügt.
 *  *
 *  * In diesem Fenster gibt es Textfelder zum Ausfüllen und Schaltflächen zum Speichern oder Abbrechen.
 *  *
 *  * Die Klasse baut auf JDialog auf und sorgt dafür, dass man erst dieses Fenster schließen muss,
 *  * bevor man im Hauptfenster weiterarbeiten kann.
 */
public class AddMasterData extends JDialog { //modales Dialogfenster

    private final DefaultTableModel tableModel; //Datenmodell der Tabelle, zu der neue Hoteldaten hinzugefügt werden sollen

    private final Map<String, JTextField> fields = new LinkedHashMap<>(); //Map, die Beschriftungen den dazugehörigen Eingabefeldern zuordnet, LinkedHashMap für die Reihenfolge

    public AddMasterData(DefaultTableModel model) { // Konstruktor
        this.tableModel = model;

        configureDialog(); // Grundeigenschaften des Dialogs
        createForm(); //erstellt Eingabeformular mit Beschriftungen & Textfeldern
        createButtons(); // erzeugt "Speichern" und "Abbrechen"

        pack(); // passt die Größe des Fensters automatisch an den Inhalt an
        setLocationRelativeTo(null); //zentriert Fenster auf Bildschirm
    }

    private void configureDialog() { // Methode
        setTitle("Add New Hotel"); // Titel des Fensters
        setModal(true); // macht den Dialog modal (d.h. blockiert das Hauptfenster)
        setLayout(new GridBagLayout()); // flexibles Layout-Management-System
    }

    private void createForm() { // Methode -> baut Formular dynamisch auf
        GridBagConstraints gbc = new GridBagConstraints(); //Objekt für die Verwaltung von Positionierung und Verhalten von Komponenten (Labels, Textfelder)
        gbc.insets = new Insets(5, 10, 5, 10); //definiert äußeren Abstand für jede Komponente
        gbc.fill = GridBagConstraints.HORIZONTAL; // Komponenten sollen den verfügbaren horizontalen Platz in ihrer Zelle ausfüllen
        String[] labels = {"ID:", "Name:", "Adresse:", "Rooms:", "Beds:"}; //Array für Beschriftung der Eingabefelder
        for (int i = 0; i < labels.length; i++) { //durchläuft alle Elemente des labels-Array
            fields.put(labels[i], addLabelAndTextField(labels[i], i, gbc));
            // eigentlicher Aufbau des Formulars -> addLabelAndTextField wird aufgerufen
        }
    }

    private void createButtons() { // erzeugt Schaltflächen "Speichern" und "Abbrechen" und fügt sie dem Dialogfenster hinzu
        JPanel buttonPanel = new JPanel(); // Container, um GUI-Komponenten zu gruppieren
        JButton saveButton = new JButton("Save"); // erstellt Schaltfläche
        JButton cancelButton = new JButton("Cancel"); // erstellt Schaltfläche

        saveButton.addActionListener(this::saveHotel); // beim Klicken dieser Schaltfläche wird saveHotel ausgeführt

        cancelButton.addActionListener(e -> dispose());
        // dispose() wird aufgerufen - Fenster wird geschlossen, ohne dass die Daten gespeichert werden

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        // fügt beide erstellten Schaltflächen dem buttonPanel hinzu

        GridBagConstraints gbc = new GridBagConstraints(); // Objekt für die Positionierung von Komponenten
        gbc.gridx = 0;
        gbc.gridy = fields.size();
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        // Platzierung des Panels
        add(buttonPanel, gbc); // fügt buttonPanel zum JDialog (Schaltfläche wird zum Hauptcontainer hinzugefügt)
    }

    private JTextField addLabelAndTextField(String labelText, int y, GridBagConstraints gbc) { //Hilfsmethode - TextField wird dem dazugehörigen Label richtig positioniert
        gbc.gridx = 0;
        gbc.gridy = y;
        add(new JLabel(labelText), gbc); //Positionierung des Labels

        gbc.gridx = 1;
        JTextField textField = new JTextField(15);
        add(textField, gbc); //Positionierung und Erstellung des Textfeldes
        return textField;
    }

    private void saveHotel(ActionEvent e) { //Hotel wird gespeichert, sobald der Nutzer auf "Speichern" klickt
        try {
            //liest die Eingaben aus den Textfeldern und entfernt Leerzeichen am Anfang/Ende
            int id = Integer.parseInt(fields.get("ID:").getText().trim());
            String name = fields.get("Name:").getText().trim();
            String address = fields.get("Adresse:").getText().trim();
            int rooms = Integer.parseInt(fields.get("Rooms:").getText().trim());
            int beds = Integer.parseInt(fields.get("Beds:").getText().trim());

            // Neues Hotelobjekt wird erzeugt
            Hotel hotel = new Hotel();
            hotel.setId(id);
            hotel.setName(name);
            hotel.setAddress(address);
            hotel.setNoRooms(rooms);
            hotel.setNoBeds(beds);

            // Hotel wird über DAO in die Datenbank geschrieben
            HotelDAO.createHotel(hotel);

            // zusätzlich in die Tabelle eingefügt (z. B. für UI-Refresh)
            tableModel.addRow(new Object[]{id, name, address, rooms, beds});

            //Dialogfenster wird geschlossen
            dispose();

        } catch (NumberFormatException ex) {
            //Fehlermeldung als Dialogfenster
            JOptionPane.showMessageDialog(this,
                    "Please enter valid numbers for ID, Rooms and Beds.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
