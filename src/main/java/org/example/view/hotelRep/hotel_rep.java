package org.example.view.hotelRep;

import org.example.dao.HotelDAO;
import org.example.entity.Hotel;
import org.example.entity.Occupancy;
import org.example.view.auth.login;
import org.example.view.hotel.EditHotelDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class hotel_rep extends JPanel {
    // Listen aus der Datenbank
    private List<Hotel> hotels;
    private List<Occupancy> occupancies;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public hotel_rep() {
        reloadData();
        initComponents();
        TableColumnModel cm = table1.getColumnModel();
        cm.getColumn(0).setPreferredWidth(15);

        showHotelsInTable();
        showTransactionalTable();
        updateHotelComboBox();

        // Jahr und Monat initialisieren
        comboBox4.removeAllItems();
        comboBox4.addItem("---select---");
        for (int y = 2025; y >= 2000; y--) comboBox4.addItem(String.valueOf(y));
        comboBox5.removeAllItems();
        comboBox5.addItem("---select---");
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August",
                "September", "October", "November", "December"};
        for (String month : months) comboBox5.addItem(month);

        // Filter-Listener
        comboBox4.addActionListener(e -> filterTransactionalData());
        comboBox5.addActionListener(e -> filterTransactionalData());
        comboBox15.addActionListener(e -> filterTransactionalData());

        // Logout (alle Tabs)
        ActionListener logout = e -> {
            JFrame top = (JFrame) SwingUtilities.getWindowAncestor(this);
            top.setContentPane(new login());
            top.pack();
            top.setLocationRelativeTo(null);
        };
        button1.addActionListener(logout);
        button3.addActionListener(logout);
        button7.addActionListener(logout);

        // Help-Button (alle Tabs)
        ActionListener help = e -> {
            JOptionPane.showMessageDialog(
                    hotel_rep.this,
                    "Welcome to the Lower Austria Tourist Portal!\n\n" +
                            "How to use:\n" +
                            "1. \"Hotel List\": View & edit all hotels.\n" +
                            "2. \"Transactional Data\": Add & review occupancy data for hotels.\n" +
                            "3. \"Transactional Data List\": Filter & view occupancy by year, month, hotel.\n\n" +
                            "Use provided buttons to edit or log out. For more help, contact support.",
                    "Help",
                    JOptionPane.INFORMATION_MESSAGE
            );
        };
        button2.addActionListener(help);
        button6.addActionListener(help);
        button8.addActionListener(help);

        // "Add Transactional Data"-Button (Tab 2)
        button4.addActionListener(e -> openAddTransactionDialog());

        // Hotel bearbeiten (Tab 1)
        button5.addActionListener(this::button5);
    }

    // --- Hilfsmethoden ---
    private void reloadData() {
        hotels = HotelDAO.getAllHotels();
        occupancies = HotelDAO.getAllOccupancies();
    }

    private void updateHotelComboBox() {
        comboBox15.removeAllItems();
        comboBox15.addItem("---select---");
        for (Hotel hotel : hotels) {
            comboBox15.addItem(hotel.getName());
        }
    }

    private void showHotelsInTable() {
        String[] columns = {"ID", "Category", "Hotel Name", "Address", "City", "City Code", "No. of Rooms", "No. of Beds", "Last Transaction"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        for (Hotel hotel : hotels) {
            String lastDate = getLastTransactionDate(hotel.getId());
            model.addRow(new Object[] {
                    hotel.getId(),
                    hotel.getCategory(),
                    hotel.getName(),
                    hotel.getAddress(),
                    hotel.getCity(),
                    hotel.getCityCode(),
                    hotel.getNoRooms(),
                    hotel.getNoBeds(),
                    lastDate
            });
        }
        table1.setModel(model);
    }

    private void showTransactionalTable() {
        String[] columns = {"ID", "Hotel Name", "Room Occupancy", "Bed Occupancy", "Month", "Year"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        for (Hotel hotel : hotels) {
            Occupancy occ = getLastOccupancy(hotel.getId());
            if (occ == null) {
                model.addRow(new Object[] {hotel.getId(), hotel.getName(), "", "", "", ""});
            } else {
                model.addRow(new Object[] {
                        hotel.getId(),
                        hotel.getName(),
                        occ.getRoomOccupancy(),
                        occ.getBedOccupancy(),
                        occ.getMonth(),
                        occ.getYear()
                });
            }
        }
        table2.setModel(model);
    }

    private Occupancy getLastOccupancy(int hotelId) {
        Occupancy latest = null;
        for (Occupancy occ : occupancies) {
            if (occ.getHotel().getId() == hotelId) {
                if (latest == null) latest = occ;
                else if (isLater(occ, latest)) latest = occ;
            }
        }
        return latest;
    }

    private String getLastTransactionDate(int hotelId) {
        Occupancy latest = getLastOccupancy(hotelId);
        if (latest == null) return "";
        return latest.getReportedDate() != null ?
                latest.getReportedDate().format(dateFormatter) : "";
    }

    private boolean isLater(Occupancy occ1, Occupancy occ2) {
        int y1 = occ1.getYear(), m1 = occ1.getMonth();
        int y2 = occ2.getYear(), m2 = occ2.getMonth();
        return y1 > y2 || (y1 == y2 && m1 > m2);
    }

    // *** FILTER-Funktion: Korrekt und funktioniert ***
    private void filterTransactionalData() {
        String selectedYear = (String) comboBox4.getSelectedItem();
        String selectedMonth = (String) comboBox5.getSelectedItem();
        String selectedHotel = (String) comboBox15.getSelectedItem();

        DefaultTableModel occModel = new DefaultTableModel(
                new String[]{"ID", "Hotel Name", "Room Occupancy", "Bed Occupancy", "Month", "Year"}, 0);

        // Lade aktuelle Daten aus der Datenbank
        List<Occupancy> occupancies = HotelDAO.getAllOccupancies();

        for (Occupancy occ : occupancies) {
            boolean match = true;

            // Hotel-Filter
            if (selectedHotel != null && !selectedHotel.equals("---select---")) {
                if (!occ.getHotel().getName().equalsIgnoreCase(selectedHotel)) {
                    match = false;
                }
            }

            // Jahr-Filter
            if (selectedYear != null && !selectedYear.equals("---select---")) {
                try {
                    int year = Integer.parseInt(selectedYear);
                    if (occ.getYear() != year) {
                        match = false;
                    }
                } catch (NumberFormatException e) {
                    match = false;
                }
            }

            // Monat-Filter
            if (selectedMonth != null && !selectedMonth.equals("---select---")) {
                int monthIndex = comboBox5.getSelectedIndex(); // 0 = ---select---, 1 = January → 1, etc.
                if (occ.getMonth() != monthIndex) { // Monat in DB muss 1–12 sein
                    match = false;
                }
            }

            // Wenn alle aktiven Filter zutreffen
            if (match) {
                occModel.addRow(new Object[]{
                        occ.getHotel().getId(),
                        occ.getHotel().getName(),
                        occ.getRoomOccupancy(),
                        occ.getBedOccupancy(),
                        occ.getMonth(),
                        occ.getYear()
                });
            }
        }

        table3.setModel(occModel);
    }



    // --- Dialog für neue Belegung (Tab 2) ---
    private void openAddTransactionDialog() {
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

            Hotel selectedHotel = null;
            for (Hotel h : hotels) if (h.getId() == hotelId) selectedHotel = h;
            if (selectedHotel == null) return;

            Occupancy occ = new Occupancy();
            occ.setHotel(selectedHotel);
            occ.setYear(year);
            occ.setMonth(month);
            occ.setRoomOccupancy(roomOcc);
            occ.setBedOccupancy(bedOcc);
            occ.setReportedDate(LocalDate.now());

            HotelDAO.saveOccupancy(occ);
            reloadData();

            showHotelsInTable();
            showTransactionalTable();
            filterTransactionalData();
        }
    }

    // Hotel bearbeiten (Tab 1)
    private void button5(ActionEvent e) {
        int row = table1.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a hotel to edit!");
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

        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        EditHotelDialog dialog = new EditHotelDialog(topFrame, hotel);
        dialog.setVisible(true);

        if (dialog.isSaved()) {
            HotelDAO.updateHotel(hotel);
            reloadData();
        }

        showHotelsInTable();
        showTransactionalTable();
        filterTransactionalData();
        updateHotelComboBox();
    }

    // Dialog für neue Belegungseinträge
    private static class AddTransactionalDataDialog extends JDialog {
        private boolean saved = false;
        private JComboBox<String> yearCombo, monthCombo;
        private JTextField roomField, bedField;

        public AddTransactionalDataDialog(JFrame parent, int hotelId, String hotelName) {
            super(parent, "Add Transactional Data", true);

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(new JLabel("Hotel: " + hotelName + " (ID: " + hotelId + ")"));

            JPanel yearPanel = new JPanel();
            yearPanel.add(new JLabel("Year:"));
            yearCombo = new JComboBox<>();
            yearCombo.addItem("---select---");
            for (int y = 2025; y >= 2000; y--) yearCombo.addItem(String.valueOf(y));
            yearPanel.add(yearCombo);
            mainPanel.add(yearPanel);

            JPanel monthPanel = new JPanel();
            monthPanel.add(new JLabel("Month:"));
            monthCombo = new JComboBox<>();
            monthCombo.addItem("---select---");
            String[] months = {"January", "February", "March", "April", "May", "June", "July", "August",
                    "September", "October", "November", "December"};
            for (String month : months) monthCombo.addItem(month);
            monthPanel.add(monthCombo);
            mainPanel.add(monthPanel);

            JPanel roomPanel = new JPanel();
            roomPanel.add(new JLabel("Room Occupancy:"));
            roomField = new JTextField(10);
            roomPanel.add(roomField);
            mainPanel.add(roomPanel);

            JPanel bedPanel = new JPanel();
            bedPanel.add(new JLabel("Bed Occupancy:"));
            bedField = new JTextField(10);
            bedPanel.add(bedField);
            mainPanel.add(bedPanel);

            JPanel btnPanel = new JPanel();
            JButton saveBtn = new JButton("Save");
            JButton cancelBtn = new JButton("Cancel");
            btnPanel.add(saveBtn);
            btnPanel.add(cancelBtn);
            mainPanel.add(btnPanel);

            saveBtn.addActionListener(e -> {
                String yearSel = (String) yearCombo.getSelectedItem();
                String monthSel = (String) monthCombo.getSelectedItem();
                String roomText = roomField.getText().trim();
                String bedText = bedField.getText().trim();

                if ("---select---".equals(yearSel) || "---select---".equals(monthSel)) {
                    JOptionPane.showMessageDialog(this, "Please select both Year and Month.");
                    return;
                }
                if (roomText.isEmpty() || bedText.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter both Room and Bed Occupancy.");
                    return;
                }
                if (!isNumeric(roomText) || !isNumeric(bedText)) {
                    JOptionPane.showMessageDialog(this, "Please enter valid numbers for Room and Bed Occupancy.");
                    return;
                }
                saved = true;
                setVisible(false);
            });
            cancelBtn.addActionListener(e -> setVisible(false));

            setContentPane(mainPanel);
            pack();
            setLocationRelativeTo(parent);
        }

        private boolean isNumeric(String str) {
            try { Integer.parseInt(str); return true; }
            catch (Exception e) { return false; }
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
        // Generated using JFormDesigner Educational license - amaim rathor
        tabbedPane1 = new JTabbedPane();
        panel1 = new JPanel();
        panel7 = new JPanel();
        panel8 = new JPanel();
        button1 = new JButton();
        button2 = new JButton();
        scrollPane1 = new JScrollPane();
        table1 = new JTable();
        button5 = new JButton();
        label6 = new JLabel();
        panel2 = new JPanel();
        scrollPane3 = new JScrollPane();
        table2 = new JTable();
        button4 = new JButton();
        panel9 = new JPanel();
        button3 = new JButton();
        button6 = new JButton();
        label5 = new JLabel();
        panel3 = new JPanel();
        panel10 = new JPanel();
        button7 = new JButton();
        button8 = new JButton();
        scrollPane4 = new JScrollPane();
        table3 = new JTable();
        comboBox15 = new JComboBox<>();
        label1 = new JLabel();
        label2 = new JLabel();
        label3 = new JLabel();
        label4 = new JLabel();
        comboBox4 = new JComboBox<>();
        comboBox5 = new JComboBox<>();

        //======== this ========
        setPreferredSize(new Dimension(900, 600));

        //======== tabbedPane1 ========
        {
            tabbedPane1.setPreferredSize(new Dimension(800, 600));

            //======== panel1 ========
            {
                panel1.setPreferredSize(new Dimension(800, 300));

                //======== panel7 ========
                {

                    //======== panel8 ========
                    {
                        panel8.setBackground(new Color(0x3366ff));
                        panel8.setMinimumSize(new Dimension(83, 492));

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
                                        .addGroup(panel8Layout.createSequentialGroup()
                                                .addGroup(panel8Layout.createParallelGroup()
                                                        .addGroup(panel8Layout.createSequentialGroup()
                                                                .addGap(24, 24, 24)
                                                                .addComponent(button2))
                                                        .addGroup(panel8Layout.createSequentialGroup()
                                                                .addGap(15, 15, 15)
                                                                .addComponent(button1)))
                                                .addContainerGap(15, Short.MAX_VALUE))
                        );
                        panel8Layout.setVerticalGroup(
                                panel8Layout.createParallelGroup()
                                        .addGroup(GroupLayout.Alignment.TRAILING, panel8Layout.createSequentialGroup()
                                                .addGap(34, 34, 34)
                                                .addComponent(button2)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(button1)
                                                .addGap(33, 33, 33))
                        );
                    }

                    //======== scrollPane1 ========
                    {

                        //---- table1 ----
                        table1.setModel(new DefaultTableModel(
                                new Object[][] {
                                        {"", null, "", " ", null, null, "  ", "    ", null},
                                },
                                new String[] {
                                        "ID", "Category", "Hotel Name", "Adress", "City", "PLZ", "Rooms", "Beds", "Last Transaction"
                                }
                        ) {
                            boolean[] columnEditable = new boolean[] {
                                    false, true, true, true, true, true, true, true, true
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

                    //---- button5 ----
                    button5.setText("Edit Hotel");
                    button5.addActionListener(e -> button5(e));

                    //---- label6 ----
                    label6.setIcon(new ImageIcon(getClass().getResource("/logo2.png")));

                    GroupLayout panel7Layout = new GroupLayout(panel7);
                    panel7.setLayout(panel7Layout);
                    panel7Layout.setHorizontalGroup(
                            panel7Layout.createParallelGroup()
                                    .addGroup(panel7Layout.createSequentialGroup()
                                            .addComponent(panel8, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                            .addGroup(panel7Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                    .addGroup(panel7Layout.createSequentialGroup()
                                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                            .addGroup(panel7Layout.createParallelGroup()
                                                                    .addGroup(panel7Layout.createSequentialGroup()
                                                                            .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 727, Short.MAX_VALUE)
                                                                            .addContainerGap())
                                                                    .addGroup(GroupLayout.Alignment.TRAILING, panel7Layout.createSequentialGroup()
                                                                            .addGap(0, 525, Short.MAX_VALUE)
                                                                            .addComponent(button5)
                                                                            .addGap(122, 122, 122))))
                                                    .addGroup(panel7Layout.createSequentialGroup()
                                                            .addGap(54, 564, Short.MAX_VALUE)
                                                            .addComponent(label6)
                                                            .addGap(15, 15, 15))))
                    );
                    panel7Layout.setVerticalGroup(
                            panel7Layout.createParallelGroup()
                                    .addGroup(panel7Layout.createSequentialGroup()
                                            .addContainerGap()
                                            .addComponent(label6)
                                            .addGap(27, 27, 27)
                                            .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 348, GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(button5)
                                            .addContainerGap(37, Short.MAX_VALUE))
                                    .addComponent(panel8, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    );
                }

                GroupLayout panel1Layout = new GroupLayout(panel1);
                panel1.setLayout(panel1Layout);
                panel1Layout.setHorizontalGroup(
                        panel1Layout.createParallelGroup()
                                .addGroup(panel1Layout.createSequentialGroup()
                                        .addComponent(panel7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 21, Short.MAX_VALUE))
                );
                panel1Layout.setVerticalGroup(
                        panel1Layout.createParallelGroup()
                                .addGroup(GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(panel7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                );
            }
            tabbedPane1.addTab("Hotel List", panel1);

            //======== panel2 ========
            {

                //======== scrollPane3 ========
                {

                    //---- table2 ----
                    table2.setModel(new DefaultTableModel(
                            new Object[][] {
                                    {"", "", null, null, null, null},
                            },
                            new String[] {
                                    "ID", "Hotel Name", "Room Occupany", "Bed Occupancy", "Month", "Year"
                            }
                    ));
                    scrollPane3.setViewportView(table2);
                }

                //---- button4 ----
                button4.setText("Add Transactional Data");

                //======== panel9 ========
                {
                    panel9.setBackground(new Color(0x3366ff));
                    panel9.setPreferredSize(new Dimension(89, 313));

                    //---- button3 ----
                    button3.setText("Log Out");
                    button3.setBackground(Color.lightGray);

                    //---- button6 ----
                    button6.setText("Help");
                    button6.setBackground(Color.lightGray);

                    GroupLayout panel9Layout = new GroupLayout(panel9);
                    panel9.setLayout(panel9Layout);
                    panel9Layout.setHorizontalGroup(
                            panel9Layout.createParallelGroup()
                                    .addGroup(panel9Layout.createSequentialGroup()
                                            .addGap(15, 15, 15)
                                            .addGroup(panel9Layout.createParallelGroup()
                                                    .addComponent(button3)
                                                    .addGroup(panel9Layout.createSequentialGroup()
                                                            .addGap(9, 9, 9)
                                                            .addComponent(button6)))
                                            .addContainerGap(7, Short.MAX_VALUE))
                    );
                    panel9Layout.setVerticalGroup(
                            panel9Layout.createParallelGroup()
                                    .addGroup(GroupLayout.Alignment.TRAILING, panel9Layout.createSequentialGroup()
                                            .addGap(32, 32, 32)
                                            .addComponent(button6)
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 369, Short.MAX_VALUE)
                                            .addComponent(button3)
                                            .addGap(23, 23, 23))
                    );
                }

                //---- label5 ----
                label5.setIcon(new ImageIcon(getClass().getResource("/logo2.png")));

                GroupLayout panel2Layout = new GroupLayout(panel2);
                panel2.setLayout(panel2Layout);
                panel2Layout.setHorizontalGroup(
                        panel2Layout.createParallelGroup()
                                .addGroup(panel2Layout.createSequentialGroup()
                                        .addComponent(panel9, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(panel2Layout.createParallelGroup()
                                                .addGroup(panel2Layout.createSequentialGroup()
                                                        .addGap(10, 10, 10)
                                                        .addComponent(scrollPane3, GroupLayout.PREFERRED_SIZE, 682, GroupLayout.PREFERRED_SIZE)
                                                        .addContainerGap(76, Short.MAX_VALUE))
                                                .addGroup(GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 471, Short.MAX_VALUE)
                                                        .addGroup(panel2Layout.createParallelGroup()
                                                                .addGroup(GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                                                                        .addComponent(label5)
                                                                        .addGap(84, 84, 84))
                                                                .addGroup(GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                                                                        .addComponent(button4)
                                                                        .addGap(140, 140, 140))))))
                );
                panel2Layout.setVerticalGroup(
                        panel2Layout.createParallelGroup()
                                .addGroup(GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(label5)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                                        .addComponent(scrollPane3, GroupLayout.PREFERRED_SIZE, 349, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(button4)
                                        .addGap(21, 21, 21))
                                .addComponent(panel9, GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE)
                );
            }
            tabbedPane1.addTab("Transactional Data", panel2);

            //======== panel3 ========
            {
                panel3.setPreferredSize(new Dimension(673, 300));

                //======== panel10 ========
                {
                    panel10.setBackground(new Color(0x3366ff));
                    panel10.setPreferredSize(new Dimension(89, 313));

                    //---- button7 ----
                    button7.setText("Log Out");
                    button7.setBackground(Color.lightGray);

                    //---- button8 ----
                    button8.setText("Help");
                    button8.setBackground(Color.lightGray);

                    GroupLayout panel10Layout = new GroupLayout(panel10);
                    panel10.setLayout(panel10Layout);
                    panel10Layout.setHorizontalGroup(
                            panel10Layout.createParallelGroup()
                                    .addGroup(panel10Layout.createSequentialGroup()
                                            .addGroup(panel10Layout.createParallelGroup()
                                                    .addGroup(panel10Layout.createSequentialGroup()
                                                            .addGap(24, 24, 24)
                                                            .addComponent(button8))
                                                    .addGroup(panel10Layout.createSequentialGroup()
                                                            .addGap(15, 15, 15)
                                                            .addComponent(button7)))
                                            .addContainerGap(8, Short.MAX_VALUE))
                    );
                    panel10Layout.setVerticalGroup(
                            panel10Layout.createParallelGroup()
                                    .addGroup(GroupLayout.Alignment.TRAILING, panel10Layout.createSequentialGroup()
                                            .addGap(27, 27, 27)
                                            .addComponent(button8)
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 376, Short.MAX_VALUE)
                                            .addComponent(button7)
                                            .addGap(21, 21, 21))
                    );
                }

                //======== scrollPane4 ========
                {

                    //---- table3 ----
                    table3.setModel(new DefaultTableModel(
                            new Object[][] {
                                    {null, null, "", null, null, null},
                            },
                            new String[] {
                                    "ID", "Hotel Name", "Room Occupancy", "Bed Occupancy", "Month", "Year"
                            }
                    ));
                    scrollPane4.setViewportView(table3);
                }

                //---- comboBox15 ----
                comboBox15.setModel(new DefaultComboBoxModel<>(new String[] {
                        "---select---",
                        "Hotel Alpha",
                        "Hotel Beta",
                        "Hotel Gamma ",
                        "Hotel Delta",
                        "Hotel Epsilon"
                }));

                //---- label1 ----
                label1.setText("Year:");

                //---- label2 ----
                label2.setText("Month:");

                //---- label3 ----
                label3.setText("Hotel:");

                //---- label4 ----
                label4.setIcon(new ImageIcon(getClass().getResource("/logo2.png")));

                //---- comboBox4 ----
                comboBox4.setModel(new DefaultComboBoxModel<>(new String[] {
                        "2025",
                        "2024",
                        "2023",
                        "2022",
                        "2021",
                        "2020",
                        "2019",
                        "2018",
                        "2017",
                        "2016",
                        "2015",
                        "2014",
                        "2013",
                        "2012",
                        "2011",
                        "2010",
                        "2009",
                        "2008",
                        "2007",
                        "2006",
                        "2005",
                        "2004",
                        "2003",
                        "2002",
                        "2001",
                        "2000",
                        "1999",
                        "1998",
                        "1997",
                        "1996",
                        "1995",
                        "1994",
                        "1993",
                        "1992",
                        "1991",
                        "1990",
                        "1989",
                        "1988",
                        "1987",
                        "1986",
                        "1985",
                        "1984",
                        "1983",
                        "1982",
                        "1981",
                        "1980",
                        "1979",
                        "1978",
                        "1977",
                        "1976",
                        "1975",
                        "1974",
                        "1973",
                        "1972",
                        "1971",
                        "1970",
                        "1969",
                        "1968",
                        "1967",
                        "1966",
                        "1965",
                        "1964",
                        "1963",
                        "1962",
                        "1961",
                        "1960",
                        "1959",
                        "1958",
                        "1957",
                        "1956",
                        "1955",
                        "1954",
                        "1953",
                        "1952",
                        "1951",
                        "1950",
                        "1949",
                        "1948",
                        "1947",
                        "1946",
                        "1945",
                        "1944",
                        "1943",
                        "1942",
                        "1941",
                        "1940",
                        "1939",
                        "1938",
                        "1937",
                        "1936",
                        "1935",
                        "1934",
                        "1933",
                        "1932",
                        "1931",
                        "1930",
                        "1929",
                        "1928",
                        "1927",
                        "1926",
                        "1925",
                        "1924",
                        "1923",
                        "1922",
                        "1921",
                        "1920",
                        "1919",
                        "1918",
                        "1917",
                        "1916",
                        "1915",
                        "1914",
                        "1913",
                        "1912",
                        "1911",
                        "1910",
                        "1909",
                        "1908",
                        "1907",
                        "1906",
                        "1905",
                        "1904",
                        "1903",
                        "1902",
                        "1901",
                        "1900"
                }));

                //---- comboBox5 ----
                comboBox5.setModel(new DefaultComboBoxModel<>(new String[] {
                        "January",
                        "February",
                        "March",
                        "April",
                        "May",
                        "June",
                        "July",
                        "August",
                        "September",
                        "October",
                        "November",
                        "December"
                }));

                GroupLayout panel3Layout = new GroupLayout(panel3);
                panel3.setLayout(panel3Layout);
                panel3Layout.setHorizontalGroup(
                        panel3Layout.createParallelGroup()
                                .addGroup(panel3Layout.createSequentialGroup()
                                        .addComponent(panel10, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
                                        .addGap(35, 35, 35)
                                        .addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                .addGroup(panel3Layout.createSequentialGroup()
                                                        .addGroup(panel3Layout.createParallelGroup()
                                                                .addGroup(panel3Layout.createSequentialGroup()
                                                                        .addComponent(label2)
                                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                                        .addComponent(comboBox5, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE))
                                                                .addComponent(scrollPane4, GroupLayout.PREFERRED_SIZE, 674, GroupLayout.PREFERRED_SIZE))
                                                        .addContainerGap(58, Short.MAX_VALUE))
                                                .addGroup(panel3Layout.createSequentialGroup()
                                                        .addComponent(label1)
                                                        .addGap(33, 33, 33)
                                                        .addComponent(comboBox4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(label3)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(comboBox15, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 196, Short.MAX_VALUE)
                                                        .addComponent(label4)
                                                        .addGap(78, 78, 78))))
                );
                panel3Layout.setVerticalGroup(
                        panel3Layout.createParallelGroup()
                                .addGroup(panel3Layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                .addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(comboBox15, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(label3)
                                                        .addComponent(label1)
                                                        .addComponent(comboBox4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                .addComponent(label4))
                                        .addGap(33, 33, 33)
                                        .addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(comboBox5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(label2))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                                        .addComponent(scrollPane4, GroupLayout.PREFERRED_SIZE, 294, GroupLayout.PREFERRED_SIZE)
                                        .addGap(34, 34, 34))
                                .addComponent(panel10, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE)
                );
            }
            tabbedPane1.addTab("Transactional Data List", panel3);
        }

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(74, Short.MAX_VALUE)
                                .addComponent(tabbedPane1, GroupLayout.PREFERRED_SIZE, 875, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(tabbedPane1, GroupLayout.PREFERRED_SIZE, 509, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Educational license - amaim rathor
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JPanel panel7;
    private JPanel panel8;
    private JButton button1;
    private JButton button2;
    private JScrollPane scrollPane1;
    private JTable table1;
    private JButton button5;
    private JLabel label6;
    private JPanel panel2;
    private JScrollPane scrollPane3;
    private JTable table2;
    private JButton button4;
    private JPanel panel9;
    private JButton button3;
    private JButton button6;
    private JLabel label5;
    private JPanel panel3;
    private JPanel panel10;
    private JButton button7;
    private JButton button8;
    private JScrollPane scrollPane4;
    private JTable table3;
    private JComboBox<String> comboBox15;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JComboBox<String> comboBox4;
    private JComboBox<String> comboBox5;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
