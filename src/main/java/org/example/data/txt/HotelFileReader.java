package org.example.data.txt;

import org.example.entity.Hotel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HotelFileReader {

    public static List<Hotel> readHotelsFromFile(String filePath) {
        List<Hotel> hotels = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine && line.toLowerCase().contains("id")) {
                    isFirstLine = false;
                    continue;
                }

                line = line.replace("\"", "").trim();
                String[] parts = line.split(",", -1);

                if (parts.length < 11) {
                    System.out.println("Warning: Skipping incomplete line: " + line);
                    continue;
                }

                for (int i = 0; i < parts.length; i++) {
                    if (parts[i] == null || parts[i].trim().isEmpty()) {
                        parts[i] = (i >= 9) ? "0" : "unknown";
                    }
                }

                try {
                    Hotel hotel = new Hotel();
                    hotel.setId(Integer.parseInt(parts[0]));
                    hotel.setCategory(parts[1]);
                    hotel.setName(parts[2]);
                    hotel.setOwner(parts[3]);
                    hotel.setContact(parts[4]);
                    hotel.setAddress(parts[5]);
                    hotel.setCity(parts[6]);
                    hotel.setCityCode(parts[7]);
                    hotel.setPhone(parts[8]);
                    hotel.setNoRooms(Integer.parseInt(parts[9]));
                    hotel.setNoBeds(Integer.parseInt(parts[10]));
                    hotels.add(hotel);
                } catch (Exception e) {
                    System.out.println("Error parsing line: " + line);
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
            e.printStackTrace();
        }

        // 🔽 Hier wird die Liste nach ID sortiert
        hotels.sort(Comparator.comparingInt(Hotel::getId));

        return hotels;
    }
}
