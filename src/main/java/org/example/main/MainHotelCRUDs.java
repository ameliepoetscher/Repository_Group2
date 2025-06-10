package org.example.main;

import org.example.HotelDAO;
import org.example.entity.Hotel;

public class MainHotelCRUDs {

    public static void main(String[] args) {


        Hotel hotel = new Hotel(2,
                "5",
                "Hotel Demo",
                "Test Owner",
                "Test Contact",
                "Test Address",
                "Test City",
                "1234",
                "+43 1234567890",
                100,
                200, null, null
        );


        HotelDAO.createHotel(hotel);


    }
}
