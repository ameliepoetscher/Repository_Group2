package org.example.view.mainWindow;

import javax.swing.*;
import java.awt.*;

public class AddTransactionalDataDialog extends JDialog {
    private boolean saved = false;
    private JComboBox<String> yearCombo;
    private JComboBox<String> monthCombo;
    private JTextField roomField;
    private JTextField bedField;

    public AddTransactionalDataDialog(JFrame parent, int hotelId, String hotelName) {
        super(parent, "Add Transactional Data", true);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Hotel:"), gbc);
        gbc.gridx = 1;
        add(new JLabel(hotelName), gbc);

        gbc.gridx = 0; gbc.gridy++;
        add(new JLabel("Hotel ID:"), gbc);
        gbc.gridx = 1;
        add(new JLabel(String.valueOf(hotelId)), gbc);

        gbc.gridx = 0; gbc.gridy++;
        add(new JLabel("Year:"), gbc);
        gbc.gridx = 1;
        yearCombo = new JComboBox<>();
        yearCombo.addItem("---select---");
        for (int y = 2025; y >= 2000; y--) yearCombo.addItem(String.valueOf(y));
        add(yearCombo, gbc);

        gbc.gridx = 0; gbc.gridy++;
        add(new JLabel("Month:"), gbc);
        gbc.gridx = 1;
        monthCombo = new JComboBox<>();
        monthCombo.addItem("---select---");
        String[] months = { "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December" };
        for (String m : months) monthCombo.addItem(m);
        add(monthCombo, gbc);

        gbc.gridx = 0; gbc.gridy++;
        add(new JLabel("Room Occupancy:"), gbc);
        gbc.gridx = 1;
        roomField = new JTextField(10);
        add(roomField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        add(new JLabel("Bed Occupancy:"), gbc);
        gbc.gridx = 1;
        bedField = new JTextField(10);
        add(bedField, gbc);

        // Buttons
        gbc.gridx = 0; gbc.gridy++;
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        add(saveButton, gbc);
        gbc.gridx = 1;
        add(cancelButton, gbc);

        saveButton.addActionListener(e -> {
            if (!validateInput()) return;
            saved = true;
            setVisible(false);
        });

        cancelButton.addActionListener(e -> setVisible(false));

        pack();
        setLocationRelativeTo(parent);
    }

    private boolean validateInput() {
        if (yearCombo.getSelectedIndex() <= 0 || monthCombo.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(this, "Please select both year and month.");
            return false;
        }
        if (roomField.getText().trim().isEmpty() || bedField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in both occupancy fields.");
            return false;
        }
        try {
            Integer.parseInt(roomField.getText().trim());
            Integer.parseInt(bedField.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Occupancy must be numbers.");
            return false;
        }
        return true;
    }

    // === Getter-Methoden ===

    public boolean isSaved() {
        return saved;
    }

    public int getSelectedYear() {
        return Integer.parseInt((String) yearCombo.getSelectedItem());
    }

    public int getSelectedMonth() {
        return monthCombo.getSelectedIndex(); // January = 1 (weil erstes Element ist "---select---")
    }

    public int getRoomOccupancy() {
        return Integer.parseInt(roomField.getText().trim());
    }

    public int getBedOccupancy() {
        return Integer.parseInt(bedField.getText().trim());
    }
}
