package dao;

import connectDB.ConnectDB;
import entity.MovieScheduleSeat;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MovieScheduleSeat_DAO {
    public MovieScheduleSeat_DAO(){}

    public ArrayList<MovieScheduleSeat> getalltbMovieScheduleSeat() {
        ArrayList<MovieScheduleSeat> ds = new ArrayList<>();
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();
            String sql = "Select * from MovieScheduleSeat";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                String schedID = rs.getString(1);
                String seatID  = rs.getString(2);
                boolean available = rs.getBoolean(3);
                MovieScheduleSeat obj = new MovieScheduleSeat(null, null, available);
                // TODO: load MovieSchedule and Seat
                ds.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }
}