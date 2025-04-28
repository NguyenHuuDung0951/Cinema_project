package dao;

import connectDB.ConnectDB;
import entity.Orders;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Orders_DAO {
    public boolean addOrder(Orders order) throws SQLException {
        String sql = "INSERT INTO Orders (orderDate, totalPrice, employeeID, voucherID) VALUES (?, ?, ?, ?)";
        try (Connection con = ConnectDB.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, java.sql.Date.valueOf(order.getOrderDate()));
            ps.setDouble(2, order.getTotalPrice());
            ps.setString(3, order.getEmployee().getEmployeeID());
            ps.setString(4, order.getVoucher().getVoucherID());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean updateTotalPrice(Orders order) throws SQLException {
        String sql = "UPDATE Orders SET totalPrice = ? WHERE orderID = ?";
        try (Connection con = ConnectDB.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, order.getTotalPrice());
            ps.setString(2, order.getOrderID());
            return ps.executeUpdate() > 0;
        }
    }
}