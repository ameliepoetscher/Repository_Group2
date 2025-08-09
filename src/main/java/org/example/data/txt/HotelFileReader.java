package org.example.data.txt;

import org.example.entity.Hotel;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class HotelFileReader {

    public static List<Hotel> readHotelsFromFile(String resourcePath) {
        List<Hotel> hotels = new ArrayList<>();

        // Resource vom Classpath laden (src/main/resources/…)
        InputStream is = HotelFileReader.class.getClassLoader().getResourceAsStream(resourcePath);
        if (is == null) {
            throw new RuntimeException("❌ Datei nicht gefunden im Ressourcenpfad: " + resourcePath);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                // optional: Headerzeile überspringen
                if (isFirstLine && line.toLowerCase().contains("id")) {
                    isFirstLine = false;
                    continue;
                }
                isFirstLine = false;

                // CSV mit Quotes korrekt trennen
                var fields = new ArrayList<String>();
                var cur = new StringBuilder();
                boolean inQuotes = false;
                for (char c : line.toCharArray()) {
                    if (c == '"') {
                        inQuotes = !inQuotes;
                    } else if (c == ',' && !inQuotes) {
                        fields.add(cur.toString().trim());
                        cur.setLength(0);
                    } else {
                        cur.append(c);
                    }
                }
                fields.add(cur.toString().trim());
                String[] parts = fields.toArray(new String[0]);

                // Erwartetes Minimum prüfen (deine Datei hat mind. 11 Spalten)
                if (parts.length < 11) {
                    System.out.println("⚠ Zeile unvollständig: " + line);
                    continue;
                }

                try {
                    // index 0 = id in Datei -> NICHT setzen!
                    String category = parts[1].replace("\"", "");
                    String name     = parts[2].replace("\"", "");

                    // owner/contact/phone stehen in deiner Datei, werden hier optional gesetzt:
                    String owner   = parts.length > 3  ? parts[3].replace("\"", "") : null;
                    String contact = parts.length > 4  ? parts[4].replace("\"", "") : null;

                    String address = parts[5].replace("\"", "");
                    String city    = parts[6].replace("\"", "");
                    String cityCode= parts[7].replace("\"", "");

                    String phone   = parts.length > 8  ? parts[8].replace("\"", "") : null;

                    int noRooms    = Integer.parseInt(parts[9].replace("\"", ""));
                    int noBeds     = Integer.parseInt(parts[10].replace("\"", ""));

                    String attribute = parts.length > 11 ? parts[11].replace("\"", "") : "";
                    String lastTransactionalData = (parts.length > 12 && !parts[12].isBlank())
                            ? parts[12].replace("\"", "")
                            : "2025-06-22";

                    // WICHTIG: Konstruktor OHNE ID verwenden (DB generiert die ID)
                    Hotel hotel = new Hotel(
                            category, name, address, city, cityCode,
                            noRooms, noBeds, attribute, lastTransactionalData
                    );
                    // optionale Felder setzen
                    hotel.setOwner(owner);
                    hotel.setContact(contact);
                    hotel.setPhone(phone);

                    hotels.add(hotel);
                } catch (Exception parseEx) {
                    System.out.println("⚠ Fehler beim Parsen: " + line);
                    parseEx.printStackTrace();
                }
            }
        } catch (Exception e) {
            System.out.println("❌ Fehler beim Lesen der Datei: " + resourcePath);
            e.printStackTrace();
        }

        // Kein Sortieren nach getId(), weil die vor dem Persist null ist
        return hotels;
    }
}
