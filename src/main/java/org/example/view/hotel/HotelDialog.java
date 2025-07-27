package org.example.view.hotel;

import org.example.dao.HotelDAO;
import org.example.entity.Hotel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Dialogfenster für das Hinzufügen oder Bearbeiten eines Hotels, speichert direkt in der Datenbank.
 */
public class HotelDialog extends JDialog {
    private boolean saved = false;
    private final JTextField[] fields = new JTextField[8];
    private final JComboBox<String> attributeBox = new JComboBox<>();
    private final JTextField addNewAttrField = new JTextField(15);

    /**
     * Öffnet den Dialog.
     * @param parent     Das übergeordnete Fenster
     * @param data       Hoteldaten (als Array), oder null für ein neues Hotel
     * @param isEdit     true = Bearbeiten, false = Neu anlegen
     * @param hotelTable JTable (nur für automatische ID-Vergabe, sonst null)
     */
    public HotelDialog(JFrame parent, Object[] data, boolean isEdit, JTable hotelTable) {
        super(parent, isEdit ? "Edit Hotel" : "Add Hotel", true);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);

        String[] labels = {"ID:", "Category:", "Name:", "Adresse:", "City:", "PLZ:", "Rooms:", "Beds:"};

        // Felder erzeugen und ggf. mit bestehenden Werten befüllen
        for (int i = 0; i < labels.length; i++) {
            addField(labels[i], i, gbc);
            if (i == 0) {
                fields[0].setEditable(false);
                fields[0].setText(String.valueOf(getId(data, isEdit, hotelTable)));
            } else if (isEdit && data != null) {
                fields[i].setText(String.valueOf(data[i]));
            }
        }

        // Attribut-Auswahl (mit Manager und Option für neues Attribut)
        addAttributeRow(gbc, isEdit && data != null && data.length >= 9 ? String.valueOf(data[8]) : null);

        // Speicher- und Cancel-Buttons
        JPanel btnPanel = new JPanel();
        JButton saveBtn = new JButton("Save");
        JButton cancelBtn = new JButton("Cancel");
        btnPanel.add(saveBtn);
        btnPanel.add(cancelBtn);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        add(btnPanel, gbc);

        // Speichern: Holt die Eingaben und speichert per DAO in der Datenbank
        saveBtn.addActionListener(e -> {
            // Wenn neues Attribut eingegeben wurde, erst hinzufügen
            if (addNewAttrField.isVisible() && !addNewAttrField.getText().trim().isEmpty()) {
                String newAttr = addNewAttrField.getText().trim();
                HotelAttributeManager.addAttribute(newAttr);
                attributeBox.insertItemAt(newAttr, attributeBox.getItemCount() - 1);
                attributeBox.setSelectedItem(newAttr);
            }

            // Auslesen & Objekt bauen
            Object[] hotelData = getHotelData();
            Hotel hotel = new Hotel();
            hotel.setId(Integer.parseInt(hotelData[0].toString()));
            hotel.setCategory(hotelData[1].toString());
            hotel.setName(hotelData[2].toString());
            hotel.setAddress(hotelData[3].toString());
            hotel.setCity(hotelData[4].toString());
            hotel.setCityCode(hotelData[5].toString());
            hotel.setNoRooms(Integer.parseInt(hotelData[6].toString()));
            hotel.setNoBeds(Integer.parseInt(hotelData[7].toString()));
            hotel.setAttribute(hotelData[8].toString());

            // Speichern/Update in die DB
            if (isEdit) {
                HotelDAO.updateHotel(hotel);   // Das muss es im DAO geben!
            } else {
                HotelDAO.createHotel(hotel);   // So heißt die Methode im DAO!
            }

            saved = true;
            setVisible(false);
        });
        cancelBtn.addActionListener(e -> {
            saved = false;
            setVisible(false);
        });

        pack();
        setLocationRelativeTo(parent);
    }

    /** Fügt ein Label und ein Eingabefeld für einen Hoteldatenpunkt hinzu. */
    private void addField(String label, int index, GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = index;
        add(new JLabel(label), gbc);
        gbc.gridx = 1;
        fields[index] = new JTextField(15);
        add(fields[index], gbc);
    }

    /** Holt die nächste freie Hotel-ID, oder übernimmt die bestehende */
    private int getId(Object[] data, boolean isEdit, JTable table) {
        if (isEdit && data != null)
            return Integer.parseInt(String.valueOf(data[0]));
        int maxId = 0;
        if (table != null) {
            for (int i = 0; i < table.getRowCount(); i++) {
                try {
                    int id = Integer.parseInt(table.getValueAt(i, 0).toString());
                    if (id > maxId) maxId = id;
                } catch (Exception ignored) {}
            }
        }
        return maxId + 1;
    }

    /** Lädt alle Attribute aus dem Manager in das Dropdown + Feld für neue Attribute */
    private void addAttributeRow(GridBagConstraints gbc, String selected) {
        gbc.gridy++;
        gbc.gridx = 0;
        add(new JLabel("Attribute:"), gbc);

        gbc.gridx = 1;
        attributeBox.removeAllItems();

        // Attribute aus dem Manager holen!
        List<String> attrs = HotelAttributeManager.getAttributes();
        for (String attr : attrs) {
            attributeBox.addItem(attr);
        }
        attributeBox.addItem("Add new attribute...");
        if (selected != null) attributeBox.setSelectedItem(selected);
        add(attributeBox, gbc);

        // Textfeld für neues Attribut (am Anfang unsichtbar)
        gbc.gridy++;
        gbc.gridx = 1;
        addNewAttrField.setVisible(false);
        add(addNewAttrField, gbc);

        // Dropdown-Listener: zeigt Textfeld wenn "Add new attribute..." gewählt wird
        attributeBox.addActionListener(e -> {
            boolean isNew = "Add new attribute...".equals(attributeBox.getSelectedItem());
            addNewAttrField.setVisible(isNew);
            pack();
        });
    }

    /** Gibt zurück, ob gespeichert wurde. */
    public boolean isSaved() { return saved; }

    /** Holt die Daten aus allen Eingabefeldern als Array */
    public Object[] getHotelData() {
        Object[] data = new Object[9];
        for (int i = 0; i < 8; i++) data[i] = fields[i].getText();
        data[8] = "Add new attribute...".equals(attributeBox.getSelectedItem())
                ? addNewAttrField.getText() : attributeBox.getSelectedItem();
        return data;
    }
}
