package com.database;

import org.bson.Document;

public class Guest {
    private int guestId;
    private String name;
    private String email;
    private String phone;

    public Guest() {
    }

    public Guest(int guestId, String name, String email, String phone) {
        this.guestId = guestId;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public int getGuestId() { return guestId; }
    public void setGuestId(int guestId) { this.guestId = guestId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public Document toDocument() {
        return new Document("_id", guestId)
                .append("name", name)
                .append("email", email)
                .append("phone", phone);
    }

    public static Guest fromDocument(Document doc) {
        Guest g = new Guest();
        g.setGuestId(doc.getInteger("_id"));
        g.setName(doc.getString("name"));
        g.setEmail(doc.getString("email"));
        g.setPhone(doc.getString("phone"));
        return g;
    }

    @Override
    public String toString() {
        return "Guest [guestId=" + guestId + ", name=" + name + ", email=" + email + ", phone=" + phone + "]";
    }
}