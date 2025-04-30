package dao;

import connectDB.ConnectDB;
import entity.Orders;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Orders_DAO {

    public String addOrder(Orders order) throws SQLException {
        try (Connection con = ConnectDB.getConnection()) {
            // 1) Insert
            String ins = "INSERT INTO Orders(orderDate, totalPrice, employeeID, voucherID) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(ins)) {
                ps.setDate(1, java.sql.Date.valueOf(order.getOrderDate()));
                ps.setDouble(2, order.getTotalPrice());
                ps.setString(3, order.getEmployee().getEmployeeID());
                if (order.getVoucher() != null) {
                    ps.setString(4, order.getVoucher().getVoucherID());
                } else {
                    ps.setNull(4, java.sql.Types.VARCHAR);
                }
                ps.executeUpdate();
            }

            // 2) Lấy ID vừa sinh
            String sel = "SELECT TOP 1 orderID FROM Orders ORDER BY orderID DESC";
            try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sel)) {
                if (rs.next()) {
                    String newId = rs.getString(1);
                    order.setOrderID(newId);
                    return newId;
                }
                throw new SQLException("Không lấy được orderID sau insert");
            }
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
