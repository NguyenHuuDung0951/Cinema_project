package dao;

import connectDB.ConnectDB;
import entity.TicketDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketDetail_DAO {

    public String addTicket(TicketDetail ticket) throws SQLException {
        String insertSql
                = "INSERT INTO TicketDetail(movieID, showDate, seatID, room, ticketPrice) "
                + "VALUES (?, ?, ?, ?, ?)";
        String selectSql
                = "SELECT TOP 1 ticketID FROM TicketDetail ORDER BY ticketID DESC";

        try (Connection con = ConnectDB.getConnection()) {

            try (PreparedStatement ps = con.prepareStatement(insertSql)) {
                ps.setString(1, ticket.getMovie().getMovieID());

                ps.setTimestamp(2, java.sql.Timestamp.valueOf(ticket.getShowDate()));

                ps.setString(3, ticket.getSeat().getSeatID());
                ps.setString(4, ticket.getRoom().getRoomID());
                ps.setDouble(5, ticket.getTicketPrice());

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
