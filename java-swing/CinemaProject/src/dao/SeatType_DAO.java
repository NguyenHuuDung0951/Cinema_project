package dao;

import connectDB.ConnectDB;
import entity.SeatType;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SeatType_DAO {
    public SeatType_DAO(){}

    public ArrayList<SeatType> getalltbSeatType() {
        ArrayList<SeatType> ds = new ArrayList<>();
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();
            String sql = "Select * from SeatType";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                String id = rs.getString(1);
                String name = rs.getNString(2);
                String desc = rs.getNString(3);
                SeatType obj = new SeatType(id, name, desc);
                ds.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }
}
