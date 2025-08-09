package org.example.data.txt;

import org.example.entity.Hotel;
import org.example.entity.Occupancy;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class OccupancyFileReader {

    /**
     * Liest Occupancy-Daten aus einer Datei im resources-Ordner (z. B. "data.txt/occupancies.txt").
     * @param resourcePath Pfad relativ zum classpath, z. B. "data.txt/occupancies.txt"
     * @param hotels Liste der bereits eingelesenen Hotels (zum Zuordnen via hotelId)
     */
    public static List<Occupancy> readOccupanciesFromFile(String resourcePath, List<Hotel> hotels) {
        List<Occupancy> occupancyList = new ArrayList<>();

        try (InputStream is = OccupancyFileReader.class.getClassLoader().getResourceAsStream(resourcePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            if (is == null) {
                throw new IllegalStateException("Resource not found on classpath: " + resourcePath);
            }

            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                // Kopfzeile optional überspringen
                if (isFirstLine && line.toLowerCase().contains("id")) {
                    isFirstLine = false;
                    continue;
                }
                isFirstLine = false;

                // Erwartetes Format (Beispiel aus deinem Screenshot):
                // hotelId, rooms, usedRooms, beds, usedBeds, year, month
                String[] parts = line.split(",", -1);
                if (parts.length < 7) {
                    System.out.println("⚠ Unvollständige Zeile übersprungen: " + line);
                    continue;
                }

                try {
                    int hotelId   = Integer.parseInt(parts[0].trim());
                    int rooms     = Integer.parseInt(parts[1].trim());
                    int usedRooms = Integer.parseInt(parts[2].trim());
                    int beds      = Integer.parseInt(parts[3].trim());
                    int usedBeds  = Integer.parseInt(parts[4].trim());
                    int year      = Integer.parseInt(parts[5].trim());
                    int month     = Integer.parseInt(parts[6].trim());

                    Hotel matchingHotel = hotels.stream()
                            .filter(h -> h.getId() != null && h.getId() == hotelId)
                            .findFirst()
                            .orElse(null);

                    if (matchingHotel != null) {
                        Occupancy occupancy = new Occupancy();
                        occupancy.setHotel(matchingHotel);
                        occupancy.setRooms(rooms);
                        occupancy.setUsedRooms(usedRooms);
                        occupancy.setBeds(beds);
                        occupancy.setUsedBeds(usedBeds);
                        occupancy.setYear(year);
                        occupancy.setMonth(month);
                        occupancyList.add(occupancy);
                    } else {
                        System.out.println("⚠ Kein Hotel mit ID " + hotelId + " gefunden. Zeile übersprungen: " + line);
                    }

                } catch (Exception e) {
                    System.out.println("⚠ Fehler beim Parsen der Zeile: " + line);
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            System.out.println("❌ Fehler beim Lesen der Resource: " + resourcePath);
            e.printStackTrace();
        }

        return occupancyList;
    }
}
