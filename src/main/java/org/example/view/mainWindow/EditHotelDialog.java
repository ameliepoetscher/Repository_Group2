package org.example.view.mainWindow;

import org.example.entity.Hotel;

import javax.swing.*;
import java.awt.*;

public class EditHotelDialog extends JDialog {
    // Dieses Feld merkt, ob gespeichert wurde
    private boolean saved = false;

    private JTextField idField;
    private JTextField nameField;
    private JTextField addressField;
    private JTextField roomsField;
    private JTextField bedsField;

    // Dialog zum Bearbeiten eines Hotels
    public EditHotelDialog(JFrame parent, Hotel hotel) {
        super(parent, "Edit Master Data", true);
        setLayout(new BorderLayout());

        // Panel für die Eingabefelder
        JPanel fieldsPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // ID (read-only)
        fieldsPanel.add(new JLabel("Hotel ID:"));
        idField = new JTextField(String.valueOf(hotel.getId()));
        idField.setEditable(false);
        fieldsPanel.add(idField);

        // Name
        fieldsPanel.add(new JLabel("Hotel Name:"));
        nameField = new JTextField(hotel.getName());
        fieldsPanel.add(nameField);

        // Address
        fieldsPanel.add(new JLabel("Address:"));
        addressField = new JTextField(hotel.getAddress());
        fieldsPanel.add(addressField);

        // Number of Rooms
        fieldsPanel.add(new JLabel("Number of Rooms:"));
        roomsField = new JTextField(String.valueOf(hotel.getNoRooms()));
        fieldsPanel.add(roomsField);

        // Number of Beds
        fieldsPanel.add(new JLabel("Number of Beds:"));
        bedsField = new JTextField(String.valueOf(hotel.getNoBeds()));
        fieldsPanel.add(bedsField);

        // Panel für Buttons
        JPanel buttonsPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        JButton backButton = new JButton("Back to Hotel List");

        // Wenn auf "Save" geklickt wird: Werte speichern und Merker setzen
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
            saved = true; // <<--- WICHTIG: Merker setzen
            JOptionPane.showMessageDialog(this, "Saved successfully!");
            setVisible(false);
        });

        // Wenn auf "Back" geklickt wird: Dialog einfach schließen (ohne speichern)
        backButton.addActionListener(e -> setVisible(false));

        buttonsPanel.add(saveButton);
        buttonsPanel.add(backButton);

        // Alles zusammenbauen
        add(fieldsPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
    }

    // Getter, um abzufragen, ob gespeichert wurde
    public boolean isSaved() {
        return saved;
    }
}
