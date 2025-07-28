// Datei: HotelDataSaver.java
package org.example.view.hotel;

import javax.swing.JTable;
import java.util.List;
import java.util.ArrayList;

public class HotelDataSaver {

    public static List<String[]> extractDataFromTable(JTable table) {
        List<String[]> hotelDataList = new ArrayList<>();

        for (int i = 0; i < table.getRowCount(); i++) {
            String[] row = new String[table.getColumnCount()];
            for (int j = 0; j < table.getColumnCount(); j++) {
                Object value = table.getValueAt(i, j);
                row[j] = value != null ? value.toString() : "";
            }
            hotelDataList.add(row);
        }

        return hotelDataList;
    }
}
