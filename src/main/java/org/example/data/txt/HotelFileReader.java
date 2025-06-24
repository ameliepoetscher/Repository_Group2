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
                    continue;  // Header überspringen
                }

                // CSV-Parsing
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
                    int id = Integer.parseInt(parts[0].replaceAll("\"", ""));
                    String category = parts[1].replaceAll("\"", "");
                    String name = parts[2].replaceAll("\"", "");
                    String owner = parts[3].replaceAll("\"", "");
                    String contact = parts[4].replaceAll("\"", "");
                    String address = parts[5].replaceAll("\"", "");
                    String city = parts[6].replaceAll("\"", "");
                    String cityCode = parts[7].replaceAll("\"", "");
                    String phone = parts[8].replaceAll("\"", "");
                    int noRooms = Integer.parseInt(parts[9].replaceAll("\"", ""));
                    int noBeds = Integer.parseInt(parts[10].replaceAll("\"", ""));

                    // Attribut (optional) - falls du später Attribute in Text speicherst
                    String attribute = parts.length > 11 ? parts[11].replaceAll("\"", "") : "";

                    Hotel hotel = new Hotel(id, category, name, address, city, cityCode, noRooms, noBeds, attribute);
                    hotel.setOwner(owner);
                    hotel.setContact(contact);
                    hotel.setPhone(phone);

                    hotels.add(hotel);

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
