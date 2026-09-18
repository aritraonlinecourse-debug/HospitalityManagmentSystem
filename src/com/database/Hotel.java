package com.database;

import org.bson.Document;

public class Hotel {
    private int hotelId;
    private String name;
    private String location;
    private String amenities;
    private double singleRoomPrice;
    private double coupleRoomPrice;
    private double familyRoomPrice;

    public Hotel() {
    }

    public Hotel(int hotelId, String name, String location, String amenities,
                 double singleRoomPrice, double coupleRoomPrice, double familyRoomPrice) {
        this.hotelId = hotelId;
        this.name = name;
        this.location = location;
        this.amenities = amenities;
        this.singleRoomPrice = singleRoomPrice;
        this.coupleRoomPrice = coupleRoomPrice;
        this.familyRoomPrice = familyRoomPrice;
    }

    public int getHotelId() { return hotelId; }
    public void setHotelId(int hotelId) { this.hotelId = hotelId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getAmenities() { return amenities; }
    public void setAmenities(String amenities) { this.amenities = amenities; }
    public double getSingleRoomPrice() { return singleRoomPrice; }
    public void setSingleRoomPrice(double singleRoomPrice) { this.singleRoomPrice = singleRoomPrice; }
    public double getCoupleRoomPrice() { return coupleRoomPrice; }
    public void setCoupleRoomPrice(double coupleRoomPrice) { this.coupleRoomPrice = coupleRoomPrice; }
    public double getFamilyRoomPrice() { return familyRoomPrice; }
    public void setFamilyRoomPrice(double familyRoomPrice) { this.familyRoomPrice = familyRoomPrice; }

    public Document toDocument() {
        return new Document("_id", hotelId)
                .append("name", name)
                .append("location", location)
                .append("amenities", amenities)
                .append("singleRoomPrice", singleRoomPrice)
                .append("coupleRoomPrice", coupleRoomPrice)
                .append("familyRoomPrice", familyRoomPrice);
    }

    public static Hotel fromDocument(Document doc) {
        Hotel h = new Hotel();
        h.setHotelId(doc.getInteger("_id"));
        h.setName(doc.getString("name"));
        h.setLocation(doc.getString("location"));
        h.setAmenities(doc.getString("amenities"));
        h.setSingleRoomPrice(doc.get("singleRoomPrice") != null ? doc.getDouble("singleRoomPrice") : 0.0);
        h.setCoupleRoomPrice(doc.get("coupleRoomPrice") != null ? doc.getDouble("coupleRoomPrice") : 0.0);
        h.setFamilyRoomPrice(doc.get("familyRoomPrice") != null ? doc.getDouble("familyRoomPrice") : 0.0);
        return h;
    }

    @Override
    public String toString() {
        return "Hotel [hotelId=" + hotelId + ", name=" + name + ", location=" + location
                + ", amenities=" + amenities
                + ", singleRoomPrice=" + singleRoomPrice
                + ", coupleRoomPrice=" + coupleRoomPrice
                + ", familyRoomPrice=" + familyRoomPrice + "]";
    }
}