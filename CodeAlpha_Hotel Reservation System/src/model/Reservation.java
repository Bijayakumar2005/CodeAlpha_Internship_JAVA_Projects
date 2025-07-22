package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Reservation {
    private static int nextId = 1;
    
    private final int id;
    private final String guestName;
    private final Room room;
    private final LocalDate checkInDate;
    private final LocalDate checkOutDate;
    private boolean isCancelled;

    public Reservation(String guestName, Room room, LocalDate checkInDate, LocalDate checkOutDate) {
        this.id = nextId++;
        this.guestName = guestName;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.isCancelled = false;
        room.setAvailable(false);
    }

    public double calculateTotal() {
        long nights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        return nights * room.getType().getBasePrice();
    }

    public void cancel() {
        this.isCancelled = true;
        room.setAvailable(true);
    }

    // Getters
    public int getId() { return id; }
    public String getGuestName() { return guestName; }
    public Room getRoom() { return room; }
    public LocalDate getCheckInDate() { return checkInDate; }
    public LocalDate getCheckOutDate() { return checkOutDate; }
    public boolean isCancelled() { return isCancelled; }

    @Override
    public String toString() {
        return String.format("Reservation #%d\nGuest: %s\n%s\nDates: %s to %s\nTotal: $%.2f\nStatus: %s",
                id, guestName, room, checkInDate, checkOutDate, calculateTotal(),
                isCancelled ? "Cancelled" : "Confirmed");
    }
}