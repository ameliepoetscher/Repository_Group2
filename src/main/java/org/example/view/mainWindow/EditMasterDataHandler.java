package org.example.view.mainWindow;

import org.example.entity.Hotel;
import org.example.view.hotel.EditHotelDialog;
import org.example.data.txt.HotelFileWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Öffnet EditHotelDialog, übernimmt Änderungen in die JTable
 * und persistiert ausschließlich in hotels.txt (keine DB/DAO).
 */
public class EditMasterDataHandler {

    public static void editHotel(JFrame parentFrame, JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(parentFrame, "Bitte wählen Sie ein Hotel in der Tabelle aus.");
            return;
        }

        DefaultTableModel m = (DefaultTableModel) table.getModel();

        // Spaltenreihenfolge laut startseite:
        // 0 ID, 1 Category, 2 Name, 3 Adresse, 4 City, 5 PLZ, 6 Rooms, 7 Beds, 8 Attribute, 9 Last Transactional Data
        Hotel h = new Hotel(
                parseInt(m.getValueAt(selectedRow, 0)),
                str(m.getValueAt(selectedRow, 1)),
                str(m.getValueAt(selectedRow, 2)),
                str(m.getValueAt(selectedRow, 3)),
                str(m.getValueAt(selectedRow, 4)),
                str(m.getValueAt(selectedRow, 5)),
                parseInt(m.getValueAt(selectedRow, 6)),
                parseInt(m.getValueAt(selectedRow, 7)),
                str(m.getValueAt(selectedRow, 8)),
                str(m.getValueAt(selectedRow, 9))
        );

        // Dialog öffnen (ohne DB)
        EditHotelDialog dialog = new EditHotelDialog(parentFrame, h);
        dialog.setVisible(true);

        if (!dialog.isSaved()) return;

        // Änderungen zurück in die Tabelle schreiben
        m.setValueAt(h.getName(),    selectedRow, 2);
        m.setValueAt(h.getAddress(), selectedRow, 3);
        m.setValueAt(h.getNoRooms(), selectedRow, 6);
        m.setValueAt(h.getNoBeds(),  selectedRow, 7);

        // Gesamte Tabelle -> Liste<Hotel> -> hotels.txt persistieren
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

        JOptionPane.showMessageDialog(parentFrame, "Änderungen in hotels.txt gespeichert.");
    }

    private static int parseInt(Object v) {
        if (v == null) return 0;
        try { return Integer.parseInt(v.toString().trim()); }
        catch (NumberFormatException e) { return 0; }
    }

    private static String str(Object v) {
        return v == null ? "" : v.toString();
    }
}
