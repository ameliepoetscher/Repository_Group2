package org.example.view.mainWindow;

import org.example.dao.HotelDAO;
import org.example.data.txt.HotelFileReader;
import org.example.data.txt.OccupancyFileReader;
import org.example.entity.Hotel;
import org.example.entity.Occupancy;

import javax.swing.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 1. Datenbank ggf. initialisieren
        initializeDatabaseIfNeeded();

        // 2. Starte GUI
        SwingUtilities.invokeLater(() -> {
            noe_to_logo frame = new noe_to_logo();
            frame.setTitle("Lower Austria Tourist Portal");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }

    private static void initializeDatabaseIfNeeded() {
        if (HotelDAO.getAllHotels().isEmpty()) {  // ODER: HotelDAO.countHotels() == 0, je nachdem was du hast
            System.out.println("No hotels in DB. Importing from txt files...");

            // Hotels aus TXT lesen und speichern
            List<Hotel> hotels = HotelFileReader.readHotelsFromFile("src/main/resources/data.txt/hotels.txt");
            for (Hotel hotel : hotels) {
                HotelDAO.createHotel(hotel); // ACHTUNG: Methode muss es in deinem HotelDAO geben
            }

            // Occupancies aus TXT lesen und speichern
            List<Occupancy> occupancies = OccupancyFileReader.readOccupanciesFromFile("src/main/resources/data.txt/occupancies.txt", hotels);
            for (Occupancy occ : occupancies) {
                HotelDAO.saveOccupancy(occ); // Methode wie bei der anderen Gruppe im DAO (siehe unten)
            }

            System.out.println("Import finished.");
        } else {
            System.out.println("Hotels already in DB. Skipping import.");
        }
    }
}
