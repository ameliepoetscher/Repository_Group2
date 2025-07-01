package org.example.data.txt;

import org.example.entity.Occupancy;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class OccupancyFileWriter {

    public static void writeOccupanciesToFile(List<Occupancy> occupancies, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Header schreiben
            writer.write("id,rooms,usedRooms,beds,usedBeds,year,month");
            writer.newLine();

            for (Occupancy occ : occupancies) {
                String line = occ.getHotel().getId() + "," +
                        occ.getRooms() + "," +
                        occ.getUsedRooms() + "," +
                        occ.getBeds() + "," +
                        occ.getUsedBeds() + "," +
                        occ.getYear() + "," +
                        occ.getMonth();
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing occupancies: " + e.getMessage());
        }
    }
    public static void appendOccupancyToFile(Occupancy occ, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) { // true = append mode
            String line = occ.getHotel().getId() + "," +
                    occ.getRooms() + "," +
                    occ.getUsedRooms() + "," +
                    occ.getBeds() + "," +
                    occ.getUsedBeds() + "," +
                    occ.getYear() + "," +
                    occ.getMonth();

            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error appending occupancy: " + e.getMessage());
        }
    }

}
