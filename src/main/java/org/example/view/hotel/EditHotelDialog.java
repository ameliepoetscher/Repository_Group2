package org.example.view.hotel;

import org.example.dao.HotelDAO;
import org.example.entity.Hotel;

import javax.swing.*;
import java.awt.*;

/**
 * In hotel_rep
 * Ein Dialogfenster zum Bearbeiten der Daten eines Hotels
 *
 * Dieses Fenster zeigt eine grafische Oberfläche, mit der man Stammdaten wie
 * Name, Adresse, Anzahl der Zimmer und Betten eines ausgewählten Hotels ändern kann.
 *
 * Als Eingabe bekommt die Klasse das übergeordnete Fenster (JFrame) und das Hotel-Objekt,
 * das bearbeitet werden soll.
 */
public class EditHotelDialog extends JDialog {
    private boolean saved = false;

    private JTextField idField;
    private JTextField nameField;
    private JTextField addressField;
    private JTextField roomsField;
    private JTextField bedsField;

    public EditHotelDialog(JFrame parent, Hotel hotel) {
        super(parent, "Edit Master Data", true);
        setLayout(new BorderLayout());

        // Eingabefelder
        JPanel fieldsPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // Hotel-ID (read-only)
        fieldsPanel.add(new JLabel("Hotel ID:"));
        idField = new JTextField(String.valueOf(hotel.getId()));
        idField.setEditable(false);
        fieldsPanel.add(idField);

        // Hotel-Name
        fieldsPanel.add(new JLabel("Hotel Name:"));
        nameField = new JTextField(hotel.getName());
        fieldsPanel.add(nameField);

        // Adresse
        fieldsPanel.add(new JLabel("Address:"));
        addressField = new JTextField(hotel.getAddress());
        fieldsPanel.add(addressField);

        // Zimmeranzahl
        fieldsPanel.add(new JLabel("Number of Rooms:"));
        roomsField = new JTextField(String.valueOf(hotel.getNoRooms()));
        fieldsPanel.add(roomsField);

        // Bettenanzahl
        fieldsPanel.add(new JLabel("Number of Beds:"));
        bedsField = new JTextField(String.valueOf(hotel.getNoBeds()));
        fieldsPanel.add(bedsField);

        // Button-Bereich
        JPanel buttonsPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        JButton backButton = new JButton("Back to Hotel List");

        // Speichern-Button mit DB-Anbindung
        saveButton.addActionListener(e -> {
            hotel.setName(nameField.getText());
            hotel.setAddress(addressField.getText());
            try {
                hotel.setNoRooms(Integer.parseInt(roomsField.getText()));
                hotel.setNoBeds(Integer.parseInt(bedsField.getText()));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers for rooms and beds!");
                return;
            }

            //  Datenbank-Update
            HotelDAO.updateHotel(hotel);

            saved = true;
            JOptionPane.showMessageDialog(this, "Saved successfully!");
            setVisible(false);
        });

        // Zurück-Button
        backButton.addActionListener(e -> setVisible(false));

        buttonsPanel.add(saveButton);
        buttonsPanel.add(backButton);

        // Komponenten zusammenbauen
        add(fieldsPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
    }

    public boolean isSaved() {
        return saved;
    }
}