package org.example.data.txt;

import org.example.entity.Hotel;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class HotelFileWriter {

    public static void writeHotelsToFile(List<Hotel> hotels, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("id,category,name,owner,contact,address,city,cityCode,phone,noRooms,noBeds,attribute");
            writer.newLine();

            for (Hotel hotel : hotels) {
                String line = hotel.getId() + "," +
                        "\"" + hotel.getCategory() + "\"," +
                        "\"" + hotel.getName() + "\"," +
                        "\"-\",\"-\",\"" + hotel.getAddress() + "\"," +
                        "\"" + hotel.getCity() + "\"," +
                        "\"" + hotel.getCityCode() + "\",\"-\"," +
                        hotel.getNoRooms() + "," +
                        hotel.getNoBeds() + "," +
                        "\"" + hotel.getAttribute() + "\"";

                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Fehler beim Schreiben: " + e.getMessage());
        }
    }
}
