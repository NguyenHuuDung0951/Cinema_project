package dao;

import connectDB.ConnectDB;
import entity.Movie;
import entity.Orders;
import entity.Room;
import entity.Seat;
import entity.TicketDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TicketDetail_DAO {

    public List<TicketDetail> getByOrderID(String orderID) throws SQLException {
        String sql = ""
                + "SELECT td.ticketID, td.showDate, td.ticketPrice, "
                + "       td.movieID, m.movieName, "
                + "       td.seatID, s.location, "
                + "       td.room "
                + "FROM TicketDetail td "
                + "  JOIN Movie m ON td.movieID = m.movieID "
                + "  JOIN Seat s  ON td.seatID   = s.seatID "
                + "WHERE td.orderID = ?";
        List<TicketDetail> list = new ArrayList<>();
        try (Connection con = ConnectDB.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, orderID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String tid = rs.getString("ticketID");
                    LocalDateTime dt = rs.getTimestamp("showDate").toLocalDateTime();
                    double price = rs.getDouble("ticketPrice");

                    String mid = rs.getString("movieID");
                    String mname = rs.getString("movieName");
                    Movie mv = new Movie(mid, mname, null, 0, null);

                    String sid = rs.getString("seatID");
                    String loc = rs.getString("location");
                    Seat seat = new Seat(sid);
                    seat.setLocation(loc);   

                    Room room = new Room(rs.getString("room"));

                    Orders ord = new Orders(orderID);
                    TicketDetail td = new TicketDetail(tid, mv, dt, seat, room, price, ord);
                    list.add(td);
                }
            }
        }
        return list;
    }

    

    public String addTicket(TicketDetail ticket) throws SQLException {
        String insertSql
                = "INSERT INTO TicketDetail(movieID, showDate, seatID, room, ticketPrice, orderID) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        String selectSql
                = "SELECT TOP 1 ticketID FROM TicketDetail ORDER BY ticketID DESC";

        try (Connection con = ConnectDB.getConnection()) {

            try (PreparedStatement ps = con.prepareStatement(insertSql)) {
                ps.setString(1, ticket.getMovie().getMovieID());

                ps.setTimestamp(2, java.sql.Timestamp.valueOf(ticket.getShowDate()));

                ps.setString(3, ticket.getSeat().getSeatID());
                ps.setString(4, ticket.getRoom().getRoomID());
                ps.setDouble(5, ticket.getTicketPrice());
                ps.setString(6, ticket.getOrders().getOrderID());
                ps.executeUpdate();
            }

            try (PreparedStatement ps2 = con.prepareStatement(selectSql); ResultSet rs = ps2.executeQuery()) {

                if (rs.next()) {
                    String newId = rs.getString("ticketID");
                    ticket.setTicketID(newId);
                    return newId;
                } else {
                    throw new SQLException("Không lấy được ticketID sau khi insert");
                }
            }
        }
    }

}
