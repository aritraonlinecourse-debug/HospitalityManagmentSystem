package com.database;

import org.bson.Document;

public class Hotel {
    private int hotelId;
    private String name;
    private String location;
    private String amenities;

    public Hotel() {
    }

    public Hotel(int hotelId, String name, String location, String amenities) {
        this.hotelId = hotelId;
        this.name = name;
        this.location = location;
        this.amenities = amenities;
    }

    public int getHotelId() { return hotelId; }
    public void setHotelId(int hotelId) { this.hotelId = hotelId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getAmenities() { return amenities; }
    public void setAmenities(String amenities) { this.amenities = amenities; }

    public Document toDocument() {
        return new Document("_id", hotelId)
                .append("name", name)
                .append("location", location)
                .append("amenities", amenities);
    }

    public static Hotel fromDocument(Document doc) {
        Hotel h = new Hotel();
        h.setHotelId(doc.getInteger("_id"));
        h.setName(doc.getString("name"));
        h.setLocation(doc.getString("location"));
        h.setAmenities(doc.getString("amenities"));
        return h;
    }

    @Override
    public String toString() {
        return "Hotel [hotelId=" + hotelId + ", name=" + name + ", location=" + location + ", amenities=" + amenities + "]";
    }
}