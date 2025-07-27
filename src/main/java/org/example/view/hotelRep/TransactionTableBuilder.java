package org.example.view.hotelRep;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TransactionTableBuilder {

    public static JTable createTransactionTable() {
        JTable table = new JTable();

        Object[][] data = {
                {"T1", "Hotel Alpha", "Check-In", "2024-07-01"},
                {"T2", "Hotel Beta", "Check-Out", "2024-07-02"},
                {"T3", "Hotel Gamma", "Booking", "2024-07-03"}
        };

        String[] columnNames = {"ID", "Hotel", "Type", "Date"};

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            final boolean[] columnEditable = new boolean[] {
                    false, false, true, true
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
