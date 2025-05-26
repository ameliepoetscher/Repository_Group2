package org.example;

/**
 * DTO für einen Belegungs-Datensatz (Transactional Data).
 */
public class Occupancy {
    private final int hotelId;      // ID des Hotels
    private final int year;         // Jahr (z.B. 2025)
    private final int month;        // Monat (1–12)
    private final int occRooms;     // belegte Zimmer
    private final int occBeds;      // belegte Betten
    private final String hotelName; // Name des Hotels
    private final int category;     // Kategorie (1–5 Sterne)

    /**
     * @param hotelId   eindeutige Hotel-ID
     * @param year      Jahr der Belegung
     * @param month     Monat der Belegung (1=Jan … 12=Dez)
     * @param occRooms  Anzahl belegter Zimmer
     * @param occBeds   Anzahl belegter Betten
     * @param hotelName Name des Hotels
     * @param category  Hotel-Kategorie (1–5)
     */
    public Occupancy(int hotelId,
                     int year,
                     int month,
                     int occRooms,
                     int occBeds,
                     String hotelName,
                     int category) {
        this.hotelId   = hotelId;
        this.year      = year;
        this.month     = month;
        this.occRooms  = occRooms;
        this.occBeds   = occBeds;
        this.hotelName = hotelName;
        this.category  = category;
    }

    // Getter-Methoden

    /** @return Hotel-ID */
    public int getHotelId() {
        return hotelId;
    }

    /** @return Jahr (z.B. 2025) */
    public int getYear() {
        return year;
    }

    /** @return Monat (1–12) */
    public int getMonth() {
        return month;
    }

    /** @return Anzahl belegter Zimmer */
    public int getOccRooms() {
        return occRooms;
    }

    /** @return Anzahl belegter Betten */
    public int getOccBeds() {
        return occBeds;
    }

    /** @return Name des Hotels */
    public String getHotelName() {
        return hotelName;
    }

    /** @return Kategorie (1=★ … 5=★★★★★) */
    public int getCategory() {
        return category;
    }
}
