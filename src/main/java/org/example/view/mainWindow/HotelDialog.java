package org.example.view.mainWindow;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HotelDialog extends JDialog {
    private boolean saved = false;
    private JTextField[] fields;
    private JComboBox<String> attributeBox;
    private JTextField addNewAttrField;

    /**
     * @param parent     Das aufrufende Frame
     * @param data       Die zu bearbeitenden Hoteldaten (null, wenn neu)
     * @param isEdit     true = Edit, false = Add
     * @param hotelTable Die JTable aller Hotels (nur für automatische ID-Vergabe notwendig, sonst null)
     */
    public HotelDialog(JFrame parent, Object[] data, boolean isEdit, JTable hotelTable) {
        super(parent, isEdit ? "Edit Hotel" : "Add Hotel", true);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        String[] labels = {"ID:", "Category:", "Name:", "Adresse:", "City:", "PLZ:", "Rooms:", "Beds:"};
        fields = new JTextField[8];

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0; gbc.gridy = i;
            add(new JLabel(labels[i]), gbc);
            gbc.gridx = 1;
            fields[i] = new JTextField(15);

            // --- ID-Handling ---
            if (i == 0) {
                fields[0].setEditable(false); // ID nie editierbar
                if (!isEdit) {
                    // Automatisch neue ID vergeben (max + 1)
                    int maxId = 0;
                    if (hotelTable != null) {
                        for (int row = 0; row < hotelTable.getRowCount(); row++) {
                            try {
                                int id = Integer.parseInt(hotelTable.getValueAt(row, 0).toString());
                                if (id > maxId) maxId = id;
                            } catch (Exception ex) {
                                // Ignoriere Fehlerhafte IDs
                            }
                        }
                    }
                    fields[0].setText(String.valueOf(maxId + 1));
                } else if (data != null) {
                    fields[0].setText(data[0].toString());
                }
            } else if (isEdit && data != null) {
                fields[i].setText(data[i].toString());
            }
            add(fields[i], gbc);
        }

        // --- Attribute Dropdown ---
        gbc.gridy++;
        gbc.gridx = 0;
        add(new JLabel("Attribute:"), gbc);
        gbc.gridx = 1;
        attributeBox = new JComboBox<>();
        refreshAttributes();
        if (isEdit && data != null && data.length >= 9) {
            attributeBox.setSelectedItem(data[8]);
        }
        add(attributeBox, gbc);

        // --- Feld für neues Attribut (zunächst versteckt) ---
        gbc.gridy++;
        gbc.gridx = 1;
        addNewAttrField = new JTextField(15);
        addNewAttrField.setVisible(false);
        add(addNewAttrField, gbc);

        attributeBox.addActionListener(e -> {
            if ("Add new attribute...".equals(attributeBox.getSelectedItem())) {
                addNewAttrField.setVisible(true);
                pack();
            } else {
                addNewAttrField.setVisible(false);
                pack();
            }
        });

        // --- Buttons ---
        gbc.gridy++;
        gbc.gridx = 0;
        JButton saveBtn = new JButton("Ok");
        JButton cancelBtn = new JButton("Cancel");
        add(saveBtn, gbc);
        gbc.gridx = 1;
        add(cancelBtn, gbc);

        saveBtn.addActionListener(e -> {
            if (addNewAttrField.isVisible() && !addNewAttrField.getText().trim().isEmpty()) {
                HotelAttributeManager.addAttribute(addNewAttrField.getText().trim());
                attributeBox.insertItemAt(addNewAttrField.getText().trim(), attributeBox.getItemCount() - 1);
                attributeBox.setSelectedItem(addNewAttrField.getText().trim());
            }
            saved = true;
            setVisible(false);
        });
        cancelBtn.addActionListener(e -> setVisible(false));
        pack();
        setLocationRelativeTo(parent);
    }

    private void refreshAttributes() {
        attributeBox.removeAllItems();
        List<String> attrs = HotelAttributeManager.getAttributes();
        for (String attr : attrs) {
            attributeBox.addItem(attr);
        }
        attributeBox.addItem("Add new attribute...");
    }

    public boolean isSaved() {
        return saved;
    }

    public Object[] getHotelData() {
        Object[] data = new Object[9];
        for (int i = 0; i < 8; i++) data[i] = fields[i].getText();
        data[8] = attributeBox.getSelectedItem();
        return data;
    }
}
