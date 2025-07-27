package org.example.view.hotel;

import java.util.ArrayList;
import java.util.List;

/**
 * Verwaltet die Attribute (startseite), die Hotels zugeordnet werden können.
 */

public class HotelAttributeManager {
    // Statische Liste aller verfügbaren Hotel\-Attribute
    private static final List<String> attributeList = new ArrayList<>();
//attribute für senioruser
    static {
        attributeList.add("family friendly");
        attributeList.add("dog friendly");
        attributeList.add("spa");
        attributeList.add("fitness");
    }
    /**
     * Gibt eine Kopie der aktuellen Attribut\-Liste zurück.
     * So kann die Liste von außen gelesen werden, aber nicht direkt verändert werden.
     */
    public static List<String> getAttributes() {
        return new ArrayList<>(attributeList);
    }

    /**
     * Fügt ein neues Attribut zur Liste hinzu, falls es noch nicht existiert.
     * So können weitere Merkmale für Hotels ergänzt werden.
     */
    public static void addAttribute(String attr) {
        if (!attributeList.contains(attr)) {
            attributeList.add(attr);
        }
    }
}
