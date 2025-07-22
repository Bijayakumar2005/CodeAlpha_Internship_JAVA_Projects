package controller;

import model.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HotelController {
    private List<Room> rooms;
    private List<Reservation> reservations;

    public HotelController() {
        initializeRooms();
        this.reservations = new ArrayList<>();
    }

    private void initializeRooms() {
        rooms = new ArrayList<>();
        // Add some sample rooms
        rooms.add(new Room("101", RoomType.STANDARD));
        rooms.add(new Room("102", RoomType.STANDARD));
        rooms.add(new Room("201", RoomType.DELUXE));
        rooms.add(new Room("202", RoomType.DELUXE));
        rooms.add(new Room("301", RoomType.SUITE));
        rooms.add(new Room("302", RoomType.SUITE));
        rooms.add(new Room("401", RoomType.EXECUTIVE_SUITE));
        rooms.add(new Room("501", RoomType.PRESIDENTIAL));
    }

    public String searchAvailableRooms(RoomType type, LocalDate checkIn, LocalDate checkOut) {
        List<Room> availableRooms = new ArrayList<>();
        
        for (Room room : rooms) {
            if (room.getType() == type && room.isAvailable()) {
                // Check if room is available for the entire duration
                boolean isAvailable = true;
                for (Reservation res : reservations) {
                    if (res.getRoom().equals(room) && !res.isCancelled()) {
                        if (!(checkOut.isBefore(res.getCheckInDate())) && 
                            !(checkIn.isAfter(res.getCheckOutDate()))) {
                            isAvailable = false;
                            break;
                        }
                    }
                }
                if (isAvailable) {
                    availableRooms.add(room);
                }
            }
        }

        if (availableRooms.isEmpty()) {
            return "No available " + type.getDisplayName() + " rooms for the selected dates.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Available ").append(type.getDisplayName()).append(" Rooms:\n\n");
        for (Room room : availableRooms) {
            sb.append(room).append("\nPrice per night: $")
              .append(room.getType().getBasePrice()).append("\n\n");
        }
        return sb.toString();
    }

    public String bookRoom(String guestName, RoomType type, LocalDate checkIn, LocalDate checkOut) {
        // Find first available room of the selected type
        for (Room room : rooms) {
            if (room.getType() == type && room.isAvailable()) {
                boolean isAvailable = true;
                for (Reservation res : reservations) {
                    if (res.getRoom().equals(room) && !res.isCancelled()) {
                        if (!(checkOut.isBefore(res.getCheckInDate()) && 
                            !(checkIn.isAfter(res.getCheckOutDate())))) {
                            isAvailable = false;
                            break;
                        }
                    }
                }
                if (isAvailable) {
                    Reservation reservation = new Reservation(guestName, room, checkIn, checkOut);
                    reservations.add(reservation);
                    return "Booking confirmed!\n\n" + reservation;
                }
            }
        }
        return "Failed to book room. No available " + type.getDisplayName() + " rooms.";
    }

    public String getAllReservations() {
        if (reservations.isEmpty()) {
            return "No reservations found.";
        }

        StringBuilder sb = new StringBuilder("All Reservations:\n\n");
        for (Reservation res : reservations) {
            sb.append(res).append("\n\n");
        }
        return sb.toString();
    }

    public String cancelReservation(int reservationId) {
        for (Reservation res : reservations) {
            if (res.getId() == reservationId && !res.isCancelled()) {
                res.cancel();
                return "Reservation #" + reservationId + " has been cancelled.\n\n" + res;
            }
        }
        return "Reservation #" + reservationId + " not found or already cancelled.";
    }
}