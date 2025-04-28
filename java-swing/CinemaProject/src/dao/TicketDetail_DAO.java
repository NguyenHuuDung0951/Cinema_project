package dao;

import connectDB.ConnectDB;
import entity.TicketDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TicketDetail_DAO {
    public boolean addTicket(TicketDetail ticket) throws SQLException {
        String sql = "INSERT INTO TicketDetail (movieID, showDate, seatID, room, ticketPrice) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = ConnectDB.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, ticket.getMovie().getMovieID());
            ps.setDate(2, java.sql.Date.valueOf(ticket.getShowDate()));
            ps.setString(3, ticket.getSeat().getSeatID());
            ps.setString(4, ticket.getRoom().getRoomID());
            ps.setDouble(5, ticket.getTicketPrice());
            return ps.executeUpdate() > 0;
        }
    }
}