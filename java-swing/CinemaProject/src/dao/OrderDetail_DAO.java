package dao;

import connectDB.ConnectDB;
import entity.MovieSchedule;
import entity.OrderDetail;
import entity.Orders;
import entity.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetail_DAO {

    public boolean addOrderDetail(OrderDetail detail) throws SQLException {
        String sql = "INSERT INTO OrderDetail (orderID, productID, scheduleID, quantity) VALUES (?, ?, ?, ?)";
        try (Connection con = ConnectDB.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, detail.getOrder().getOrderID());
            ps.setString(2, detail.getProduct().getProductID());
            ps.setString(3, detail.getSchedule().getScheduleID());
            ps.setInt(4, detail.getQuantity());
            return ps.executeUpdate() > 0;
        }
    }

    public List<OrderDetail> getByOrderID(String orderID) throws SQLException {
        String sql = ""
                + "SELECT od.orderDetailID, od.productID, od.scheduleID, od.quantity, "
                + "       p.productName, p.productType, p.price, p.posterPath "
                + "FROM OrderDetail od "
                + "  JOIN Product p ON od.productID = p.productID "
                + "WHERE od.orderID = ?";
        List<OrderDetail> list = new ArrayList<>();
        try (Connection con = ConnectDB.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, orderID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String detailID = rs.getString("orderDetailID");
                    String prodID = rs.getString("productID");
                    String schedID = rs.getString("scheduleID");
                    int qty = rs.getInt("quantity");

                    String name = rs.getString("productName");
                    String type = rs.getString("productType");
                    double price = rs.getDouble("price");
                    String poster = rs.getString("posterPath");

                    Orders order = new Orders(orderID);
                    Product prod = new Product(
                            prodID, name, 0, type, price, poster);
                    MovieSchedule ms = new MovieSchedule(schedID);
                    OrderDetail od = new OrderDetail(
                            detailID, order, prod, ms, qty);

                    list.add(od);
                }
            }
        }
        return list;
    }
}
