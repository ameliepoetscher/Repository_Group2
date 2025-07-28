package org.example.view.hotel;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JPanel;

public class HotelTableLoader {

    public static void loadModelIntoTable(JTable table, DefaultTableModel model) {
        table.setModel(model);
    }

    public static void loadModelIntoPanel(JPanel panel, JTable table, DefaultTableModel model) {
        table.setModel(model);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane);
    }

    public static void loadHotelsIntoTable(JTable table, DefaultTableModel model) {
        table.setModel(model);
    }
}
