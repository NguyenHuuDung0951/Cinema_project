package model;

import entity.CartItem;
import java.util.ArrayList;

public class BookingData {
    private static BookingData instance;

    private String movieName;
    private String posterPath;
    private String roomName;
    private String showDate;
    private String showTime;
    private String selectedSeats;
    private double ticketTotal;
    private double productTotal;
    private ArrayList<CartItem> cartItems;

    public BookingData() {
        cartItems = new ArrayList<>();
    }

    public static BookingData getInstance() {
        if (instance == null) {
            instance = new BookingData();
        }
        return instance;
    }

    public void reset() {
        movieName = null;
        posterPath = null;
        roomName = null;
        showDate = null;
        showTime = null;
        selectedSeats = null;
        ticketTotal = 0;
        productTotal = 0;
        cartItems.clear();
    }

    // Getters & Setters

    public String getMovieName() { return movieName; }
    public void setMovieName(String movieName) { this.movieName = movieName; }

    public String getPosterPath() { return posterPath; }
    public void setPosterPath(String posterPath) { this.posterPath = posterPath; }

    public String getRoomName() { return roomName; }
    public void setRoomName(String roomName) { this.roomName = roomName; }

    public String getShowDate() { return showDate; }
    public void setShowDate(String showDate) { this.showDate = showDate; }

    public String getShowTime() { return showTime; }
    public void setShowTime(String showTime) { this.showTime = showTime; }

    public String getSelectedSeats() { return selectedSeats; }
    public void setSelectedSeats(String selectedSeats) { this.selectedSeats = selectedSeats; }

    public double getTicketTotal() { return ticketTotal; }
    public void setTicketTotal(double ticketTotal) { this.ticketTotal = ticketTotal; }

    public double getProductTotal() { return productTotal; }
    public void setProductTotal(double productTotal) { this.productTotal = productTotal; }

    public ArrayList<CartItem> getCartItems() { return cartItems; }
    public void setCartItems(ArrayList<CartItem> cartItems) { this.cartItems = cartItems; }
}
