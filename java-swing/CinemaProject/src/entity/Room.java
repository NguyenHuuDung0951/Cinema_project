package entity;

import java.util.Objects;

public class Room {

    private String roomID;
    private String roomName;
    private int numberOfSeats;

    public Room(String roomID, String roomName, int numberOfSeats) {
        this.roomID = roomID;
        this.roomName = roomName;
        this.numberOfSeats = numberOfSeats;
    }

    public Room(String roomID) {
        this.roomID = roomID;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Room)) {
            return false;
        }
        Room other = (Room) o;
        return Objects.equals(roomID, other.roomID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomID);
    }
}
