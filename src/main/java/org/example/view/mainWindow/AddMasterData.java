package org.example.view.mainWindow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AddMasterData extends JDialog {

    private JTextField idField;
    private JTextField nameField;
    private JTextField addressField;
    private JTextField roomsField;
    private JTextField bedsField;
    private JButton saveButton;
    private JButton cancelButton;

    private DefaultTableModel tableModel;

    public AddMasterData(DefaultTableModel model) {
        this.tableModel = model;

        setTitle("Neues Hotel hinzufügen");
        setModal(true);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Label + Input für ID
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        idField = new JTextField();
        add(idField, gbc);

        // Name
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField();
        add(nameField, gbc);

        // Adresse
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Adresse:"), gbc);
        gbc.gridx = 1;
        addressField = new JTextField();
        add(addressField, gbc);

        // Rooms
        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Rooms:"), gbc);
        gbc.gridx = 1;
        roomsField = new JTextField();
        add(roomsField, gbc);

        // Beds
        gbc.gridx = 0; gbc.gridy = 4;
        add(new JLabel("Beds:"), gbc);
        gbc.gridx = 1;
        bedsField = new JTextField();
        add(bedsField, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel();
        saveButton = new JButton("Speichern");
        cancelButton = new JButton("Abbrechen");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(buttonPanel, gbc);

        // Button-Logik
        saveButton.addActionListener(this::saveHotel);
        cancelButton.addActionListener(e -> dispose());
    }

    private void saveHotel(ActionEvent e) {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            String name = nameField.getText().trim();
            String address = addressField.getText().trim();
            int rooms = Integer.parseInt(roomsField.getText().trim());
            int beds = Integer.parseInt(bedsField.getText().trim());

            // Neue Zeile zur Tabelle hinzufügen
            tableModel.addRow(new Object[]{id, name, address, rooms, beds});
            dispose(); // Fenster schließen
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Bitte gib gültige Zahlen für ID, Rooms und Beds ein.",
                    "Ungültige Eingabe", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Maria Malik
        panel1 = new JPanel();

        //======== panel1 ========
        {
            panel1.setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new
            javax. swing. border. EmptyBorder( 0, 0, 0, 0) , "JF\u006frmDesi\u0067ner Ev\u0061luatio\u006e", javax
            . swing. border. TitledBorder. CENTER, javax. swing. border. TitledBorder. BOTTOM, new java
            .awt .Font ("Dialo\u0067" ,java .awt .Font .BOLD ,12 ), java. awt
            . Color. red) ,panel1. getBorder( )) ); panel1. addPropertyChangeListener (new java. beans.
            PropertyChangeListener( ){ @Override public void propertyChange (java .beans .PropertyChangeEvent e) {if ("borde\u0072" .
            equals (e .getPropertyName () )) throw new RuntimeException( ); }} );

            GroupLayout panel1Layout = new GroupLayout(panel1);
            panel1.setLayout(panel1Layout);
            panel1Layout.setHorizontalGroup(
                panel1Layout.createParallelGroup()
                    .addGap(0, 540, Short.MAX_VALUE)
            );
            panel1Layout.setVerticalGroup(
                panel1Layout.createParallelGroup()
                    .addGap(0, 480, Short.MAX_VALUE)
            );
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Maria Malik
    private JPanel panel1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
