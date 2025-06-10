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
    private String state;

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "hotel_amenity",
            joinColumns = @JoinColumn(name = "hotel_id"),
            inverseJoinColumns = @JoinColumn(name = "amenity_id")
    )
    private Set<Amenity> amenities = new HashSet<>();

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Occupancy> occupancies = new ArrayList<>();

    public void addAmenity(Amenity amenity) {
        this.amenities.add(amenity);
    }

    public void removeAmenity(Amenity amenity) {
        this.amenities.remove(amenity);
    }
}
