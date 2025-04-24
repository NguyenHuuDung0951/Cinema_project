package entity;

import java.util.Objects;

public class Seat {
    private String seatID;
    private String location;
    private Room room;          // FK object
    private SeatType seatType;  // FK object

    public Seat(String seatID, String location, Room room, SeatType seatType) {
        this.seatID = seatID;
        this.location = location;
        this.room = room;
        this.seatType = seatType;
    }

    public String getSeatID() { return seatID; }
    public void setSeatID(String seatID) { this.seatID = seatID; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Room getRoom() { return room; }
    public void setRoom(Room room) { this.room = room; }

    public SeatType getSeatType() { return seatType; }
    public void setSeatType(SeatType seatType) { this.seatType = seatType; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Seat)) return false;
        Seat other = (Seat) o;
        return Objects.equals(seatID, other.seatID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seatID);
    }
}