// Datei: AddMasterDataHandler.java
package org.example.view.mainWindow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.example.view.mainWindow.AddMasterDataHandler;
import org.example.view.hotel.HotelDialog;



public class AddMasterDataHandler {

    public static void addHotel(JFrame parentFrame, JTable table) {
        HotelDialog dialog = new HotelDialog(parentFrame, null, false, table);
        dialog.setVisible(true);

        if (dialog.isSaved()) {
            Object[] newHotel = dialog.getHotelData();
            ((DefaultTableModel) table.getModel()).addRow(newHotel);
        }
    }

    public static void editHotel(JFrame parentFrame, JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) return; // Nichts ausgewählt

        Object[] oldData = new Object[table.getColumnCount()];
        for (int i = 0; i < table.getColumnCount(); i++) {
            oldData[i] = table.getValueAt(selectedRow, i);
        }

        HotelDialog dialog = new HotelDialog(parentFrame, oldData, true, table);
        dialog.setVisible(true);

        if (dialog.isSaved()) {
            Object[] newData = dialog.getHotelData();
            for (int i = 0; i < newData.length; i++) {
                table.setValueAt(newData[i], selectedRow, i);
            }
        }
    }
    public static void deleteHotel(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            ((DefaultTableModel) table.getModel()).removeRow(selectedRow);
        }
    }

}
