package dao;

import connectDB.ConnectDB;
import entity.Seat;
import entity.Room;
import entity.SeatType;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Seat_DAO {
    public Seat_DAO(){}

    public ArrayList<Seat> getalltbSeat() {
        ArrayList<Seat> ds = new ArrayList<>();
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();
            String sql = "Select * from Seat";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                String seatID = rs.getString(1);
                String loc = rs.getString(2);
                String roomID = rs.getString(3);
                String typeID = rs.getString(4);
                Seat obj = new Seat(seatID, loc, null, null);
                // TODO: load Room and SeatType into obj
                ds.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }
}