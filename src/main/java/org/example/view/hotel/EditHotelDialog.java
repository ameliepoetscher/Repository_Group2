package org.example.view.hotel;

import org.example.entity.Hotel;

import javax.swing.*;
import java.awt.*;

/**
 * Dialog zum Bearbeiten/Hinzufügen der Stammdaten eines Hotels (dateibasiert).
 * Schreibt Änderungen NUR ins übergebene Hotel-Objekt.
 * Speichern der Datei erledigt der Aufrufer (Handler).
 */
public class EditHotelDialog extends JDialog {

    private boolean saved = false;

    private JTextField idField;
    private JComboBox<String> categoryBox;
    private JTextField nameField;
    private JTextField addressField;
    private JTextField cityField;
    private JTextField cityCodeField; // PLZ
    private JTextField roomsField;
    private JTextField bedsField;

    /** Beibehaltung alter Aufrufe: Edit-Standard */
    public EditHotelDialog(JFrame parent, Hotel hotel) {
        this(parent, hotel, false);
    }

    /**
     * @param isNew true = "Add Master Data", false = "Edit Master Data"
     */
    public EditHotelDialog(JFrame parent, Hotel hotel, boolean isNew) {
        super(parent, isNew ? "Add Master Data" : "Edit Master Data", true);
        setLayout(new BorderLayout());

        JPanel fields = new JPanel(new GridLayout(8, 2, 10, 10));
        fields.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // ID (read-only)
        fields.add(new JLabel("Hotel ID:"));
        idField = new JTextField(String.valueOf(hotel.getId()));
        idField.setEditable(false);
        fields.add(idField);

        // Kategorie (Sterne)
        fields.add(new JLabel("Category:"));
        categoryBox = new JComboBox<>(new String[]{"*", "**", "***", "****", "*****"});
        String cat = safe(hotel.getCategory());
        categoryBox.setSelectedItem(cat.isEmpty() ? "*" : cat);
        fields.add(categoryBox);

        // Name
        fields.add(new JLabel("Hotel Name:"));
        nameField = new JTextField(safe(hotel.getName()));
        fields.add(nameField);

        // Adresse
        fields.add(new JLabel("Address:"));
        addressField = new JTextField(safe(hotel.getAddress()));
        fields.add(addressField);

        // City
        fields.add(new JLabel("City:"));
        cityField = new JTextField(safe(hotel.getCity()));
        fields.add(cityField);

        // PLZ
        fields.add(new JLabel("PLZ:"));
        cityCodeField = new JTextField(safe(hotel.getCityCode()));
        fields.add(cityCodeField);

        // Zimmer
        fields.add(new JLabel("Number of Rooms:"));
        roomsField = new JTextField(String.valueOf(hotel.getNoRooms()));
        fields.add(roomsField);

        // Betten
        fields.add(new JLabel("Number of Beds:"));
        bedsField = new JTextField(String.valueOf(hotel.getNoBeds()));
        fields.add(bedsField);

        // Buttons
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveBtn = new JButton("Save");
        JButton cancelBtn = new JButton("Back to Hotel List");

        saveBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String address = addressField.getText().trim();
            String city = cityField.getText().trim();
            String cityCode = cityCodeField.getText().trim();

            if (name.isEmpty()) { warn("Please enter a hotel name!"); nameField.requestFocus(); return; }
            if (address.isEmpty()) { warn("Please enter an address!"); addressField.requestFocus(); return; }
            if (city.isEmpty()) { warn("Please enter a city!"); cityField.requestFocus(); return; }
            if (cityCode.isEmpty()) { warn("Please enter a postal code (PLZ)!"); cityCodeField.requestFocus(); return; }

            int rooms, beds;
            try {
                rooms = Integer.parseInt(roomsField.getText().trim());
                beds  = Integer.parseInt(bedsField.getText().trim());
                if (rooms < 0 || beds < 0) {
                    warn("Rooms and beds must be non-negative numbers.");
                    return;
                }
            } catch (NumberFormatException ex) {
                warn("Please enter valid whole numbers for rooms and beds!");
                return;
            }

            // Änderungen ins Modellobjekt schreiben
            hotel.setCategory((String) categoryBox.getSelectedItem());
            hotel.setName(name);
            hotel.setAddress(address);
            hotel.setCity(city);
            hotel.setCityCode(cityCode);
            hotel.setNoRooms(rooms);
            hotel.setNoBeds(beds);

            saved = true;
            dispose();
        });

        cancelBtn.addActionListener(e -> { saved = false; dispose(); });

        buttons.add(saveBtn);
        buttons.add(cancelBtn);

        add(fields, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);

        getRootPane().setDefaultButton(saveBtn); // Enter = Save
        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    public boolean isSaved() { return saved; }

    private static String safe(String s) { return s == null ? "" : s; }

    private void warn(String msg) { JOptionPane.showMessageDialog(this, msg); }
}
