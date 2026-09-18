package com.database;

public class DAOSmokeTest {
    public static void main(String[] args) {
        HotelDAO hdao = new HotelDAO();
        RoomsDAO rdao = new RoomsDAO();
        GuestDAO gdao = new GuestDAO();
        ReservationDAO resDao = new ReservationDAO();

        // 1. Create hotel
        Hotel h = new Hotel(0, "Test Hotel", "Test City", "Pool,Gym", 60.0, 100.0, 150.0);
        hdao.addHotel(h);
        System.out.println("Added hotel id: " + h.getHotelId());

        // 2. Create room for that hotel
        Room room = new Room(0, h.getHotelId(), "101", "Deluxe", 1200.00, "available");
        rdao.addRoom(room);
        System.out.println("Added room id: " + room.getRoomId());

        // 3. Create guest
        Guest guest = new Guest(0, "John Doe", "john@example.com", "9999999999");
        gdao.addGuest(guest);
        System.out.println("Added guest id: " + guest.getGuestId());

        // 4. Create reservation
        Reservation res = new Reservation(0, guest.getGuestId(), room.getRoomId(),
                java.time.LocalDate.now(), java.time.LocalDate.now().plusDays(2), room.getPrice() * 2);
        resDao.addReservation(res);
        System.out.println("Added reservation id: " + res.getReservationId());

        // 5. Read back
        System.out.println(hdao.getHotelById(h.getHotelId()));
        System.out.println(rdao.getRoomById(room.getRoomId()));
        System.out.println(gdao.getGuestById(guest.getGuestId()));
        System.out.println(resDao.getReservationById(res.getReservationId()));

        // 6. Update test (change hotel name)
        h.setName("Test Hotel Updated");
        hdao.updateHotel(h);
        System.out.println("Updated name: " + hdao.getHotelById(h.getHotelId()).getName());

        // 7. Delete cleanup
        resDao.deleteReservation(res.getReservationId());
        rdao.deleteRoom(room.getRoomId());
        gdao.deleteGuest(guest.getGuestId());
        hdao.deleteHotel(h.getHotelId());
        System.out.println("Cleaned up test data.");
    }
}