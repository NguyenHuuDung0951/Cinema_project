package dao;

import connectDB.ConnectDB;
import entity.TicketDetail;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class TicketDetail_DAO {
    public TicketDetail_DAO(){}

    public ArrayList<TicketDetail> getalltbTicketDetail() {
        ArrayList<TicketDetail> ds = new ArrayList<>();
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();
            String sql = "Select * from TicketDetail";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                String ticketID = rs.getString(1);
                String movieID  = rs.getString(2);
                LocalDate showDate = rs.getDate(3).toLocalDate();
                String seatID   = rs.getString(4);
                String roomID   = rs.getString(5);
                double price    = rs.getDouble(6);
                TicketDetail obj = new TicketDetail(ticketID, null, showDate, null, null, price);
                // TODO: load Movie, Seat, Room into obj
                ds.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }
}