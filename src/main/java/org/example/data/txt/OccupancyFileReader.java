package org.example.data.txt;

import org.example.entity.Hotel;
import org.example.entity.Occupancy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class OccupancyFileReader {

    public static List<Occupancy> readOccupanciesFromFile(String filePath, List<Hotel> hotels) {
        List<Occupancy> occupancyList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                // Kopfzeile überspringen
                if (isFirstLine && line.toLowerCase().contains("id")) {
                    isFirstLine = false;
                    continue;
                }

                String[] parts = line.split(",", -1);
                if (parts.length < 7) {
                    System.out.println("⚠ Unvollständige Zeile übersprungen: " + line);
                    continue;
                }

                try {
                    int hotelId = Integer.parseInt(parts[0].trim());
                    int rooms = Integer.parseInt(parts[1].trim());
                    int usedRooms = Integer.parseInt(parts[2].trim());
                    int beds = Integer.parseInt(parts[3].trim());
                    int usedBeds = Integer.parseInt(parts[4].trim());
                    int year = Integer.parseInt(parts[5].trim());
                    int month = Integer.parseInt(parts[6].trim());

                    Hotel matchingHotel = hotels.stream()
                            .filter(h -> h.getId() == hotelId)
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
                    }

                } catch (Exception e) {
                    System.out.println("⚠ Fehler beim Parsen der Zeile: " + line);
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            System.out.println("❌ Fehler beim Lesen der Datei: " + filePath);
            e.printStackTrace();
        }

        return occupancyList;
    }
}
