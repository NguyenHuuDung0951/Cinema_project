package dao;

import connectDB.ConnectDB;
import entity.Orders;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class Orders_DAO {
    public Orders_DAO(){}

    public ArrayList<Orders> getalltbOrders() {
        ArrayList<Orders> dsOrders = new ArrayList<>();
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();
            String sql = "Select * from Orders";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                String orderID = rs.getString(1);
                LocalDate orderDate = rs.getDate(2).toLocalDate();
                double totalPrice = rs.getDouble(3);
                String empID = rs.getString(4);
                // If voucher column exists: String voucherID = rs.getString(5);
                Orders obj = new Orders(orderID, orderDate, totalPrice, null, null);
                // TODO: load Employee & Voucher into obj
                dsOrders.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsOrders;
    }
}
