package model;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import entity.CartItem;
import entity.TicketDetail;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class BookingData {

    private static BookingData instance;
    private String movieID;
    private String roomID;
    private String voucherID;
    private byte[] qrCodeImageBytes;

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }
    private double discountAmount;

    public double getDiscountAmount() {
        return discountAmount;
    }
    public String getMovieID() {
        return movieID;
    }

    public void setVoucherID(String voucherID) {
        this.voucherID = voucherID;
    }

    public String getVoucherID() {
        return voucherID;
    }

    public void setMovieID(String movieID) {
        this.movieID = movieID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public void generateQrCode(String content, int size) {
        try {
            BitMatrix matrix = new MultiFormatWriter()
                    .encode(content, BarcodeFormat.QR_CODE, size, size);
            BufferedImage img = MatrixToImageWriter.toBufferedImage(matrix);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(img, "PNG", baos);
            this.qrCodeImageBytes = baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            this.qrCodeImageBytes = null;
        }
    }

    public byte[] getQrCodeImageBytes() {
        return qrCodeImageBytes;
    }

    public void setScheduleID(String scheduleID) {
        this.scheduleID = scheduleID;
    }

    public String getRoomID() {
        return roomID;
    }

    public String getScheduleID() {
        return scheduleID;
    }
    private String scheduleID;
    private String movieName;
    private String posterPath;
    private String roomName;
    private String showDate;
    private String showTime;
    private String selectedSeats;
    private double ticketTotal;
    private double productTotal;
    private ArrayList<CartItem> cartItems;
    private List<TicketDetail> lastTickets = new ArrayList<>();

    public List<TicketDetail> getLastTickets() {
        return lastTickets;
    }

    public void setLastTickets(List<TicketDetail> lastTickets) {
        this.lastTickets = lastTickets;
    }

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
        movieID = null;
        roomID = null;
        scheduleID = null;
        voucherID = null;
        ticketTotal = 0;
        productTotal = 0;
        cartItems.clear();
        lastTickets.clear();
    }

    // Getters & Setters
    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getShowDate() {
        return showDate;
    }

    public void setShowDate(String showDate) {
        this.showDate = showDate;
    }

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    public String getSelectedSeats() {
        return selectedSeats;
    }

    public void setSelectedSeats(String selectedSeats) {
        this.selectedSeats = selectedSeats;
    }

    public double getTicketTotal() {
        return ticketTotal;
    }

    public void setTicketTotal(double ticketTotal) {
        this.ticketTotal = ticketTotal;
    }

    public double getProductTotal() {
        return productTotal;
    }

    public void setProductTotal(double productTotal) {
        this.productTotal = productTotal;
    }

    public ArrayList<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(ArrayList<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

}
