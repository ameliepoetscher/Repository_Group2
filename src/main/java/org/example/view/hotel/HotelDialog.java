package org.example.view.hotel;

import org.example.dao.HotelDAO;
import org.example.entity.Amenity;
import org.example.entity.Hotel;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * In hotel_rep
 * Dialogfenster für das Hinzufügen oder Bearbeiten eines Hotels, speichert direkt in der Datenbank.
 */
public class HotelDialog extends JDialog {
    private boolean saved = false;
    private final JTextField[] fields = new JTextField[8];
    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private final JList<String> attributeList = new JList<>(listModel);
    private final JTextField addNewAttrField = new JTextField(15);

    public HotelDialog(JFrame parent, Object[] data, boolean isEdit, JTable hotelTable) {
        super(parent, isEdit ? "Edit Hotel" : "Add Hotel", true);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);

        String[] labels = {"ID:", "Category:", "Name:", "Adresse:", "City:", "PLZ:", "Rooms:", "Beds:"};

        // Eingabefelder anlegen
        for (int i = 0; i < labels.length; i++) {
            addField(labels[i], i, gbc);
            if (i == 0) {
                fields[0].setEditable(false);
                fields[0].setText(String.valueOf(getId(data, isEdit, hotelTable)));
            } else if (isEdit && data != null) {
                fields[i].setText(String.valueOf(data[i]));
            }
        }

        // Attribute-Mehrfachauswahl
        addAttributeSection(gbc);

        // Buttons
        JPanel btnPanel = new JPanel();
        JButton saveBtn = new JButton("Save");
        JButton cancelBtn = new JButton("Cancel");
        btnPanel.add(saveBtn);
        btnPanel.add(cancelBtn);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        add(btnPanel, gbc);

        // Event: Save
        saveBtn.addActionListener(e -> {
            // Neuen Attributnamen hinzufügen, falls vorhanden
            String newAttr = addNewAttrField.getText().trim();
            if (!newAttr.isEmpty() && !listModel.contains(newAttr)) {
                HotelAttributeManager.getOrCreateAmenities(List.of(newAttr));
                listModel.addElement(newAttr);
                attributeList.setSelectedValue(newAttr, true);
            }

            // Eingabewerte sammeln
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

            // Attribute zuweisen
            List<String> selectedAttr = attributeList.getSelectedValuesList();
            List<Amenity> amenities = HotelAttributeManager.getOrCreateAmenities(selectedAttr);
            hotel.setAmenities(Set.copyOf(amenities));

            // DB speichern
            if (isEdit) {
                HotelDAO.updateHotel(hotel);
            } else {
                HotelDAO.createHotel(hotel); // ✅ richtiger Methodenname

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

    private void addField(String label, int index, GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = index;
        add(new JLabel(label), gbc);
        gbc.gridx = 1;
        fields[index] = new JTextField(15);
        add(fields[index], gbc);
    }

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

    private void addAttributeSection(GridBagConstraints gbc) {
        gbc.gridy++;
        gbc.gridx = 0;
        add(new JLabel("Attributes:"), gbc);

        gbc.gridx = 1;

        // Mehrfachauswahlliste
        attributeList.setVisibleRowCount(4);
        attributeList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        List<String> attrs = HotelAttributeManager.getAllAttributeNames();
        for (String a : attrs) listModel.addElement(a);

        JScrollPane scrollPane = new JScrollPane(attributeList);
        scrollPane.setPreferredSize(new Dimension(150, 80));
        add(scrollPane, gbc);

        // Neues Attributfeld
        gbc.gridy++;
        gbc.gridx = 1;
        addNewAttrField.setToolTipText("Add new attribute");
        add(addNewAttrField, gbc);
    }

    public boolean isSaved() {
        return saved;
    }

    public Object[] getHotelData() {
        Object[] data = new Object[8];
        for (int i = 0; i < 8; i++) data[i] = fields[i].getText();
        return data;
    }
}
