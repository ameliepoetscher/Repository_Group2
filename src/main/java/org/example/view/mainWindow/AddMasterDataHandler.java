package org.example.view.mainWindow;

import org.example.entity.Hotel;
import org.example.view.hotel.EditHotelDialog;
import org.example.data.txt.HotelFileWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;
import java.awt.Component;


/**
 * Fügt ein neues Hotel über den EditHotelDialog hinzu
 * und speichert ausschließlich in hotels.txt (keine DB/DAO).
 */
public final class AddMasterDataHandler {

    private AddMasterDataHandler() {}

    public static void addHotel(JFrame parent, JTable table) {
        DefaultTableModel m = (DefaultTableModel) table.getModel();

        // Neue eindeutige ID bestimmen (max ID + 1)
        int newId = 1;
        for (int i = 0; i < m.getRowCount(); i++) {
            Object v = m.getValueAt(i, 0);
            if (v != null) {
                try { newId = Math.max(newId, Integer.parseInt(v.toString()) + 1); }
                catch (NumberFormatException ignored) {}
            }
        }

        // Neues Hotel mit Defaultwerten
        Hotel h = new Hotel(newId, "*", "", "", "", "", 0, 0, "", "");

        // Dialog öffnen (Add)
        EditHotelDialog dlg = new EditHotelDialog(parent, h, true);
        dlg.setVisible(true);
        if (!dlg.isSaved()) return;

        // In Tabelle neue Zeile einfügen (Spaltenreihenfolge beachten!)
        m.addRow(new Object[]{
                h.getId(),            // 0 ID
                h.getCategory(),      // 1 Category
                h.getName(),          // 2 Name
                h.getAddress(),       // 3 Adresse
                h.getCity(),          // 4 City
                h.getCityCode(),      // 5 PLZ
                h.getNoRooms(),       // 6 Rooms
                h.getNoBeds(),        // 7 Beds
                "",                   // 8 Attribute
                ""                    // 9 Last Transactional Data
        });

        // Gesamte Tabelle -> Liste<Hotel> -> hotels.txt schreiben
        persistAllRowsToFile(m, parent);
        JOptionPane.showMessageDialog(parent, "Neues Hotel gespeichert.");
    }

    static void persistAllRowsToFile(DefaultTableModel m, Component parentForError) {
        try {
            List<Hotel> hotels = new ArrayList<>();
            for (int i = 0; i < m.getRowCount(); i++) {
                hotels.add(new Hotel(
                        parseInt(m.getValueAt(i, 0)),
                        str(m.getValueAt(i, 1)),
                        str(m.getValueAt(i, 2)),
                        str(m.getValueAt(i, 3)),
                        str(m.getValueAt(i, 4)),
                        str(m.getValueAt(i, 5)),
                        parseInt(m.getValueAt(i, 6)),
                        parseInt(m.getValueAt(i, 7)),
                        str(m.getValueAt(i, 8)),
                        str(m.getValueAt(i, 9))
                ));
            }
            String filePath = "src/main/java/org/example/data/txt/hotels.txt";
            HotelFileWriter.writeHotelsToFile(hotels, filePath);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(parentForError,
                    "Fehler beim Speichern in hotels.txt:\n" + ex.getMessage(),
                    "Save Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static int parseInt(Object v) {
        if (v == null) return 0;
        try { return Integer.parseInt(v.toString().trim()); }
        catch (NumberFormatException e) { return 0; }
    }

    private static String str(Object v) { return v == null ? "" : v.toString(); }
}
