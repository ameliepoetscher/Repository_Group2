/*package org.example.main;

import org.example.dao.HotelDAO;
import org.example.entity.Hotel;

import java.util.ArrayList;
import java.util.List;

public class MainAddAmentiesToHotel {

    public static void main(String[] args) {

        // before -> previous state, existing hotel but no added amenities

        Hotel tempHotel = HotelDAO.getHotelById(2);
        System.out.printf("Anzahl der Amenities des Hotels %s: %d%n", tempHotel.getName(), tempHotel.getAmenities().size());
        tempHotel.getAmenities().forEach(amenity -> System.out.printf("Amenity: %s%n", amenity.getName()));


        // Szemario 2: Load an existing hotel and add (existing) amenities
        ArrayList<Integer> amenities = new ArrayList<>(List.of(1));
        HotelDAO.addAmenitiesToHotel(2, amenities);


        // afterwards   check if the amenities were added, there should be 3 amenities at hotel 2

        tempHotel = HotelDAO.getHotelById(2);
        System.out.printf("Anzahl der Amenities des Hotels %s: %d%n", tempHotel.getName(), tempHotel.getAmenities().size());
        tempHotel.getAmenities().forEach(amenity -> System.out.printf("Amenity: %s%n", amenity.getName()));

    }
}
*/