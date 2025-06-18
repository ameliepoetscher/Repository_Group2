/*
 * Created by JFormDesigner on Sun May 18 18:16:41 CEST 2025
 */
package org.example.view.mainWindow;

import org.example.data.txt.HotelFileReader;
import org.example.entity.Hotel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class hotel_rep extends JPanel {
    private List<Hotel> hotels;
    private List<Map<String, Object>> occupancies = new ArrayList<>();
    private Map<Integer, String> lastTransactionMap = new HashMap<>();
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public hotel_rep() {
        String hotelFilePath = "src/main/java/org/example/data/txt/hotels.txt";
        hotels = HotelFileReader.readHotelsFromFile(hotelFilePath);

        initComponents();

        // Set column width for ID in Hotel List
        TableColumnModel cm = table1.getColumnModel();
        cm.getColumn(0).setPreferredWidth(15);

        loadHotelsInTable();
        loadTransactionalTable();

        updateHotelComboBox(); // Wichtig: Initialisiere das Dropdown mit aktuellen Namen

        comboBox4.removeAllItems();
        comboBox4.addItem("---select---");
        for (int y = 2025; y >= 2000; y--) comboBox4.addItem(String.valueOf(y));

        comboBox5.removeAllItems();
        comboBox5.addItem("---select---");
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August",
                "September", "October", "November", "December"};
        for (String month : months) comboBox5.addItem(month);

        comboBox4.addActionListener(e -> filterTransactionalData());
        comboBox5.addActionListener(e -> filterTransactionalData());
        comboBox15.addActionListener(e -> filterTransactionalData());

        ActionListener logout = e -> {
            JFrame top = (JFrame) SwingUtilities.getWindowAncestor(this);
            top.setContentPane(new login());
            top.pack();
            top.setLocationRelativeTo(null);
        };
        button1.addActionListener(logout);
        button3.addActionListener(logout);
        button7.addActionListener(logout);

        ActionListener help = e -> {
            JOptionPane.showMessageDialog(
                    hotel_rep.this,
                    "Welcome to the Lower Austria Tourist Portal!\n\n" +
                            "Here’s how to use this application:\n" +
                            "1. In the \"Hotel List\" tab, you can view and edit master data for all hotels.\n" +
                            "2. In the \"Transactional Data\" tab, you can add and review booking and occupancy data for hotels.\n" +
                            "3. In the \"Transactional Data List\" tab, filter and view hotel occupancy data by year, month, and hotel.\n\n" +
                            "Use the provided buttons to edit data or log out. If you need further assistance, please contact support@example.com.",
                    "Help",
                    JOptionPane.INFORMATION_MESSAGE
            );
        };
        button2.addActionListener(help);
        button6.addActionListener(help);
        button8.addActionListener(help);

        // "Add Transactional Data" Button (Tab 2)
        button4.addActionListener(e -> {
            int row = table2.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Please select a hotel first!");
                return;
            }
            int hotelId = Integer.parseInt(table2.getValueAt(row, 0).toString());
            String hotelName = (String) table2.getValueAt(row, 1);

            AddTransactionalDataDialog dialog = new AddTransactionalDataDialog(
                    (JFrame) SwingUtilities.getWindowAncestor(this), hotelId, hotelName);
            dialog.setVisible(true);

            if (dialog.isSaved()) {
                int year = dialog.getSelectedYear();
                int month = dialog.getSelectedMonth();
                int roomOcc = dialog.getRoomOccupancy();
                int bedOcc = dialog.getBedOccupancy();

                Map<String, Object> occ = new HashMap<>();
                occ.put("hotelId", hotelId);
                occ.put("hotelName", hotelName);
                occ.put("year", year);
                occ.put("month", month);
                occ.put("roomOcc", roomOcc);
                occ.put("bedOcc", bedOcc);
                occupancies.add(occ);

                lastTransactionMap.put(hotelId, LocalDate.now().format(dateFormatter));

                loadHotelsInTable();
                loadTransactionalTable();
                filterTransactionalData();
            }
        });
    }

    // Dropdown für Hotels aktualisieren
    private void updateHotelComboBox() {
        comboBox15.removeAllItems();
        comboBox15.addItem("---select---");
        for (Hotel hotel : hotels) {
            comboBox15.addItem(hotel.getName());
        }
    }

    private void loadHotelsInTable() {
        DefaultTableModel model = new DefaultTableModel(new String[]{
                "ID", "Category", "Hotel Name", "Adresse", "City", "PLZ", "Rooms", "Beds", "Last Reported Transactional Data"
        }, 0);

        for (Hotel hotel : hotels) {
            Object[] rowData = {
                    hotel.getId(),
                    hotel.getCategory(),
                    hotel.getName(),
                    hotel.getAddress(),
                    hotel.getCity(),
                    hotel.getCityCode(),
                    hotel.getNoRooms(),
                    hotel.getNoBeds(),
                    lastTransactionMap.getOrDefault(hotel.getId(), "")
            };
            model.addRow(rowData);
        }
        table1.setModel(model);
    }

    private void loadTransactionalTable() {
        DefaultTableModel model = new DefaultTableModel(new String[]{
                "ID", "Hotel Name", "Room Occupancy", "Bed Occupancy", "Month", "Year"
        }, 0);

        for (Hotel hotel : hotels) {
            Map<String, Object> lastOcc = null;
            for (Map<String, Object> occ : occupancies) {
                if (((int) occ.get("hotelId")) == hotel.getId()) {
                    if (lastOcc == null) {
                        lastOcc = occ;
                    } else {
                        int y1 = (int) lastOcc.get("year");
                        int m1 = (int) lastOcc.get("month");
                        int y2 = (int) occ.get("year");
                        int m2 = (int) occ.get("month");
                        if (y2 > y1 || (y2 == y1 && m2 > m1)) lastOcc = occ;
                    }
                }
            }
            Object[] rowData;
            if (lastOcc == null) {
                rowData = new Object[]{hotel.getId(), hotel.getName(), "", "", "", ""};
            } else {
                rowData = new Object[]{
                        hotel.getId(),
                        hotel.getName(),
                        lastOcc.get("roomOcc"),
                        lastOcc.get("bedOcc"),
                        lastOcc.get("month"),
                        lastOcc.get("year")
                };
            }
            model.addRow(rowData);
        }
        table2.setModel(model);
    }

    private void filterTransactionalData() {
        String selectedYear = (String) comboBox4.getSelectedItem();
        String selectedMonth = (String) comboBox5.getSelectedItem();
        String selectedHotel = (String) comboBox15.getSelectedItem();

        DefaultTableModel occModel = new DefaultTableModel(new String[]{
                "ID", "Hotel Name", "Room Occupancy", "Bed Occupancy", "Month", "Year"
        }, 0);

        for (Map<String, Object> occ : occupancies) {
            boolean match = true;
            if (selectedYear != null && !selectedYear.equals("---select---")) {
                if (!Objects.equals(occ.get("year"), Integer.parseInt(selectedYear))) match = false;
            }
            if (selectedMonth != null && !selectedMonth.equals("---select---")) {
                int monthIndex = comboBox5.getSelectedIndex();
                if (!Objects.equals(occ.get("month"), monthIndex)) match = false;
            }
            if (selectedHotel != null && !selectedHotel.equals("---select---")) {
                if (!Objects.equals(occ.get("hotelName"), selectedHotel)) match = false;
            }
            if (match) {
                Object[] row = {
                        occ.get("hotelId"),
                        occ.get("hotelName"),
                        occ.get("roomOcc"),
                        occ.get("bedOcc"),
                        occ.get("month"),
                        occ.get("year")
                };
                occModel.addRow(row);
            }
        }
        table3.setModel(occModel);
    }

    private void button5(ActionEvent e) {
        int row = table1.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a hotel!");
            return;
        }
        int id = Integer.parseInt(table1.getValueAt(row, 0).toString());
        String name = table1.getValueAt(row, 2).toString();
        String address = table1.getValueAt(row, 3).toString();
        int noRooms = Integer.parseInt(table1.getValueAt(row, 6).toString());
        int noBeds = Integer.parseInt(table1.getValueAt(row, 7).toString());

        Hotel hotel = null;
        for (Hotel h : hotels) {
            if (h.getId() == id) {
                hotel = h;
                break;
            }
        }
        if (hotel == null) return;

        hotel.setName(name);
        hotel.setAddress(address);
        hotel.setNoRooms(noRooms);
        hotel.setNoBeds(noBeds);

        // Hotelname auch in Transaktionsdaten aktualisieren
        for (Map<String, Object> occ : occupancies) {
            if (((int) occ.get("hotelId")) == hotel.getId()) {
                occ.put("hotelName", hotel.getName());
            }
        }

        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        EditHotelDialog dialog = new EditHotelDialog(topFrame, hotel);
        dialog.setVisible(true);

        loadHotelsInTable();
        loadTransactionalTable();
        filterTransactionalData();
        updateHotelComboBox(); // Wichtig: Nach Namensänderung Dropdown aktualisieren!
    }

    private static class AddTransactionalDataDialog extends JDialog {
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
            JLabel nameLabel = new JLabel(hotelName);
            nameLabel.setFont(nameLabel.getFont().deriveFont(Font.BOLD));
            add(nameLabel, gbc);

            gbc.gridx = 0; gbc.gridy++;
            add(new JLabel("Hotel ID:"), gbc);
            gbc.gridx = 1;
            JLabel idLabel = new JLabel(Integer.toString(hotelId));
            idLabel.setFont(idLabel.getFont().deriveFont(Font.BOLD));
            add(idLabel, gbc);

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
            String[] months = {"January", "February", "March", "April", "May", "June", "July", "August",
                    "September", "October", "November", "December"};
            for (String month : months) monthCombo.addItem(month);
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

            gbc.gridx = 0; gbc.gridy++;
            JButton saveBtn = new JButton("Save");
            JButton cancelBtn = new JButton("Cancel");
            add(saveBtn, gbc);
            gbc.gridx = 1;
            add(cancelBtn, gbc);

            saveBtn.addActionListener(e -> {
                String yearSel = (String) yearCombo.getSelectedItem();
                String monthSel = (String) monthCombo.getSelectedItem();
                String roomText = roomField.getText().trim();
                String bedText = bedField.getText().trim();

                if ("---select---".equals(yearSel) || "---select---".equals(monthSel)) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Please select both Year and Month before saving.",
                            "Selection missing",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
                if (roomText.isEmpty() || bedText.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Please enter both Room Occupancy and Bed Occupancy.",
                            "Missing input",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
                if (!isNumeric(roomText) || !isNumeric(bedText)) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Please enter valid numbers for Room Occupancy and Bed Occupancy.",
                            "Invalid input",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
                saved = true;
                setVisible(false);
            });

            cancelBtn.addActionListener(e -> setVisible(false));

            pack();
            setLocationRelativeTo(parent);
        }

        private boolean isNumeric(String str) {
            if (str == null || str.isEmpty()) return false;
            try {
                Integer.parseInt(str);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        public boolean isSaved() { return saved; }
        public int getSelectedYear() { return Integer.parseInt((String) yearCombo.getSelectedItem()); }
        public int getSelectedMonth() { return monthCombo.getSelectedIndex(); }
        public int getRoomOccupancy() { return Integer.parseInt(roomField.getText().trim()); }
        public int getBedOccupancy() { return Integer.parseInt(bedField.getText().trim()); }
    }

    //ab da nichts verändern ?!
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Amaim Mumtaz Rathor
        tabbedPane1 = new JTabbedPane();
        panel1 = new JPanel();
        panel7 = new JPanel();
        panel8 = new JPanel();
        button1 = new JButton();
        button2 = new JButton();
        scrollPane1 = new JScrollPane();
        table1 = new JTable();
        label1 = new JLabel();
        panel2 = new JPanel();
        panel3 = new JPanel();

        //======== this ========
        setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax.
        swing. border. EmptyBorder( 0, 0, 0, 0) , "JFor\u006dDesi\u0067ner \u0045valu\u0061tion", javax. swing. border
        . TitledBorder. CENTER, javax. swing. border. TitledBorder. BOTTOM, new java .awt .Font ("Dia\u006cog"
        ,java .awt .Font .BOLD ,12 ), java. awt. Color. red) , getBorder
        ( )) );  addPropertyChangeListener (new java. beans. PropertyChangeListener( ){ @Override public void propertyChange (java
        .beans .PropertyChangeEvent e) {if ("bord\u0065r" .equals (e .getPropertyName () )) throw new RuntimeException
        ( ); }} );

        //======== tabbedPane1 ========
        {

            //======== panel1 ========
            {

                //======== panel7 ========
                {

                    //======== panel8 ========
                    {
                        panel8.setBackground(new Color(0x3366ff));

                        //---- button1 ----
                        button1.setText("Log Out");
                        button1.setBackground(Color.lightGray);

                        //---- button2 ----
                        button2.setText("Help");
                        button2.setBackground(Color.lightGray);

                        GroupLayout panel8Layout = new GroupLayout(panel8);
                        panel8.setLayout(panel8Layout);
                        panel8Layout.setHorizontalGroup(
                            panel8Layout.createParallelGroup()
                                .addGroup(GroupLayout.Alignment.TRAILING, panel8Layout.createSequentialGroup()
                                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(button2)
                                    .addGap(17, 17, 17))
                                .addGroup(panel8Layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(button1)
                                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        );
                        panel8Layout.setVerticalGroup(
                            panel8Layout.createParallelGroup()
                                .addGroup(GroupLayout.Alignment.TRAILING, panel8Layout.createSequentialGroup()
                                    .addGap(25, 25, 25)
                                    .addComponent(button2)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 218, Short.MAX_VALUE)
                                    .addComponent(button1)
                                    .addGap(23, 23, 23))
                        );
                    }

                    //======== scrollPane1 ========
                    {

                        //---- table1 ----
                        table1.setModel(new DefaultTableModel(
                            new Object[][] {
                                {"1", "   Hotel Alpha", "    Vienna", "  20", "    35"},
                            },
                            new String[] {
                                "ID", "name", "adresse", "rooms", "beds"
                            }
                        ) {
                            boolean[] columnEditable = new boolean[] {
                                false, true, true, true, true
                            };
                            @Override
                            public boolean isCellEditable(int rowIndex, int columnIndex) {
                                return columnEditable[columnIndex];
                            }
                        });
                        {
                            TableColumnModel cm = table1.getColumnModel();
                            cm.getColumn(0).setPreferredWidth(15);
                        }
                        scrollPane1.setViewportView(table1);
                    }

                    //---- label1 ----
                    label1.setText("last report: 12.05.2025");
                    label1.setFont(new Font(".AppleSystemUIFont", Font.PLAIN, 6));
                    label1.setBackground(new Color(0x330033));

                    GroupLayout panel7Layout = new GroupLayout(panel7);
                    panel7.setLayout(panel7Layout);
                    panel7Layout.setHorizontalGroup(
                        panel7Layout.createParallelGroup()
                            .addGroup(panel7Layout.createSequentialGroup()
                                .addComponent(panel8, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 440, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(label1)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    );
                    panel7Layout.setVerticalGroup(
                        panel7Layout.createParallelGroup()
                            .addGroup(panel7Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panel7Layout.createParallelGroup()
                                    .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label1))
                                .addContainerGap(254, Short.MAX_VALUE))
                            .addComponent(panel8, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    );
                }

                GroupLayout panel1Layout = new GroupLayout(panel1);
                panel1.setLayout(panel1Layout);
                panel1Layout.setHorizontalGroup(
                    panel1Layout.createParallelGroup()
                        .addComponent(panel7, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                );
                panel1Layout.setVerticalGroup(
                    panel1Layout.createParallelGroup()
                        .addComponent(panel7, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                );
            }
            tabbedPane1.addTab("master data", panel1);

            //======== panel2 ========
            {

                GroupLayout panel2Layout = new GroupLayout(panel2);
                panel2.setLayout(panel2Layout);
                panel2Layout.setHorizontalGroup(
                    panel2Layout.createParallelGroup()
                        .addGap(0, 613, Short.MAX_VALUE)
                );
                panel2Layout.setVerticalGroup(
                    panel2Layout.createParallelGroup()
                        .addGap(0, 310, Short.MAX_VALUE)
                );
            }
            tabbedPane1.addTab("transactional", panel2);

            //======== panel3 ========
            {
                panel3.setLayout(new GridLayout(10, 10));
            }
            tabbedPane1.addTab("text", panel3);
        }

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addComponent(tabbedPane1, GroupLayout.PREFERRED_SIZE, 613, GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 122, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addComponent(tabbedPane1, GroupLayout.PREFERRED_SIZE, 343, GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 117, Short.MAX_VALUE))
        );
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Amaim Mumtaz Rathor
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JPanel panel7;
    private JPanel panel8;
    private JButton button1;
    private JButton button2;
    private JScrollPane scrollPane1;
    private JTable table1;
    private JLabel label1;
    private JPanel panel2;
    private JPanel panel3;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
