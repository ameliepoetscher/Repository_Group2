// Datei: DeleteMasterDataHandler.java
package org.example.view.mainWindow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class DeleteMasterDataHandler {

    public static void deleteHotel(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            ((DefaultTableModel) table.getModel()).removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(null, "Please select one hotel!");
        }
    }
}
