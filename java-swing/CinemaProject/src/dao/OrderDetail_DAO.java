package dao;

import connectDB.ConnectDB;
import entity.OrderDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderDetail_DAO {
    public boolean addOrderDetail(OrderDetail detail) throws SQLException {
        String sql = "INSERT INTO OrderDetail (ticketID, orderID, productID, scheduleID, quantity) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = ConnectDB.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, detail.getTicketDetail().getTicketID());
            ps.setString(2, detail.getOrder().getOrderID());
            ps.setString(3, detail.getProduct().getProductID());
            ps.setString(4, detail.getSchedule().getScheduleID());
            ps.setInt(5, detail.getQuantity());
            return ps.executeUpdate() > 0;
        }
    }
}