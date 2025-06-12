package org.example.main;

import org.example.dao.AmenityDAO;
import org.example.entity.Amenity;

import java.util.ArrayList;

public class MainAmenetiesCRUDs {

    public static void main(String[] args) {


        Amenity amenity = new Amenity("Spa");
        AmenityDAO.createAmenity(amenity);

        ArrayList<Amenity> amenities = (ArrayList<Amenity>) AmenityDAO.getAllAmenities();
        for (Amenity a : amenities) {
            System.out.println(a.getName());
        }


    }
}
