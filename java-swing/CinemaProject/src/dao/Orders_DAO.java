package dao;

import connectDB.ConnectDB;
import entity.Employee;
import entity.Orders;
import entity.Voucher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Orders_DAO {

    public String addOrder(Orders order) throws SQLException {
        try (Connection con = ConnectDB.getConnection()) {
            
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

    public Orders getOrderByID(String orderID) throws SQLException {
        String sql = ""
                + "SELECT o.orderID, o.orderDate, o.totalPrice, "
                + "       o.employeeID, e.fullName, "
                + "       o.voucherID "
                + "FROM Orders o "
                + "  JOIN Employee e ON o.employeeID = e.employeeID "
                + "WHERE o.orderID = ?";
        try (Connection con = ConnectDB.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, orderID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    LocalDate date = rs.getDate("orderDate").toLocalDate();
                    double total = rs.getDouble("totalPrice");
                    String eid = rs.getString("employeeID");
                    String name = rs.getString("fullName");
                    Employee emp = new Employee(eid, name);

                    String vid = rs.getString("voucherID");
                    Voucher v = vid != null ? new Voucher(vid) : null;

                    return new Orders(orderID, date, total, emp, v);
                }
                return null;
            }
        }
    }

    public ArrayList<Orders> getalltbOrders() {
        ArrayList<Orders> listOrders = new ArrayList<>();
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();
            String sql = "Select * from Orders";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String orderID = rs.getString(1);
                LocalDate orderDate = rs.getDate(2).toLocalDate();
                double totalPrice = rs.getDouble(3);
                String employeeID = rs.getString(4);
                Employee em = new Employee(employeeID);
                String voucherID = rs.getString(5);
                Voucher vc = new Voucher(voucherID);
                Orders obj = new Orders(orderID, orderDate, totalPrice, em, vc);
                listOrders.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOrders;
    }
    
    public List<String> getAllOrderIDs() throws SQLException {
        String sql = "SELECT orderID FROM Orders";
        List<String> list = new ArrayList<>();
        try (Connection con = ConnectDB.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(rs.getString("orderID"));
            }
        }
        return list;
    }

}
