package org.example.main;

import org.example.entity.Hotel;
import org.example.dao.HotelDAO;
import java.util.ArrayList;
import java.util.Arrays;

public class MainRemoveAmentiesFromHotel {

    public static void main(String[] args) {

        // before -> previous state, existing hotel with too much amenities
        Hotel tempHotel = HotelDAO.getHotelById(2);
        System.out.printf("Anzahl der Amenities des Hotels %s: %d%n", tempHotel.getName(), tempHotel.getAmenities().size());
        tempHotel.getAmenities().forEach(amenity -> System.out.printf("Amenity: %s%n", amenity.getName()));

        ArrayList<Integer> amenitiesToDelete = new ArrayList<>(Arrays.asList(1, 1));

        // remove of existing amenities
        HotelDAO.removeAmenitiesFromHotel(2, amenitiesToDelete);

        // afterwards   check if the amenities were removed, there should be 1 amenity at hotel 2
        tempHotel = HotelDAO.getHotelById(2);
        System.out.printf("Anzahl der Amenities des Hotels %s: %d%n", tempHotel.getName(), tempHotel.getAmenities().size());
        tempHotel.getAmenities().forEach(amenity -> System.out.printf("Amenity: %s%n", amenity.getName()));

    }
}
