package entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class TicketDetail {

    private String ticketID;
    private Movie movie;
    private LocalDateTime showDate;
    private Seat seat;
    private Room room;
    private double ticketPrice;
    private Orders orders;

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }
    public TicketDetail(String ticketID) {
        this.ticketID = ticketID;
    }

    public TicketDetail(String ticketID, Movie movie, LocalDateTime showDate,
            Seat seat, Room room, double ticketPrice, Orders orders) {
        this.ticketID = ticketID;
        this.movie = movie;
        this.showDate = showDate;
        this.seat = seat;
        this.room = room;
        this.ticketPrice = ticketPrice;
        this.orders = orders;
    }

    public String getTicketID() {
        return ticketID;
    }

    public void setTicketID(String ticketID) {
        this.ticketID = ticketID;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public LocalDateTime getShowDate() {
        return showDate;
    }

    public void setShowDate(LocalDateTime showDate) {
        this.showDate = showDate;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TicketDetail)) {
            return false;
        }
        TicketDetail other = (TicketDetail) o;
        return Objects.equals(ticketID, other.ticketID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketID);
    }
}
