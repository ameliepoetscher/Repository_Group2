package org.example.entity;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "occupancies")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Occupancy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int month;
    private int year;

    // Kapazitäten (optional)
    private int rooms;
    private int beds;

    // Belegte Zimmer/Bett-Zahlen (werden im GUI gebraucht)
    @Column(name = "used_rooms")
    private int usedRooms;

    @Column(name = "used_beds")
    private int usedBeds;

    private String nationality;

    // Meldedatum für die Belegung
    @Column(name = "reported_date")
    private LocalDate reportedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    // ----------- Getter/Setter für die GUI/DAO -----------

    // Belegte Zimmer (usedRooms)
    public int getRoomOccupancy() {
        return usedRooms;
    }
    public void setRoomOccupancy(int usedRooms) {
        this.usedRooms = usedRooms;
    }

    // Belegte Betten (usedBeds)
    public int getBedOccupancy() {
        return usedBeds;
    }
    public void setBedOccupancy(int usedBeds) {
        this.usedBeds = usedBeds;
    }

    public int getMonth() {
        return month;
    }
    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }

    public String getNationality() {
        return nationality;
    }
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Hotel getHotel() {
        return hotel;
    }
    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    // reportedDate für Datum der Meldung
    public LocalDate getReportedDate() {
        return reportedDate;
    }
    public void setReportedDate(LocalDate reportedDate) {
        this.reportedDate = reportedDate;
    }
}