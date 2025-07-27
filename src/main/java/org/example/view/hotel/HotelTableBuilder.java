package org.example.view.hotel;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class HotelTableBuilder {

    public static JTable createHotelTable() {
        JTable table = new JTable();

        Object[][] data = {
                {"1", "Hotel Alpha", "Vienna", "20", "35"},
                {"2", "Hotel Beta", "Graz", "30", "45"},
                {"3", "Hotel Gamma", "Linz", "40", "55"},
                {"4", "Hotel Delta", "Salzburg", "50", "65"},
                {"5", "Hotel Epsilon", "Klagenfurt", "60", "75"}
        };

        String[] columnNames = {"ID", "name", "adresse", "rooms", "beds"};

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            final boolean[] columnEditable = new boolean[] {
                    false, true, true, true, true
            };

            @Override
            public boolean isCellEditable(int row, int column) {
                return columnEditable[column];
            }
        };

        table.setModel(model);
        return table;
    }
}
