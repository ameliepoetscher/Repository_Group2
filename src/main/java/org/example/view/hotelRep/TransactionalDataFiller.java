package org.example.view.hotelRep;

import javax.swing.table.DefaultTableModel;
import java.util.Map;

public class TransactionalDataFiller {

    public static void fill(Map<Integer, String> targetMap, DefaultTableModel model, int columnIndex) {
        targetMap.clear();
        for (int i = 0; i < model.getRowCount(); i++) {
            Object value = model.getValueAt(i, columnIndex);
            targetMap.put(i, value != null ? value.toString() : "");
        }
    }
}
