package org.example.entity;

import java.util.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "hotels")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String category;
    private String name;
    private String owner;
    private String contact;
    private String address;
    private String city;
    private String cityCode;
    private String phone;
    private Integer noRooms;
    private Integer noBeds;
    private String attribute;
    private String lastTransactionalData;

    // Konstruktor ohne ID (für neue Einträge)
    public Hotel(String category, String name, String address, String city,
                 String cityCode, Integer noRooms, Integer noBeds,
                 String attribute, String lastTransactionalData) {
        this.category = category;
        this.name = name;
        this.address = address;
        this.city = city;
        this.cityCode = cityCode;
        this.noRooms = noRooms;
        this.noBeds = noBeds;
        this.attribute = attribute;
        this.lastTransactionalData = lastTransactionalData;
    }

    // Konstruktor mit ID (für Importe o.ä.)
    public Hotel(Integer id, String category, String name, String address, String city,
                 String cityCode, Integer noRooms, Integer noBeds,
                 String attribute, String lastTransactionalData) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.address = address;
        this.city = city;
        this.cityCode = cityCode;
        this.noRooms = noRooms;
        this.noBeds = noBeds;
        this.attribute = attribute;
        this.lastTransactionalData = lastTransactionalData;
    }

    // Manuelle Getter/Setter für Spezialfälle (falls Lombok nicht reicht)
    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getAttribute() {
        return attribute;
    }

    public String getLastTransactionalData() {
        return lastTransactionalData;
    }

    public void setLastTransactionalData(String lastTransactionalData) {
        this.lastTransactionalData = lastTransactionalData;
    }

    // Beziehung zu Amenities (viele-zu-viele)
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "hotel_amenity",
            joinColumns = @JoinColumn(name = "hotel_id"),
            inverseJoinColumns = @JoinColumn(name = "amenity_id")
    )
    private Set<Amenity> amenities = new HashSet<>();

    public void addAmenity(Amenity amenity) {
        this.amenities.add(amenity);
    }

    public void removeAmenity(Amenity amenity) {
        this.amenities.remove(amenity);
    }

    // Beziehung zu Occupancies (eins-zu-viele)
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Occupancy> occupancies = new ArrayList<>();
}
