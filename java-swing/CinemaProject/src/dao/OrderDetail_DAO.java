package dao;

import connectDB.ConnectDB;
import entity.OrderDetail;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class OrderDetail_DAO {
    public OrderDetail_DAO(){}

    public ArrayList<OrderDetail> getalltbOrderDetail() {
        ArrayList<OrderDetail> ds = new ArrayList<>();
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();
            String sql = "Select * from OrderDetail";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                String detailID = rs.getString(1);
                String ticketID = rs.getString(2);
                String orderID  = rs.getString(3);
                String productID= rs.getString(4);
                String schedID  = rs.getString(5);
                int qty         = rs.getInt(6);
                OrderDetail obj = new OrderDetail(detailID, null, null, null, null, qty);
                // TODO: load TicketDetail, Order, Product, MovieSchedule into obj
                ds.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }
}