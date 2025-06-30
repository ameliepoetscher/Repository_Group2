package org.example.tryout;

import org.example.dao.AmenityDAO;
import org.example.entity.Amenity;
import org.example.entity.Hotel;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Set;

public class AmenitySelectionApp extends JFrame {


    private final JPanel checkboxPanel;

    public AmenitySelectionApp() {
        setTitle("Amenity Selection");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout());

        checkboxPanel = new JPanel();
        checkboxPanel.setLayout(new GridLayout(0, 1)); // Vertikale Anordnung der Checkboxen
        add(new JScrollPane(checkboxPanel), BorderLayout.CENTER);

        JButton loadButton = new JButton("Hotel laden");
        add(loadButton, BorderLayout.SOUTH);

        loadAllAmenities();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AmenitySelectionApp app = new AmenitySelectionApp();
            app.setVisible(true);
        });
    }

    private void loadAllAmenities() {
        List<Amenity> allAmenities = AmenityDAO.getAllAmenities();
        checkboxPanel.removeAll();

        for (Amenity amenity : allAmenities) {
            JCheckBox checkBox = new JCheckBox(amenity.getName());
            checkBox.setActionCommand(String.valueOf(amenity.getId()));
            checkboxPanel.add(checkBox);
        }

        checkboxPanel.updateUI();
    }





}