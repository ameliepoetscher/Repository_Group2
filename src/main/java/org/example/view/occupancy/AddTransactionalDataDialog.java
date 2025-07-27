package org.example.view.occupancy;

import org.example.dao.HotelDAO;
import org.example.entity.Hotel;
import org.example.entity.Occupancy;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

import lombok.Getter;

/**
 * In hotel_rep
 * Diese Klasse zeigt ein Dialogfenster an, in dem man Belegungsdaten für ein Hotel eingeben kann –
 * zum Beispiel wie viele Zimmer und Betten belegt sind, für einen bestimmten Monat und ein bestimmtes Jahr.
 *
 * Die Klasse basiert auf {@link JDialog} und übernimmt dessen Funktionen.
 */
@Getter
public class AddTransactionalDataDialog extends JDialog { //Standard Dialogfenster geerbt von JDialog
    private boolean saved = false;
    // generiert eine Methode isSaved() um zu überprüfen ob Benutzer auf OK oder Abbrechen geklickt hat

    private final JComboBox<String> yearCombo; //Drop-Down für Jahr
    private final JComboBox<String> monthCombo; //Drop-Down für Monat
    private final JTextField roomField; //Nutzer kann Zahl für Zimmer eingeben
    private final JTextField bedField; //Nutzer kann Zahl für Betten angeben

    private final int hotelId; //Hotel-ID für DB-Zugriff

    public AddTransactionalDataDialog(JFrame parent, int hotelId, String hotelName) {
        super(parent, "Add Transactional Data", true);
        this.hotelId = hotelId;
        this.yearCombo = new JComboBox<>();
        this.monthCombo = new JComboBox<>();
        this.roomField = new JTextField(10);
        this.bedField = new JTextField(10);
        //UI-Komponenten initialisieren

        initializeComponents(hotelId, hotelName);
        //Methode aufrufen, um Layout und Inhalte des Dialogs zu erstellen
    }

    private void initializeComponents(int hotelId, String hotelName) { //baut Benutzeroberfläche des Dialogs auf
        setLayout(new GridBagLayout()); //Gitter Layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        addFormRow(gbc, "Hotel:", new JLabel(hotelName), 0);
        addFormRow(gbc, "Hotel ID:", new JLabel(String.valueOf(hotelId)), 1); //nicht editierbar

        setupYearCombo(gbc); //Dropdown für Jahr
        setupMonthCombo(gbc); //Dropdown für Monat
        addFormRow(gbc, "Room Occupancy:", roomField, 4); //Zimmerauslastung
        addFormRow(gbc, "Bed Occupancy:", bedField, 5); //Bettenauslastung
        addButtons(gbc); //Buttons hinzufügen

        pack(); //passt Größe an Inhalt an
        setLocationRelativeTo(getParent()); //zentriert Dialog
    }

    private void addFormRow(GridBagConstraints gbc, String label, Component component, int row) { //Label + Eingabefeld platzieren
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel(label), gbc);
        gbc.gridx = 1;
        add(component, gbc);
    }

    private void setupYearCombo(GridBagConstraints gbc) { //Dropdown-Menü befüllen
        yearCombo.addItem("---select---");
        for (int y = 2025; y >= 2000; y--) {
            yearCombo.addItem(String.valueOf(y));
        }
        addFormRow(gbc, "Year:", yearCombo, 2);
    }

    private void setupMonthCombo(GridBagConstraints gbc) { //Dropdown-Menü befüllen
        monthCombo.addItem("---select---");
        String[] months = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
        for (String month : months) {
            monthCombo.addItem(month);
        }
        addFormRow(gbc, "Month:", monthCombo, 3);
    }

    private void addButtons(GridBagConstraints gbc) { //Schaltflächen erstellen
        JButton saveButton = new JButton("Ok");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener(e -> { //bestimmte Aktion wird ausgeführt
            if (validateInput()) { //Eingabe überprüfen
                try {
                    // Hotel aus DB laden
                    Hotel hotel = HotelDAO.getHotelById(hotelId);
                    if (hotel == null) {
                        JOptionPane.showMessageDialog(this, "Hotel not found in database.");
                        return;
                    }

                    // Neue Belegungsdaten erzeugen
                    Occupancy occupancy = new Occupancy();
                    occupancy.setMonth(getSelectedMonth());
                    occupancy.setYear(getSelectedYear());
                    occupancy.setRoomOccupancy(getRoomOccupancy());  // ✔️
                    occupancy.setBedOccupancy(getBedOccupancy());    // ✔️
                    occupancy.setHotel(hotel);                       // Falls du ein Hotel-Objekt hast
                    occupancy.setReportedDate(LocalDate.now());

                    HotelDAO.saveOccupancy(occupancy); // DB-Speicherung


                    saved = true;
                    JOptionPane.showMessageDialog(this, "Occupancy data saved successfully.");
                    setVisible(false); //Dialog schließen
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this,
                            "Error saving occupancy: " + ex.getMessage(),
                            "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(e -> setVisible(false)); //Dialogfenster wird sofort geschlossen ohne Speicherung der Daten

        gbc.gridx = 0;
        gbc.gridy = 6;
        add(saveButton, gbc);
        gbc.gridx = 1;
        add(cancelButton, gbc);
    }

    private boolean validateInput() { //Überprüfung der Eingabedaten
        if (yearCombo.getSelectedIndex() <= 0 || monthCombo.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(this, "Please select both year and month.");
            return false;
        }
        String roomText = roomField.getText().trim();
        String bedText = bedField.getText().trim();

        if (roomText.isEmpty() || bedText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in both occupancy fields.");
            return false;
        }
        try {
            Integer.parseInt(roomText);
            Integer.parseInt(bedText);
            return true;
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Occupancy must be numbers.");
            return false;
        }
    }

    // folgende Methoden geben die eingegebenen Werte des Benutzers zurück
    public int getSelectedYear() {
        return Integer.parseInt((String) yearCombo.getSelectedItem());
    }

    public int getSelectedMonth() {
        return monthCombo.getSelectedIndex(); // Januar = 1
    }

    public int getRoomOccupancy() {
        return Integer.parseInt(roomField.getText().trim());
    }

    public int getBedOccupancy() {
        return Integer.parseInt(bedField.getText().trim());
    }
}
