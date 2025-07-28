package org.example.view.hotel;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class HotelTableModelBuilder {

    public static DefaultTableModel createHotelTableModel(List<Object[]> rowDataList, String[] columnNames) {
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        for (Object[] row : rowDataList) {
            model.addRow(row);
        }
        return model;
    }
}
