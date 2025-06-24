package org.example.view.mainWindow;

public class Hotel {
    private int id;
    private String category;
    private String name;
    private String address;
    private String city;
    private String cityCode;
    private int noRooms;
    private int noBeds;
    private String attribute;



    public Hotel(int id, String category, String name, String address, String city,
                 String cityCode, int noRooms, int noBeds, String state) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.address = address;
        this.city = city;
        this.cityCode = cityCode;
        this.noRooms = noRooms;
        this.noBeds = noBeds;
        this.attribute = attribute;
    }


    // Getter
    public int getId() { return id; }
    public String getCategory() { return category; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getCity() { return city; }
    public String getCityCode() { return cityCode; }
    public int getNoRooms() { return noRooms; }
    public int getNoBeds() { return noBeds; }
    public String getAttribute() { return attribute; }

    // Setter
    public void setCategory(String category) { this.category = category; }
    public void setName(String name) { this.name = name; }
    public void setAddress(String address) { this.address = address; }
    public void setCity(String city) { this.city = city; }
    public void setCityCode(String cityCode) { this.cityCode = cityCode; }
    public void setNoRooms(int noRooms) { this.noRooms = noRooms; }
    public void setNoBeds(int noBeds) { this.noBeds = noBeds; }
    public void setAttribute(String attribute) { this.attribute = attribute; }
}
