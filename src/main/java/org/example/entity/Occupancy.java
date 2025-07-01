package org.example.entity;

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
    private int rooms;
    private int beds;
    private String nationality;


    @Column(name = "used_rooms")
    private int usedRooms;

    @Column(name = "used_beds")
    private int usedBeds;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    /**
     * Gibt die Nationalität dieses Eintrags zurück.
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * Legt die Nationalität dieses Eintrags fest.
     */
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

}
