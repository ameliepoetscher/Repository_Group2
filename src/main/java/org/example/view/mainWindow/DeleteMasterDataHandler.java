package org.example.view.mainWindow;

import org.example.dao.HotelDAO;
import org.example.entity.Hotel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

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
            model.removeRow(selectedRow); // Aus UI entfernen
            JOptionPane.showMessageDialog(parent, "Hotel erfolgreich gelöscht.");
        } else {
            JOptionPane.showMessageDialog(parent, "Hotel konnte nicht gefunden werden.");
        }
    }
}