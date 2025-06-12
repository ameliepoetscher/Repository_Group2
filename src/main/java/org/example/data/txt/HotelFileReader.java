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

                // Verbesserte CSV-Parsing-Logik
                List<String> fields = new ArrayList<>();
                StringBuilder currentField = new StringBuilder();
                boolean inQuotes = false;

                for (char c : line.toCharArray()) {
                    if (c == '"') {
                        inQuotes = !inQuotes;
                    } else if (c == ',' && !inQuotes) {
                        fields.add(currentField.toString().trim());
                        currentField.setLength(0);
                    } else {
                        currentField.append(c);
                    }
                }
                fields.add(currentField.toString().trim());

                String[] parts = fields.toArray(new String[0]);

                if (parts.length < 11) {
                    System.out.println("Warning: Skipping incomplete line: " + line);
                    continue;
                }

                try {
                    Hotel hotel = new Hotel();
                    hotel.setId(Integer.parseInt(parts[0].replaceAll("\"", "")));
                    hotel.setCategory(parts[1].replaceAll("\"", ""));
                    hotel.setName(parts[2].replaceAll("\"", ""));
                    hotel.setOwner(parts[3].replaceAll("\"", ""));
                    hotel.setContact(parts[4].replaceAll("\"", ""));
                    hotel.setAddress(parts[5].replaceAll("\"", ""));
                    hotel.setCity(parts[6].replaceAll("\"", ""));
                    hotel.setCityCode(parts[7].replaceAll("\"", ""));
                    hotel.setPhone(parts[8].replaceAll("\"", ""));
                    hotel.setNoRooms(Integer.parseInt(parts[9].replaceAll("\"", "")));
                    hotel.setNoBeds(Integer.parseInt(parts[10].replaceAll("\"", "")));
                    hotels.add(hotel);

                    // Debug-Ausgabe
                    System.out.println("Erfolgreich eingelesen - Hotel ID: " + hotel.getId() +
                            ", Zimmer: " + hotel.getNoRooms() +
                            ", Betten: " + hotel.getNoBeds());
                } catch (Exception e) {
                    System.out.println("Error parsing line: " + line);
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
            e.printStackTrace();
        }

        hotels.sort(Comparator.comparingInt(Hotel::getId));
        return hotels;
    }
}
