package model;

public class Room {
    private String roomNumber;
    private RoomType type;
    private boolean isAvailable;

    public Room(String roomNumber, RoomType type) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.isAvailable = true;
    }

    // Getters and setters
    public String getRoomNumber() {
        return roomNumber;
    }

    public RoomType getType() {
        return type;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return "Room " + roomNumber + " (" + type.getDisplayName() + ")";
    }
}