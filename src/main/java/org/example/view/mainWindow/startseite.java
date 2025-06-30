package org.example.view.mainWindow;

import org.example.entity.Hotel;
import org.example.entity.Occupancy;
import org.example.data.txt.HotelFileReader;
import org.example.data.txt.HotelFileWriter;
import org.example.data.txt.OccupancyFileReader;
import java.time.LocalDate;
import java.util.stream.Stream;


import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class startseite extends JPanel {
    private List<Map<String, Object>> occupancyDataList = new ArrayList<>();
    private Map<Integer, String> transactionalDataAlt = new HashMap<>();



    // ===== JFormDesigner GUI-Komponenten =====
    // Die folgenden Felder müssen zu deinen JFormDesigner-Komponenten passen!
    // Beispiel:
    // private JTable table1, table3, table5, table6;
    // private JButton button25, deleteButton, button6, button3, button15, button5, button4, button1, button2;
    // private JComboBox<String> comboBox18;

    // Bitte deklariere alle Komponenten entsprechend deiner .form Datei!

    public startseite() {
        initComponents();

        //PANEL 5:

        // Panel "combined Overview" – Hotel-Dropdown befüllen
        comboBox16.removeAllItems();
        comboBox16.addItem("---select---");
        updateCombinedHotelDetails();
        comboBox16.addActionListener(e -> {
            ladeCombinedOverviewData();   // falls schon da
            updateCombinedHotelDetails(); // neu
        });
        // nach initComponents();

        comboBox12.insertItemAt("---select---", 0);    // Jahr
        comboBox13.insertItemAt("---select---", 0);    // Monat
        comboBox1 .insertItemAt("---select---", 0);    // Kategorie

        comboBox12.setSelectedIndex(0);
        comboBox13.setSelectedIndex(0);
        comboBox1 .setSelectedIndex(0);

        // Wenn sich eines der Filter ändert, neu laden:
        comboBox16.addActionListener(e -> ladeCombinedOverview());
        comboBox1 .addActionListener(e -> ladeCombinedOverview());
        comboBox12.addActionListener(e -> ladeCombinedOverview());
        comboBox13.addActionListener(e -> ladeCombinedOverview());




// Lies alle Hotels aus der Text-Datei:
        String hotelFile = "src/main/java/org/example/data/txt/hotels.txt";
        List<Hotel> hotels = HotelFileReader.readHotelsFromFile(hotelFile);

// Sortiere nach Name und füge in comboBox16 ein
        new TreeSet<>(hotels.stream()
                .map(Hotel::getName)
                .collect(Collectors.toList()))
                .forEach(comboBox16::addItem);
        //__________________



        // Button-Listener für Hotel-Panel
        button25.addActionListener(e -> saveHotelData());
        deleteButton.addActionListener(e -> deleteSelectedHotel());
        button6.addActionListener(e -> addHotel());
        button3.addActionListener(e -> editHotel());

        //für logout Buttons
        button1.addActionListener(e -> logout());
        button19.addActionListener(e -> logout());
        button22.addActionListener(e -> logout());
        button26.addActionListener(e -> logout());
        button28.addActionListener(e -> logout());

        //delete
        button7.addActionListener(e -> deleteSelectedTransactionalData());

        // Listener für weitere Panels (Beispiele)
        button15.addActionListener(e -> ladeHotelsSummary());
        button5.addActionListener(e -> openAddTransactionalDialog());
        button4.addActionListener(e -> saveTransactionalData());
        comboBox18.addActionListener(e -> ladeTransaktionsDatenMitAttributen());

        button2.addActionListener(e -> showHelp());
        button27.addActionListener(e -> showHelp());
        button29.addActionListener(e -> showHelp());
        button20.addActionListener(e -> showHelp());
        button23.addActionListener(e -> showHelp());

        ladeDropdownHotels();
        ladeHotelsInTabelle();
        ladeHotelsSummary();
        initialisiereLeereTransaktionsTabelle();

        //Panel 5:
        comboBox16.addActionListener(e -> ladeCombinedOverviewData());
        comboBox16.addActionListener(e -> ladeCombinedOverview());
    }

    // ===== Methoden für das Hotel-List-Panel =====

    private void ladeHotelsInTabelle() {
        String filePath = "src/main/java/org/example/data/txt/hotels.txt";
        List<Hotel> hotels = HotelFileReader.readHotelsFromFile(filePath);

        DefaultTableModel model = new DefaultTableModel(new String[]{
                "ID", "Category", "Name", "Adresse", "City", "PLZ", "Rooms", "Beds", "Attribute", "Last Transactional Data"
        }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0;
            }
        };

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
                    hotel.getAttribute() != null ? hotel.getAttribute() : "",
                    "" // <-- immer leer laden!
            };
            model.addRow(rowData);
        }
        table1.setModel(model);

        // HIER transactionalDataAlt befüllen:
        transactionalDataAlt.clear();
        for (int i = 0; i < model.getRowCount(); i++) {
            transactionalDataAlt.put(i, model.getValueAt(i, 8).toString());
        }
    }

    private void saveHotelData() {
        try {
            DefaultTableModel model = (DefaultTableModel) table1.getModel();
            List<String> lines = new ArrayList<>();
            // Neue Header-Zeile mit "attribute" und "lastTransactionalData"
            lines.add("id,category,name,owner,contact,address,city,cityCode,phone,noRooms,noBeds,attribute,lastTransactionalData");

            for (int i = 0; i < model.getRowCount(); i++) {
                StringJoiner joiner = new StringJoiner(",");

                String attribute = model.getValueAt(i, 8).toString();
                String lastTransactionalData = model.getValueAt(i, 9) != null ? model.getValueAt(i, 9).toString() : "";

                // ALT: alter Wert vom Laden
                String attributeAlt = transactionalDataAlt.get(i);

                // Prüfen: Hat sich das Attribut geändert?
                if (!attribute.equals(attributeAlt)) {
                    lastTransactionalData = java.time.LocalDate.now().toString();
                    model.setValueAt(lastTransactionalData, i, 9); // GUI aktualisieren
                    transactionalDataAlt.put(i, attribute);        // Merken für spätere Saves
                } else {
                    // Wenn kein Change und das Feld ist ein Default-Datum, dann leer lassen
                    if (lastTransactionalData.equals("2025-06-22")) {
                        lastTransactionalData = "";
                        model.setValueAt("", i, 9);
                    }
                }

                joiner.add(model.getValueAt(i, 0).toString()); // id
                joiner.add("\"" + model.getValueAt(i, 1).toString() + "\""); // category
                joiner.add("\"" + model.getValueAt(i, 2).toString() + "\""); // name
                joiner.add("\"-\""); // owner
                joiner.add("\"-\""); // contact
                joiner.add("\"" + model.getValueAt(i, 3).toString() + "\""); // address
                joiner.add("\"" + model.getValueAt(i, 4).toString() + "\""); // city
                joiner.add("\"" + model.getValueAt(i, 5).toString() + "\""); // cityCode
                joiner.add("\"-\""); // phone
                joiner.add(model.getValueAt(i, 6).toString()); // noRooms
                joiner.add(model.getValueAt(i, 7).toString()); // noBeds
                joiner.add("\"" + attribute + "\""); // attribute
                joiner.add("\"" + lastTransactionalData + "\""); // lastTransactionalData

                lines.add(joiner.toString());
            }

            String filePath = "src/main/java/org/example/data/txt/hotels.txt";
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
                for (String line : lines) {
                    bw.write(line);
                    bw.newLine();
                }
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Changes successfully saved!",
                    "Save",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(
                    this,
                    "Error while saving!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
    private void deleteSelectedHotel() {
        int selectedRow = table1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a hotel in the table first!",
                    "No selection",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String hotelId = table1.getValueAt(selectedRow, 0).toString();
        Object[] options = {"Delete", "Stop"};
        int choice = JOptionPane.showOptionDialog(
                this,
                "Transactional data of this Hotel will also be deleted!",
                "Are you sure?",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                options,
                options[1]
        );

        if (choice == JOptionPane.YES_OPTION) {
            DefaultTableModel model = (DefaultTableModel) table1.getModel();
            model.removeRow(selectedRow);
            JOptionPane.showMessageDialog(
                    this,
                    "Hotel with ID " + hotelId + " has been deleted.",
                    "Deleted",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }



    private void addHotel() {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        HotelDialog dialog = new HotelDialog(parentFrame, null, false, table1);
        dialog.setVisible(true);
        if (dialog.isSaved()) {
            Object[] newHotel = dialog.getHotelData();
            ((DefaultTableModel) table1.getModel()).addRow(newHotel);
        }
    }

    private void editHotel() {
        int selectedRow = table1.getSelectedRow();
        if (selectedRow == -1) return;
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        Object[] rowData = new Object[model.getColumnCount()];
        for (int i = 0; i < model.getColumnCount(); i++) {
            rowData[i] = model.getValueAt(selectedRow, i);
        }
        JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
        HotelDialog dialog = new HotelDialog(parent, rowData, true, table1);
        dialog.setVisible(true);
        if (dialog.isSaved()) {
            Object[] editedHotel = dialog.getHotelData();
            for (int i = 0; i < model.getColumnCount(); i++) {
                model.setValueAt(editedHotel[i], selectedRow, i);
            }
        }
    }

    // ===== Methoden für das Hotel-Summary-Panel =====

    private void ladeHotelsSummary() {
        String filePath = "src/main/java/org/example/data/txt/hotels.txt";
        List<Hotel> hotels = HotelFileReader.readHotelsFromFile(filePath);

        Map<String, List<Hotel>> kategorienMap = new HashMap<>();
        for (Hotel hotel : hotels) {
            kategorienMap.computeIfAbsent(hotel.getCategory(), k -> new ArrayList<>()).add(hotel);
        }

        List<String> kategorienReihenfolge = List.of("*", "**", "***", "****", "*****");
        DefaultTableModel model = (DefaultTableModel) table5.getModel();
        model.setRowCount(0);

        for (String kategorie : kategorienReihenfolge) {
            List<Hotel> kategorieHotels = kategorienMap.getOrDefault(kategorie, new ArrayList<>());
            int anzahlHotels = kategorieHotels.size();
            int sumRooms = 0, sumBeds = 0;

            for (Hotel hotel : kategorieHotels) {
                sumRooms += hotel.getNoRooms();
                sumBeds += hotel.getNoBeds();
            }

            int avgRooms = anzahlHotels == 0 ? 0 : sumRooms / anzahlHotels;
            int avgBeds = anzahlHotels == 0 ? 0 : sumBeds / anzahlHotels;

            Object[] rowData = { kategorie, anzahlHotels, avgRooms, avgBeds };
            model.addRow(rowData);
        }
        table5.setModel(model);
    }

    // ===== Methoden für das Transactional Data Panel =====

    private void initialisiereLeereTransaktionsTabelle() {
        String filePath = "src/main/java/org/example/data/txt/hotels.txt";
        List<Hotel> hotels = HotelFileReader.readHotelsFromFile(filePath);

        DefaultTableModel model = new DefaultTableModel(new Object[]{
                "ID", "Hotel Name", "Room Occupancy", "Bed Occupancy", "Month", "Year"
        }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0;
            }
        };

        for (Hotel hotel : hotels) {
            model.addRow(new Object[]{
                    hotel.getId(),
                    hotel.getName(),
                    "", "", "", ""
            });
        }
        table6.setModel(model);
    }

    private void openAddTransactionalDialog() {
        int row = table6.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a hotel in the table first!");
            return;
        }

        int hotelId = Integer.parseInt(table6.getValueAt(row, 0).toString());
        String hotelName = table6.getValueAt(row, 1).toString();

        AddTransactionalDataDialog dialog = new AddTransactionalDataDialog(
                (JFrame) SwingUtilities.getWindowAncestor(this), hotelId, hotelName
        );
        dialog.setVisible(true);

        if (dialog.isSaved()) {
            int year = dialog.getSelectedYear();
            int month = dialog.getSelectedMonth();
            int roomOcc = dialog.getRoomOccupancy();
            int bedOcc = dialog.getBedOccupancy();

            table6.setValueAt(roomOcc, row, 2);
            table6.setValueAt(bedOcc, row, 3);
            table6.setValueAt(month, row, 4);
            table6.setValueAt(year, row, 5);

            Map<String, Object> occ = new HashMap<>();
            occ.put("id", hotelId);
            occ.put("hotelName", hotelName);
            occ.put("year", year);
            occ.put("month", month);
            occ.put("roomOcc", roomOcc);
            occ.put("bedOcc", bedOcc);
            occupancyDataList.add(occ);

            // Update Last Transaction in Hotel List
            for (int i = 0; i < table1.getRowCount(); i++) {
                int idInTable1 = Integer.parseInt(table1.getValueAt(i, 0).toString());
                if (idInTable1 == hotelId) {
                    String today = java.time.LocalDate.now().toString();
                    table1.setValueAt(today, i, table1.getColumnCount() - 1);
                    break;
                }
            }
        }
    }

    private void saveTransactionalData() {
        String hotelFile = "src/main/java/org/example/data/txt/hotels.txt";
        List<Hotel> hotels = HotelFileReader.readHotelsFromFile(hotelFile);
        Map<Integer, Hotel> hotelMap = new HashMap<>();
        for (Hotel h : hotels) {
            hotelMap.put(h.getId(), h);
        }

        File out = new File("src/main/java/org/example/data/txt/occupancies.txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(out, true))) {
            for (Map<String,Object> occ : occupancyDataList) {
                int id        = (Integer) occ.get("id");
                int usedRooms = (Integer) occ.get("roomOcc");
                int usedBeds  = (Integer) occ.get("bedOcc");
                int year      = (Integer) occ.get("year");
                int month     = (Integer) occ.get("month");

                Hotel h = hotelMap.get(id);
                int totalRooms = h.getNoRooms();
                int totalBeds  = h.getNoBeds();

                String line = String.format(
                        "%d,%d,%d,%d,%d,%d,%d",
                        id, totalRooms, usedRooms, totalBeds, usedBeds, year, month
                );

                bw.write(line);
                bw.newLine();
            }
            bw.flush();

            JOptionPane.showMessageDialog(
                    this,
                    "Transactional data saved to file!",
                    "Save",
                    JOptionPane.INFORMATION_MESSAGE
            );
            occupancyDataList.clear();

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error writing occupancies.txt:\n" + ex.getMessage(),
                    "Save Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void deleteSelectedTransactionalData() {
        int selectedRow = table6.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row to delete.");
            return;
        }
        // Setze Room Occ, Bed Occ, Month, Year (Spalten 2–5) auf leer
        for (int col = 2; col <= 5; col++) {
            table6.setValueAt("", selectedRow, col);
        }
        // Optional: Wenn du eine occupancyDataList benutzt, dort ebenfalls den Eintrag löschen!
        // occupancyDataList.removeIf(occ -> (int)occ.get("id") == Integer.parseInt(table6.getValueAt(selectedRow, 0).toString()));
    }

    // ===== Methoden für das Transactional Data List Panel =====

    private void ladeTransaktionsDatenMitAttributen() {
        String hotelFile = "src/main/java/org/example/data/txt/hotels.txt";
        String occFile   = "src/main/java/org/example/data/txt/occupancies.txt";

        List<Hotel> hotels = HotelFileReader.readHotelsFromFile(hotelFile);
        List<Occupancy> occsAll = OccupancyFileReader.readOccupanciesFromFile(occFile, hotels);

        String selHotel = (String) comboBox18.getSelectedItem();
        List<Occupancy> toDisplay;
        if (selHotel != null && !selHotel.equals("---select---")) {
            toDisplay = occsAll.stream()
                    .filter(o -> o.getHotel().getName().equals(selHotel))
                    .collect(Collectors.toList());
        } else {
            toDisplay = occsAll;
        }

        toDisplay.sort(
                Comparator.comparingInt(Occupancy::getYear)
                        .thenComparingInt(Occupancy::getMonth)
        );

        DefaultTableModel model = new DefaultTableModel(
                new Object[]{ "Hotel Name","Room Occupancy","Bed Occupancy","Month","Year","Attribute" },
                0
        );
        for (Occupancy o : toDisplay) {
            model.addRow(new Object[]{
                    o.getHotel().getName(),
                    o.getUsedRooms(),
                    o.getUsedBeds(),
                    getMonthName(o.getMonth()),
                    o.getYear(),
                    ""  // Platz für Attribute
            });
        }
        table3.setModel(model);
    }

    // ===== PANEL 5: =====
    private void ladeCombinedOverviewData() {
        // 1) Lese alle Transaktionen ein
        String hotelFile = "src/main/java/org/example/data/txt/hotels.txt";
        String occFile   = "src/main/java/org/example/data/txt/occupancies.txt";
        List<Hotel> hotels = HotelFileReader.readHotelsFromFile(hotelFile);
        List<Occupancy> allOcc = OccupancyFileReader.readOccupanciesFromFile(occFile, hotels);

        // 2) Welches Hotel ist ausgewählt?
        String selHotel = (String) comboBox16.getSelectedItem();

        // 3) Filtere (oder zeige alles bei "---select---")
        List<Occupancy> toDisplay = allOcc.stream()
                .filter(o -> selHotel.equals("---select---")
                        || o.getHotel().getName().equals(selHotel))
                .collect(Collectors.toList());

        // 4) Sortiere nach Jahr/Monat
        toDisplay.sort(Comparator
                .comparingInt(Occupancy::getYear)
                .thenComparingInt(Occupancy::getMonth));



        // Wir zeigen nur den neuesten Eintrag
        Occupancy latest = toDisplay.get(toDisplay.size() - 1);
        // Und setze noch Jahr/Monat/Attribute-Combos, falls gewünscht:
        comboBox12.setSelectedItem(String.valueOf(latest.getYear()));
        comboBox13.setSelectedIndex(latest.getMonth() - 1);
    }

    //part 2:
    private void updateCombinedHotelDetails() {
        // 1) Lese alle Hotels
        List<Hotel> hotels = HotelFileReader.readHotelsFromFile("src/main/java/org/example/data/txt/hotels.txt");

        String sel = (String) comboBox16.getSelectedItem();
        // 2) Wenn gar nichts oder Default ausgewählt, dann leere Kopfzeile
        if (sel == null || sel.equals("---select---")) {
            table4.setModel(new DefaultTableModel(
                    new Object[0][],
                    new String[]{"ID", "Address", "City", "PLZ"}
            ));
            return;
        }

        // 3) Sonst genau das eine Hotel finden
        Hotel h = hotels.stream()
                .filter(x -> x.getName().equals(sel))
                .findFirst()
                .orElse(null);
        if (h == null) {
            // falls Name merkwürdig war
            table4.setModel(new DefaultTableModel(
                    new Object[0][],
                    new String[]{"ID", "Address", "City", "PLZ"}
            ));
            return;
        }

        // 4) Und in einer Zeile anzeigen
        DefaultTableModel m = new DefaultTableModel(
                new String[]{"ID", "Address", "City", "PLZ"}, 0
        );
        m.addRow(new Object[]{ h.getId(), h.getAddress(), h.getCity(), h.getCityCode() });
        table4.setModel(m);
    }
    private void ladeCombinedOverview() {
        // 1) Dateien einlesen
        String hotelFile = "src/main/java/org/example/data/txt/hotels.txt";
        String occFile   = "src/main/java/org/example/data/txt/occupancies.txt";
        List<Hotel> hotels = HotelFileReader.readHotelsFromFile(hotelFile);
        List<Occupancy> occs = OccupancyFileReader.readOccupanciesFromFile(occFile, hotels);

        // 2) Auswahl aus den Comboboxen holen
        String selHotel    = (String) comboBox16.getSelectedItem();
        String selCategory = (String) comboBox1 .getSelectedItem();
        String selYearStr  = (String) comboBox12.getSelectedItem();
        String selMonthStr = (String) comboBox13.getSelectedItem();

        // 3) Stream über alle Occupancy-Records aufbauen
        Stream<Occupancy> stream = occs.stream();

        // 3a) erstmal nach Hotel-Name filtern, falls ausgewählt
        if (selHotel != null && !selHotel.equals("---select---")) {
            stream = stream.filter(o -> o.getHotel().getName().equals(selHotel));
        }
        // 3b) sonst nach Kategorie filtern, falls ausgewählt
        else if (selCategory != null && !selCategory.equals("*")) {
            stream = stream.filter(o -> o.getHotel().getCategory().equals(selCategory));
        }

        // 3c) Jahr filtern, falls nicht Default
        if (selYearStr != null && !selYearStr.equals("---select---")) {
            int selYear = Integer.parseInt(selYearStr);
            stream = stream.filter(o -> o.getYear() == selYear);
        }

        // 3d) Monat filtern, falls nicht Default
        if (selMonthStr != null && !selMonthStr.equals("---select---")) {
            // Da wir "---select---" an Index 0 eingefügt haben,
            // entspricht comboBox13.getSelectedIndex() genau dem Monat (1=Jan,…)
            int selMonth = comboBox13.getSelectedIndex();
            stream = stream.filter(o -> o.getMonth() == selMonth);
        }

        // 4) sortieren nach Jahr/Monat
        List<Occupancy> toDisplay = stream
                .sorted(Comparator.comparingInt(Occupancy::getYear)
                        .thenComparingInt(Occupancy::getMonth))
                .collect(Collectors.toList());

        // 5) TableModel neu aufbauen – eine Zeile pro Occupancy
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{ "Hotel Name","Rooms","Room Occupancy","Beds","Bed Occupancy" }, 0
        );
        for (Occupancy o : toDisplay) {
            Hotel h = o.getHotel();
            model.addRow(new Object[]{
                    h.getName(),
                    h.getNoRooms(),
                    o.getUsedRooms(),
                    h.getNoBeds(),
                    o.getUsedBeds()
            });
        }

        // 6) ins UI eintragen
        table2.setModel(model);
    }















    // ===== Hilfsmethoden =====

    private void ladeDropdownHotels() {
        String hotelFile = "src/main/java/org/example/data/txt/hotels.txt";
        String occFile   = "src/main/java/org/example/data/txt/occupancies.txt";

        List<Hotel> hotels = HotelFileReader.readHotelsFromFile(hotelFile);
        List<Occupancy> occsAll = OccupancyFileReader.readOccupanciesFromFile(occFile, hotels);

        Set<String> names = new TreeSet<>();
        for (Occupancy o : occsAll) {
            names.add(o.getHotel().getName());
        }
        comboBox18.removeAllItems();
        comboBox18.addItem("---select---");
        for (String n : names) {
            comboBox18.addItem(n);
        }
    }

    private String getMonthName(int month) {
        String[] months = {
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        };
        return (month >= 1 && month <= 12) ? months[month - 1] : "Unknown";
    }

    private void logout() {
        JFrame top = (JFrame) SwingUtilities.getWindowAncestor(this);
        top.setContentPane(new login());
        top.pack();
        top.setLocationRelativeTo(null);
    }

    private void showHelp() {
        JOptionPane.showMessageDialog(
                this,
                "Welcome to the Lower Austria Tourist Portal!\n\n" +
                        "Here’s how to use this application:\n" +
                        "1. In the Hotels tab, view and edit master data for all hotels.\n" +
                        "2. In Hotels Summary, see aggregate statistics per hotel category.\n" +
                        "3. In Occupancy, select a year, month, and category to view occupancy trends.\n" +
                        "4. In Occupancy Summary, choose date ranges or hotel to see summarized occupancy data.\n\n" +
                        "Use the +, save and logout buttons as needed. If you need further assistance,\n" +
                        "please consult the user guide or contact support@example.com.",
                "Help",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void Add(ActionEvent e) {
        // TODO add your code here
    }

    private void button6(ActionEvent e) {
        // TODO add your code here
    }

    private void AddButton(ActionEvent e) {
        // TODO add your code here
    }

    private void EditButton(ActionEvent e) {
        // TODO add your code here
    }

    private void button7(ActionEvent e) {
        // TODO add your code here
    }

    // ===== JFormDesigner initComponents und Variablen-Deklaration =====
    // ... hier bleibt alles wie vom JFormDesigner erzeugt!



    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Educational license - Amelie Pötscher (fhb232606)
        this2 = new JPanel();
        label21 = new JLabel();
        tabbedPane1 = new JTabbedPane();
        panel1 = new JPanel();
        panel7 = new JPanel();
        panel8 = new JPanel();
        button1 = new JButton();
        button2 = new JButton();
        scrollPane1 = new JScrollPane();
        table1 = new JTable();
        button6 = new JButton();
        button25 = new JButton();
        deleteButton = new JButton();
        button3 = new JButton();
        panel3 = new JPanel();
        panel17 = new JPanel();
        button15 = new JButton();
        scrollPane5 = new JScrollPane();
        table5 = new JTable();
        panel25 = new JPanel();
        button26 = new JButton();
        button27 = new JButton();
        panel2 = new JPanel();
        scrollPane6 = new JScrollPane();
        table6 = new JTable();
        button4 = new JButton();
        panel26 = new JPanel();
        button28 = new JButton();
        button29 = new JButton();
        button5 = new JButton();
        button7 = new JButton();
        panel5 = new JPanel();
        panel21 = new JPanel();
        panel22 = new JPanel();
        button19 = new JButton();
        button20 = new JButton();
        button21 = new JButton();
        comboBox7 = new JComboBox<>();
        label12 = new JLabel();
        label13 = new JLabel();
        comboBox8 = new JComboBox<>();
        scrollPane3 = new JScrollPane();
        table3 = new JTable();
        label14 = new JLabel();
        comboBox9 = new JComboBox<>();
        label15 = new JLabel();
        comboBox10 = new JComboBox<>();
        comboBox17 = new JComboBox<>();
        label23 = new JLabel();
        label25 = new JLabel();
        comboBox18 = new JComboBox<>();
        panel6 = new JPanel();
        panel23 = new JPanel();
        panel24 = new JPanel();
        button22 = new JButton();
        button23 = new JButton();
        comboBox12 = new JComboBox<>();
        label16 = new JLabel();
        label17 = new JLabel();
        comboBox13 = new JComboBox<>();
        comboBox16 = new JComboBox<>();
        label7 = new JLabel();
        scrollPane4 = new JScrollPane();
        table4 = new JTable();
        comboBox1 = new JComboBox<>();
        scrollPane2 = new JScrollPane();
        table2 = new JTable();

        //======== this ========
        setPreferredSize(new Dimension(900, 600));

        //======== this2 ========
        {

            GroupLayout this2Layout = new GroupLayout(this2);
            this2.setLayout(this2Layout);
            this2Layout.setHorizontalGroup(
                this2Layout.createParallelGroup()
                    .addGap(0, 29, Short.MAX_VALUE)
            );
            this2Layout.setVerticalGroup(
                this2Layout.createParallelGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
            );
        }

        //---- label21 ----
        label21.setIcon(new ImageIcon(getClass().getResource("/logo2.png")));

        //======== tabbedPane1 ========
        {
            tabbedPane1.setPreferredSize(new Dimension(700, 400));

            //======== panel1 ========
            {
                panel1.setBackground(Color.white);

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
                                .addGroup(panel8Layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(panel8Layout.createParallelGroup()
                                        .addComponent(button1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(panel8Layout.createSequentialGroup()
                                            .addComponent(button2, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
                                            .addGap(0, 0, Short.MAX_VALUE)))
                                    .addContainerGap())
                        );
                        panel8Layout.setVerticalGroup(
                            panel8Layout.createParallelGroup()
                                .addGroup(GroupLayout.Alignment.TRAILING, panel8Layout.createSequentialGroup()
                                    .addGap(25, 25, 25)
                                    .addComponent(button2)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(button1)
                                    .addGap(23, 23, 23))
                        );
                    }

                    //======== scrollPane1 ========
                    {

                        //---- table1 ----
                        table1.setModel(new DefaultTableModel(
                            new Object[][] {
                                {"1", "Hotel Alpha", "Vienna", "20", "35"},
                                {"2", "Hotel Beta", "Graz", "30", "45"},
                                {"3", "Hotel Gamma", "Linz", "40", "55"},
                                {"4", "Hotel Delta", "Salzburg ", "50", "65"},
                                {"5", "Hotel Epsilon", "Klagenfurt", "60", "75"},
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
                            cm.getColumn(0).setPreferredWidth(5);
                        }
                        scrollPane1.setViewportView(table1);
                    }

                    //---- button6 ----
                    button6.setText("+");
                    button6.addActionListener(e -> {
			Add(e);
			button6(e);
			AddButton(e);
		});

                    //---- button25 ----
                    button25.setText("Save");

                    //---- deleteButton ----
                    deleteButton.setText("Delete");

                    //---- button3 ----
                    button3.setText("Edit");
                    button3.addActionListener(e -> EditButton(e));

                    GroupLayout panel7Layout = new GroupLayout(panel7);
                    panel7.setLayout(panel7Layout);
                    panel7Layout.setHorizontalGroup(
                        panel7Layout.createParallelGroup()
                            .addGroup(panel7Layout.createSequentialGroup()
                                .addComponent(panel8, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel7Layout.createParallelGroup()
                                    .addGroup(panel7Layout.createSequentialGroup()
                                        .addComponent(button6)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(button3)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(deleteButton)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(button25)
                                        .addGap(94, 94, 94))
                                    .addGroup(panel7Layout.createSequentialGroup()
                                        .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 715, Short.MAX_VALUE)
                                        .addContainerGap())))
                    );
                    panel7Layout.setVerticalGroup(
                        panel7Layout.createParallelGroup()
                            .addGroup(panel7Layout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 302, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel7Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(button6)
                                    .addComponent(deleteButton)
                                    .addComponent(button25)
                                    .addComponent(button3))
                                .addGap(28, 28, 28))
                            .addComponent(panel8, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
            tabbedPane1.addTab("Hotel List", panel1);

            //======== panel3 ========
            {
                panel3.setBackground(Color.white);

                //======== panel17 ========
                {

                    //---- button15 ----
                    button15.setText("update");

                    //======== scrollPane5 ========
                    {

                        //---- table5 ----
                        table5.setModel(new DefaultTableModel(
                            new Object[][] {
                                {"\u2605", "20", "40", "70"},
                                {"\u2605\u2605", "25", "50", "75"},
                                {"\u2605\u2605\u2605", "30", "35", "50"},
                                {"\u2605\u2605\u2605\u2605", "20", "30", "40"},
                                {"\u2605\u2605\u2605\u2605\u2605", "15", "25", "30"},
                            },
                            new String[] {
                                "category", "hotels per category", "\u00d8 rooms", "\u00d8 beds"
                            }
                        ));
                        scrollPane5.setViewportView(table5);
                    }

                    //======== panel25 ========
                    {
                        panel25.setBackground(new Color(0x3366ff));

                        //---- button26 ----
                        button26.setText("Log Out");
                        button26.setBackground(Color.lightGray);

                        //---- button27 ----
                        button27.setText("Help");
                        button27.setBackground(Color.lightGray);

                        GroupLayout panel25Layout = new GroupLayout(panel25);
                        panel25.setLayout(panel25Layout);
                        panel25Layout.setHorizontalGroup(
                            panel25Layout.createParallelGroup()
                                .addGroup(panel25Layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(panel25Layout.createParallelGroup()
                                        .addGroup(panel25Layout.createSequentialGroup()
                                            .addComponent(button27, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
                                            .addGap(0, 0, Short.MAX_VALUE))
                                        .addComponent(button26, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addContainerGap())
                        );
                        panel25Layout.setVerticalGroup(
                            panel25Layout.createParallelGroup()
                                .addGroup(GroupLayout.Alignment.TRAILING, panel25Layout.createSequentialGroup()
                                    .addGap(25, 25, 25)
                                    .addComponent(button27)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 260, Short.MAX_VALUE)
                                    .addComponent(button26)
                                    .addGap(40, 40, 40))
                        );
                    }

                    GroupLayout panel17Layout = new GroupLayout(panel17);
                    panel17.setLayout(panel17Layout);
                    panel17Layout.setHorizontalGroup(
                        panel17Layout.createParallelGroup()
                            .addGroup(GroupLayout.Alignment.TRAILING, panel17Layout.createSequentialGroup()
                                .addComponent(panel25, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGroup(panel17Layout.createParallelGroup()
                                    .addGroup(panel17Layout.createSequentialGroup()
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(scrollPane5, GroupLayout.PREFERRED_SIZE, 562, GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap(159, Short.MAX_VALUE))
                                    .addGroup(GroupLayout.Alignment.TRAILING, panel17Layout.createSequentialGroup()
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 470, Short.MAX_VALUE)
                                        .addComponent(button15)
                                        .addGap(184, 184, 184))))
                    );
                    panel17Layout.setVerticalGroup(
                        panel17Layout.createParallelGroup()
                            .addGroup(panel17Layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(scrollPane5, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(button15)
                                .addGap(47, 47, 47))
                            .addGroup(panel17Layout.createSequentialGroup()
                                .addComponent(panel25, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                    );
                }

                GroupLayout panel3Layout = new GroupLayout(panel3);
                panel3.setLayout(panel3Layout);
                panel3Layout.setHorizontalGroup(
                    panel3Layout.createParallelGroup()
                        .addComponent(panel17, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                );
                panel3Layout.setVerticalGroup(
                    panel3Layout.createParallelGroup()
                        .addComponent(panel17, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                );
            }
            tabbedPane1.addTab("Hotel Summary", panel3);

            //======== panel2 ========
            {

                //======== scrollPane6 ========
                {

                    //---- table6 ----
                    table6.setModel(new DefaultTableModel(
                        new Object[][] {
                            {"", "", null, null, null, null, null},
                        },
                        new String[] {
                            "ID", "Hotel Name", "Room Occupany", "Bed Occupancy", "Month", "Year", "Attributes"
                        }
                    ));
                    scrollPane6.setViewportView(table6);
                }

                //---- button4 ----
                button4.setText("Save");

                //======== panel26 ========
                {
                    panel26.setBackground(new Color(0x3366ff));

                    //---- button28 ----
                    button28.setText("Log Out");
                    button28.setBackground(Color.lightGray);

                    //---- button29 ----
                    button29.setText("Help");
                    button29.setBackground(Color.lightGray);

                    GroupLayout panel26Layout = new GroupLayout(panel26);
                    panel26.setLayout(panel26Layout);
                    panel26Layout.setHorizontalGroup(
                        panel26Layout.createParallelGroup()
                            .addGroup(panel26Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panel26Layout.createParallelGroup()
                                    .addComponent(button28, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(panel26Layout.createSequentialGroup()
                                        .addComponent(button29, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
                    );
                    panel26Layout.setVerticalGroup(
                        panel26Layout.createParallelGroup()
                            .addGroup(GroupLayout.Alignment.TRAILING, panel26Layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(button29)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 269, Short.MAX_VALUE)
                                .addComponent(button28)
                                .addGap(23, 23, 23))
                    );
                }

                //---- button5 ----
                button5.setText("Add Attribute");

                //---- button7 ----
                button7.setText("Delete");
                button7.addActionListener(e -> {
			button7(e);
			button7(e);
		});

                GroupLayout panel2Layout = new GroupLayout(panel2);
                panel2.setLayout(panel2Layout);
                panel2Layout.setHorizontalGroup(
                    panel2Layout.createParallelGroup()
                        .addGroup(panel2Layout.createSequentialGroup()
                            .addComponent(panel26, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGroup(panel2Layout.createParallelGroup()
                                .addGroup(panel2Layout.createSequentialGroup()
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 313, Short.MAX_VALUE)
                                    .addComponent(button7)
                                    .addGap(26, 26, 26)
                                    .addComponent(button5)
                                    .addGap(18, 18, 18)
                                    .addComponent(button4)
                                    .addGap(112, 112, 112))
                                .addGroup(panel2Layout.createSequentialGroup()
                                    .addGap(18, 18, 18)
                                    .addComponent(scrollPane6, GroupLayout.PREFERRED_SIZE, 571, GroupLayout.PREFERRED_SIZE)
                                    .addContainerGap(138, Short.MAX_VALUE))))
                );
                panel2Layout.setVerticalGroup(
                    panel2Layout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                            .addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(panel26, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(panel2Layout.createSequentialGroup()
                                    .addContainerGap(10, Short.MAX_VALUE)
                                    .addComponent(scrollPane6, GroupLayout.PREFERRED_SIZE, 298, GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(button5)
                                        .addComponent(button4)
                                        .addComponent(button7))
                                    .addGap(13, 13, 13)))
                            .addContainerGap())
                );
            }
            tabbedPane1.addTab("Transactional Data", panel2);

            //======== panel5 ========
            {
                panel5.setBackground(Color.white);

                //======== panel21 ========
                {

                    //======== panel22 ========
                    {
                        panel22.setBackground(new Color(0x3366ff));

                        //---- button19 ----
                        button19.setText("Log Out");
                        button19.setBackground(Color.lightGray);

                        //---- button20 ----
                        button20.setText("Help");
                        button20.setBackground(Color.lightGray);

                        GroupLayout panel22Layout = new GroupLayout(panel22);
                        panel22.setLayout(panel22Layout);
                        panel22Layout.setHorizontalGroup(
                            panel22Layout.createParallelGroup()
                                .addGroup(panel22Layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(panel22Layout.createParallelGroup()
                                        .addComponent(button19, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(panel22Layout.createSequentialGroup()
                                            .addComponent(button20, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
                                            .addGap(0, 0, Short.MAX_VALUE)))
                                    .addContainerGap())
                        );
                        panel22Layout.setVerticalGroup(
                            panel22Layout.createParallelGroup()
                                .addGroup(GroupLayout.Alignment.TRAILING, panel22Layout.createSequentialGroup()
                                    .addGap(25, 25, 25)
                                    .addComponent(button20)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(button19)
                                    .addGap(23, 23, 23))
                        );
                    }

                    //---- button21 ----
                    button21.setText("save");

                    //---- comboBox7 ----
                    comboBox7.setModel(new DefaultComboBoxModel<>(new String[] {
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

                    //---- label12 ----
                    label12.setText("from:");

                    //---- label13 ----
                    label13.setText("from:");

                    //---- comboBox8 ----
                    comboBox8.setModel(new DefaultComboBoxModel<>(new String[] {
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

                    //======== scrollPane3 ========
                    {

                        //---- table3 ----
                        table3.setModel(new DefaultTableModel(
                            new Object[][] {
                                {"January", "2023", "10", "15"},
                                {"February", "2023", "20", "25"},
                                {"March", "2023", "30", "35"},
                                {"April", "2023", "40", "45"},
                                {"May", "2023", "50", "55"},
                                {"June", "2023", "20", "30"},
                                {"July", "2023", "35", "44"},
                                {"August", "2023", "55", "78"},
                                {"September", "2023", "62", "88"},
                                {"October", "2023", "44", "65"},
                                {"November", "2023", "58", "72"},
                                {"December", "2023", "60", "70"},
                            },
                            new String[] {
                                "Month", "Year", "Occup. rooms", "Occup. beds"
                            }
                        ));
                        scrollPane3.setViewportView(table3);
                    }

                    //---- label14 ----
                    label14.setText("to:");

                    //---- comboBox9 ----
                    comboBox9.setModel(new DefaultComboBoxModel<>(new String[] {
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

                    //---- label15 ----
                    label15.setText("to:");

                    //---- comboBox10 ----
                    comboBox10.setModel(new DefaultComboBoxModel<>(new String[] {
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

                    //---- comboBox17 ----
                    comboBox17.setModel(new DefaultComboBoxModel<>(new String[] {
                        "\u2605",
                        "\u2605\u2605",
                        "\u2605\u2605\u2605",
                        "\u2605\u2605\u2605\u2605",
                        "\u2605\u2605\u2605\u2605\u2605"
                    }));

                    //---- label23 ----
                    label23.setText("category:");

                    //---- label25 ----
                    label25.setText("hotel:");

                    //---- comboBox18 ----
                    comboBox18.setModel(new DefaultComboBoxModel<>(new String[] {
                        "---select---",
                        "Hotel Alpha",
                        "Hotel Beta",
                        "Hotel Gamma ",
                        "Hotel Delta",
                        "Hotel Epsilon"
                    }));

                    GroupLayout panel21Layout = new GroupLayout(panel21);
                    panel21.setLayout(panel21Layout);
                    panel21Layout.setHorizontalGroup(
                        panel21Layout.createParallelGroup()
                            .addGroup(panel21Layout.createSequentialGroup()
                                .addComponent(panel22, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGroup(panel21Layout.createParallelGroup()
                                    .addGroup(panel21Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(panel21Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                            .addGroup(GroupLayout.Alignment.LEADING, panel21Layout.createSequentialGroup()
                                                .addComponent(scrollPane3, GroupLayout.PREFERRED_SIZE, 531, GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 155, Short.MAX_VALUE))
                                            .addGroup(GroupLayout.Alignment.LEADING, panel21Layout.createSequentialGroup()
                                                .addGap(399, 399, 399)
                                                .addComponent(button21)
                                                .addContainerGap(215, Short.MAX_VALUE))))
                                    .addGroup(panel21Layout.createSequentialGroup()
                                        .addGap(7, 7, 7)
                                        .addGroup(panel21Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                            .addComponent(label23)
                                            .addComponent(label25))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(panel21Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                            .addComponent(comboBox17)
                                            .addComponent(comboBox18, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(panel21Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                            .addComponent(label12)
                                            .addComponent(label14))
                                        .addGap(6, 6, 6)
                                        .addGroup(panel21Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                            .addGroup(panel21Layout.createSequentialGroup()
                                                .addComponent(comboBox9, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(label15))
                                            .addGroup(panel21Layout.createSequentialGroup()
                                                .addComponent(comboBox7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(31, 31, 31)
                                                .addComponent(label13)))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(panel21Layout.createParallelGroup()
                                            .addComponent(comboBox8, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                            .addGroup(panel21Layout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addComponent(comboBox10, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                        .addGap(0, 226, Short.MAX_VALUE))))
                    );
                    panel21Layout.setVerticalGroup(
                        panel21Layout.createParallelGroup()
                            .addGroup(GroupLayout.Alignment.TRAILING, panel21Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addGroup(panel21Layout.createParallelGroup()
                                    .addGroup(panel21Layout.createSequentialGroup()
                                        .addGroup(panel21Layout.createParallelGroup()
                                            .addComponent(label25)
                                            .addComponent(comboBox18, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(panel21Layout.createParallelGroup()
                                            .addComponent(label23)
                                            .addComponent(comboBox17, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(panel21Layout.createSequentialGroup()
                                        .addGroup(panel21Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(comboBox7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label12)
                                            .addComponent(label13)
                                            .addComponent(comboBox8, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(panel21Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(comboBox9, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label14)
                                            .addComponent(label15)
                                            .addComponent(comboBox10, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
                                .addComponent(scrollPane3, GroupLayout.PREFERRED_SIZE, 236, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(button21)
                                .addGap(26, 26, 26))
                            .addComponent(panel22, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    );
                }

                GroupLayout panel5Layout = new GroupLayout(panel5);
                panel5.setLayout(panel5Layout);
                panel5Layout.setHorizontalGroup(
                    panel5Layout.createParallelGroup()
                        .addGroup(panel5Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(panel21, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGap(17, 17, 17))
                );
                panel5Layout.setVerticalGroup(
                    panel5Layout.createParallelGroup()
                        .addComponent(panel21, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                );
            }
            tabbedPane1.addTab("Transactional Data List", panel5);

            //======== panel6 ========
            {
                panel6.setBackground(Color.white);

                //======== panel23 ========
                {

                    //======== panel24 ========
                    {
                        panel24.setBackground(new Color(0x3366ff));

                        //---- button22 ----
                        button22.setText("Log Out");
                        button22.setBackground(Color.lightGray);

                        //---- button23 ----
                        button23.setText("Help");
                        button23.setBackground(Color.lightGray);

                        GroupLayout panel24Layout = new GroupLayout(panel24);
                        panel24.setLayout(panel24Layout);
                        panel24Layout.setHorizontalGroup(
                            panel24Layout.createParallelGroup()
                                .addGroup(panel24Layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(panel24Layout.createParallelGroup()
                                        .addGroup(panel24Layout.createSequentialGroup()
                                            .addComponent(button23, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
                                            .addGap(0, 0, Short.MAX_VALUE))
                                        .addComponent(button22, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addContainerGap())
                        );
                        panel24Layout.setVerticalGroup(
                            panel24Layout.createParallelGroup()
                                .addGroup(GroupLayout.Alignment.TRAILING, panel24Layout.createSequentialGroup()
                                    .addGap(25, 25, 25)
                                    .addComponent(button23)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(button22)
                                    .addGap(25, 25, 25))
                        );
                    }

                    //---- comboBox12 ----
                    comboBox12.setModel(new DefaultComboBoxModel<>(new String[] {
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

                    //---- label16 ----
                    label16.setText("year:");

                    //---- label17 ----
                    label17.setText("month:");

                    //---- comboBox13 ----
                    comboBox13.setModel(new DefaultComboBoxModel<>(new String[] {
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

                    //---- comboBox16 ----
                    comboBox16.setModel(new DefaultComboBoxModel<>(new String[] {
                        "Hotel Alpha",
                        "Hotel Beta",
                        "Hotel Gamma ",
                        "Hotel Delta",
                        "Hotel Epsilon"
                    }));
                    comboBox16.setFont(new Font(".AppleSystemUIFont", Font.PLAIN, 15));
                    comboBox16.setPreferredSize(new Dimension(80, 25));

                    //---- label7 ----
                    label7.setText("category:");

                    //======== scrollPane4 ========
                    {

                        //---- table4 ----
                        table4.setModel(new DefaultTableModel(
                            new Object[][] {
                                {"adresse:", "Vienna"},
                            },
                            new String[] {
                                "ID:", "1"
                            }
                        ));
                        scrollPane4.setViewportView(table4);
                    }

                    //---- comboBox1 ----
                    comboBox1.setModel(new DefaultComboBoxModel<>(new String[] {
                        "*",
                        "**",
                        "***",
                        "****",
                        "*****"
                    }));

                    //======== scrollPane2 ========
                    {

                        //---- table2 ----
                        table2.setModel(new DefaultTableModel(
                            new Object[][] {
                                {null, null, null, null, null},
                                {null, null, null, null, null},
                            },
                            new String[] {
                                "hotel name", "rooms", "room occupancy", "beds", "bed occupancy"
                            }
                        ));
                        scrollPane2.setViewportView(table2);
                    }

                    GroupLayout panel23Layout = new GroupLayout(panel23);
                    panel23.setLayout(panel23Layout);
                    panel23Layout.setHorizontalGroup(
                        panel23Layout.createParallelGroup()
                            .addGroup(panel23Layout.createSequentialGroup()
                                .addComponent(panel24, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(panel23Layout.createParallelGroup()
                                    .addGroup(panel23Layout.createSequentialGroup()
                                        .addGroup(panel23Layout.createParallelGroup()
                                            .addComponent(comboBox16, GroupLayout.PREFERRED_SIZE, 193, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(scrollPane4, GroupLayout.PREFERRED_SIZE, 257, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(panel23Layout.createParallelGroup()
                                            .addGroup(panel23Layout.createSequentialGroup()
                                                .addGap(88, 88, 88)
                                                .addComponent(label16)
                                                .addGap(18, 18, 18)
                                                .addComponent(comboBox12, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                            .addGroup(panel23Layout.createSequentialGroup()
                                                .addGap(64, 64, 64)
                                                .addGroup(panel23Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                    .addComponent(label7)
                                                    .addComponent(label17))
                                                .addGroup(panel23Layout.createParallelGroup()
                                                    .addGroup(panel23Layout.createSequentialGroup()
                                                        .addGap(18, 18, 18)
                                                        .addComponent(comboBox13, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(panel23Layout.createSequentialGroup()
                                                        .addGap(18, 18, 18)
                                                        .addComponent(comboBox1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))))
                                    .addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, 530, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(179, Short.MAX_VALUE))
                    );
                    panel23Layout.setVerticalGroup(
                        panel23Layout.createParallelGroup()
                            .addComponent(panel24, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(GroupLayout.Alignment.TRAILING, panel23Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addGroup(panel23Layout.createParallelGroup()
                                    .addGroup(panel23Layout.createSequentialGroup()
                                        .addComponent(comboBox16, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(scrollPane4, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panel23Layout.createSequentialGroup()
                                        .addGroup(panel23Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(label16)
                                            .addComponent(comboBox12, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(panel23Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(label17)
                                            .addComponent(comboBox13, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(panel23Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(label7)
                                            .addComponent(comboBox1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                                .addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, 181, GroupLayout.PREFERRED_SIZE)
                                .addGap(23, 23, 23))
                    );
                }

                GroupLayout panel6Layout = new GroupLayout(panel6);
                panel6.setLayout(panel6Layout);
                panel6Layout.setHorizontalGroup(
                    panel6Layout.createParallelGroup()
                        .addComponent(panel23, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                );
                panel6Layout.setVerticalGroup(
                    panel6Layout.createParallelGroup()
                        .addComponent(panel23, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                );
            }
            tabbedPane1.addTab("combined Overview ", panel6);
        }

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(this2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(tabbedPane1, GroupLayout.PREFERRED_SIZE, 822, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(label21, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addComponent(label21, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                    .addComponent(this2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(412, 412, 412))
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(tabbedPane1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(119, Short.MAX_VALUE))
        );
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Educational license - Amelie Pötscher (fhb232606)
    private JPanel this2;
    private JLabel label21;
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JPanel panel7;
    private JPanel panel8;
    private JButton button1;
    private JButton button2;
    private JScrollPane scrollPane1;
    private JTable table1;
    private JButton button6;
    private JButton button25;
    private JButton deleteButton;
    private JButton button3;
    private JPanel panel3;
    private JPanel panel17;
    private JButton button15;
    private JScrollPane scrollPane5;
    private JTable table5;
    private JPanel panel25;
    private JButton button26;
    private JButton button27;
    private JPanel panel2;
    private JScrollPane scrollPane6;
    private JTable table6;
    private JButton button4;
    private JPanel panel26;
    private JButton button28;
    private JButton button29;
    private JButton button5;
    private JButton button7;
    private JPanel panel5;
    private JPanel panel21;
    private JPanel panel22;
    private JButton button19;
    private JButton button20;
    private JButton button21;
    private JComboBox<String> comboBox7;
    private JLabel label12;
    private JLabel label13;
    private JComboBox<String> comboBox8;
    private JScrollPane scrollPane3;
    private JTable table3;
    private JLabel label14;
    private JComboBox<String> comboBox9;
    private JLabel label15;
    private JComboBox<String> comboBox10;
    private JComboBox<String> comboBox17;
    private JLabel label23;
    private JLabel label25;
    private JComboBox<String> comboBox18;
    private JPanel panel6;
    private JPanel panel23;
    private JPanel panel24;
    private JButton button22;
    private JButton button23;
    private JComboBox<String> comboBox12;
    private JLabel label16;
    private JLabel label17;
    private JComboBox<String> comboBox13;
    private JComboBox<String> comboBox16;
    private JLabel label7;
    private JScrollPane scrollPane4;
    private JTable table4;
    private JComboBox<String> comboBox1;
    private JScrollPane scrollPane2;
    private JTable table2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    private class save extends AbstractAction {
        private save() {
            // JFormDesigner - Action initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
            // Generated using JFormDesigner Educational license - Amelie Pötscher (fhb232606)
            // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
        }

        public void actionPerformed(ActionEvent e) {
            try {
                DefaultTableModel model = (DefaultTableModel) table1.getModel();
                List<Hotel> hotelListe = new ArrayList<>();

                for (int i = 0; i < model.getRowCount(); i++) {
                    String heute = LocalDate.now().toString();
                    int id = Integer.parseInt(model.getValueAt(i, 0).toString());
                    String category = model.getValueAt(i, 1).toString();
                    String name = model.getValueAt(i, 2).toString();
                    String address = model.getValueAt(i, 3).toString();
                    String city = model.getValueAt(i, 4).toString();
                    String cityCode = model.getValueAt(i, 5).toString();
                    int noRooms = Integer.parseInt(model.getValueAt(i, 6).toString());
                    int noBeds = Integer.parseInt(model.getValueAt(i, 7).toString());
                    String attribute = ""; // optional, falls du später State aus Tabelle brauchst
                    String lastTransactionalData = model.getValueAt(i, 9) != null ? model.getValueAt(i, 9).toString() : "";

                    Hotel hotel = new Hotel(id, category, name, address, city, cityCode, noRooms, noBeds, attribute, lastTransactionalData);
                    hotelListe.add(hotel);
                }

                String filePath = "src/main/java/org/example/data/txt/hotels.txt";
                HotelFileWriter.writeHotelsToFile(hotelListe, filePath);

                JOptionPane.showMessageDialog(
                        startseite.this,
                        "Changes successfully saved!",
                        "Save",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(
                        startseite.this,
                        "Error while saving!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }

    }


    private class DeleteHotelAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = table1.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(
                        startseite.this,
                        "Please select a hotel in the table first!",
                        "No selection",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            String hotelId = table1.getValueAt(selectedRow, 0).toString();
            Object[] options = {"Delete", "Stop"};
            int choice = JOptionPane.showOptionDialog(
                    startseite.this,
                    "Transactional data of this Hotel will also be deleted!",
                    "Are you sure?",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null,
                    options,
                    options[1]  // Default-Fokus auf "Stop"
            );

            if (choice == JOptionPane.YES_OPTION) {
                DefaultTableModel model = (DefaultTableModel) table1.getModel();
                model.removeRow(selectedRow);
                JOptionPane.showMessageDialog(
                        startseite.this,
                        "Hotel with ID " + hotelId + " has been deleted.",
                        "Deleted",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
            // Bei Stop (NO_OPTION) passiert nichts
        }
    }

}