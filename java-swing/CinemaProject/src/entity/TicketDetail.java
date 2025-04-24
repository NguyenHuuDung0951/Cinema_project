package entity;

import java.time.LocalDate;
import java.util.Objects;

public class TicketDetail {
    private String ticketID;
    private Movie movie;
    private LocalDate showDate;
    private Seat seat;
    private Room room;
    private double ticketPrice;

    public TicketDetail(String ticketID, Movie movie, LocalDate showDate,
                        Seat seat, Room room, double ticketPrice) {
        this.ticketID = ticketID;
        this.movie = movie;
        this.showDate = showDate;
        this.seat = seat;
        this.room = room;
        this.ticketPrice = ticketPrice;
    }

    public String getTicketID() { return ticketID; }
    public void setTicketID(String ticketID) { this.ticketID = ticketID; }

    public Movie getMovie() { return movie; }
    public void setMovie(Movie movie) { this.movie = movie; }

    public LocalDate getShowDate() { return showDate; }
    public void setShowDate(LocalDate showDate) { this.showDate = showDate; }

    public Seat getSeat() { return seat; }
    public void setSeat(Seat seat) { this.seat = seat; }

    public Room getRoom() { return room; }
    public void setRoom(Room room) { this.room = room; }

    public double getTicketPrice() { return ticketPrice; }
    public void setTicketPrice(double ticketPrice) { this.ticketPrice = ticketPrice; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TicketDetail)) return false;
        TicketDetail other = (TicketDetail) o;
        return Objects.equals(ticketID, other.ticketID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketID);
    }
}