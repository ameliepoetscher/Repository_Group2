package org.example.view.mainWindow;

import org.example.dao.HotelDAO;
import org.example.entity.Hotel;
import org.example.entity.Occupancy;

import javax.swing.*;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // 1) DB ggf. initial befüllen
        initializeDatabaseIfNeeded();

        // 2) GUI starten
        SwingUtilities.invokeLater(() -> {
            noe_to_logo frame = new noe_to_logo();
            frame.setTitle("Lower Austria Tourist Portal");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }

    /**
     * Wenn keine Hotels vorhanden sind, importiere sie aus den Ressourcen:
     *  - src/main/resources/data.txt/hotels.txt
     *  - src/main/resources/data.txt/occupancies.txt
     */
    private static void initializeDatabaseIfNeeded() {
        int before = HotelDAO.getAllHotels().size();
        System.out.println("Hotels in DB (vor Import): " + before);

        if (before > 0) {
            System.out.println("Hotels bereits in der DB. Import übersprungen.");
            return;
        }

        try {
            System.out.println("Keine Hotels in der DB. Importiere aus TXT-Dateien...");

            // ⚠️ Pfade sind CLASSPATH-relativ – Dateien müssen in resources/data.txt liegen!
            List<Hotel> hotels = HotelFileReader.readHotelsFromFile("data.txt/hotels.txt");
            System.out.println("Hotels aus Datei gelesen: " + hotels.size());

            // In die DB schreiben
            for (Hotel h : hotels) {
                HotelDAO.createHotel(h);
            }

            // Occupancies importieren (optional – nur wenn vorhanden)
            List<Occupancy> occupancies =
                    OccupancyFileReader.readOccupanciesFromFile("data.txt/occupancies.txt", hotels);
            System.out.println("Occupancies aus Datei gelesen: " + occupancies.size());

            for (Occupancy occ : occupancies) {
                HotelDAO.saveOccupancy(occ);
            }

            int after = HotelDAO.getAllHotels().size();
            System.out.println("Hotels in DB (nach Import): " + after);
            System.out.println("Import abgeschlossen.");

        } catch (Exception e) {
            System.err.println("Fehler beim Initialimport: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
