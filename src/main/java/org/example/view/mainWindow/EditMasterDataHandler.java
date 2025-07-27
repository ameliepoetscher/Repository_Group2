package org.example.view.mainWindow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.example.view.hotel.HotelDialog;


public class EditMasterDataHandler {

    public static void editHotel(JFrame parentFrame, JTable table) {
        int selectedRow = table.getSelectedRow(); // Wichtig: muss vor der Verwendung bekannt sein!
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(parentFrame, "Bitte wählen Sie ein Hotel aus.");
            return;
        }

        // Zeilendaten abrufen
        Object[] rowData = new Object[table.getColumnCount()];
        for (int i = 0; i < table.getColumnCount(); i++) {
            rowData[i] = table.getValueAt(selectedRow, i);
        }

        // Dialog starten
        HotelDialog dialog = new HotelDialog(parentFrame, rowData, true, table);
        dialog.setVisible(true);

        // Wenn gespeichert wurde, aktualisieren
        if (dialog.isSaved()) {
            Object[] updatedData = dialog.getHotelData();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            for (int i = 0; i < updatedData.length; i++) {
                model.setValueAt(updatedData[i], selectedRow, i);
            }
        }
    }
}
