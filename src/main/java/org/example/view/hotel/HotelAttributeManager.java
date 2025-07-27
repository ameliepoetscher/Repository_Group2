package org.example.view.hotel;

import org.example.dao.AmenityDAO;
import org.example.entity.Amenity;

import java.util.ArrayList;
import java.util.List;

/** In startseite
 * Verwaltet Hotelattribute aus der Datenbank.
 */
public class HotelAttributeManager {

    // Standardattribute
    private static final List<String> DEFAULT_ATTRIBUTES = List.of(
            "family friendly", "dog friendly", "spa", "fitness"
    );

    /**
     * Initialisiert die Standardattribute in der Datenbank (falls nicht vorhanden).
     */
    public static void initDefaults() {
        for (String attr : DEFAULT_ATTRIBUTES) {
            if (AmenityDAO.findByName(attr) == null) {
                AmenityDAO.createAmenity(new Amenity(attr));
            }
        }
    }

    /**
     * Gibt alle Attributnamen aus der Datenbank zurück.
     */
    public static List<String> getAllAttributeNames() {
        List<String> result = new ArrayList<>();
        for (Amenity a : AmenityDAO.getAllAmenities()) {
            result.add(a.getName());
        }
        return result;
    }

    /**
     * Gibt Amenity-Objekte für gegebene Namen zurück (neue werden automatisch erstellt).
     */
    public static List<Amenity> getOrCreateAmenities(List<String> names) {
        List<Amenity> result = new ArrayList<>();
        for (String name : names) {
            Amenity a = AmenityDAO.findByName(name);
            if (a == null) {
                a = new Amenity(name);
                AmenityDAO.createAmenity(a);
            }
            result.add(a);
        }
        return result;
    }
}
