package org.example.view.mainWindow;

import org.example.dao.HotelDAO;
import org.example.entity.Hotel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class DeleteMasterDataHandler {

    public static void deleteHotel(JFrame parent, JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(parent, "Bitte wähle ein Hotel zum Löschen aus.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(parent,
                "Möchtest du dieses Hotel wirklich löschen?",
                "Löschen bestätigen", JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) return;

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int hotelId = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());

        Hotel hotel = HotelDAO.getHotelById(hotelId);
        if (hotel != null) {
            HotelDAO.deleteHotel(hotel);  // Aus DB löschen
            JOptionPane.showMessageDialog(parent, "Hotel erfolgreich gelöscht.");
            refreshHotelTable(table);     // Tabelle neu laden
        } else {
            JOptionPane.showMessageDialog(parent, "Hotel konnte nicht gefunden werden.");
        }
    }

    /**
     * Holt alle Hotels aus der DB und füllt die JTable neu.
     */
    private static void refreshHotelTable(JTable table) {
        List<Hotel> hotels = HotelDAO.getAllHotels();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Alte Zeilen löschen

        for (Hotel h : hotels) {
            model.addRow(new Object[]{
                    h.getId(),
                    h.getCategory(),
                    h.getName(),
                    h.getAddress(),
                    h.getCity(),
                    h.getCityCode(),
                    h.getNoRooms(),
                    h.getNoBeds(),
                    h.getAttribute(),
                    h.getLastTransactionalData()
            });
        }
    }
}
