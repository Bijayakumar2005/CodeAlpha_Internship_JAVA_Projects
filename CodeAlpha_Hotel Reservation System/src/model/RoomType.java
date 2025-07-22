package model;

public enum RoomType {
    STANDARD("Standard", 100),
    DELUXE("Deluxe", 150),
    SUITE("Suite", 250),
    EXECUTIVE_SUITE("Executive Suite", 350),
    PRESIDENTIAL("Presidential", 500);

    private final String displayName;
    private final double basePrice;

    RoomType(String displayName, double basePrice) {
        this.displayName = displayName;
        this.basePrice = basePrice;
    }

    public String getDisplayName() {
        return displayName;
    }

    public double getBasePrice() {
        return basePrice;
    }

    @Override
    public String toString() {
        return displayName;
    }
}