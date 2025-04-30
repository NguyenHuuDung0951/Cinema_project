package entity;

import java.util.Objects;

public class SeatType {

    private String seatTypeID;
    private String seatTypeName;
    private String descriptionSeat;

    public SeatType(String seatTypeID) {
        this.seatTypeID = seatTypeID;
    }

    public SeatType(String seatTypeID, String seatTypeName, String descriptionSeat) {
        this.seatTypeID = seatTypeID;
        this.seatTypeName = seatTypeName;
        this.descriptionSeat = descriptionSeat;
    }

    public String getSeatTypeID() {
        return seatTypeID;
    }

    public void setSeatTypeID(String seatTypeID) {
        this.seatTypeID = seatTypeID;
    }

    public String getSeatTypeName() {
        return seatTypeName;
    }

    public void setSeatTypeName(String seatTypeName) {
        this.seatTypeName = seatTypeName;
    }

    public String getDescriptionSeat() {
        return descriptionSeat;
    }

    public void setDescriptionSeat(String descriptionSeat) {
        this.descriptionSeat = descriptionSeat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SeatType)) {
            return false;
        }
        SeatType other = (SeatType) o;
        return Objects.equals(seatTypeID, other.seatTypeID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seatTypeID);
    }
}
