package dao;

import connectDB.ConnectDB;
import entity.MovieSchedule;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class MovieSchedule_DAO {
    public MovieSchedule_DAO(){}

    public ArrayList<MovieSchedule> getalltbMovieSchedule() {
        ArrayList<MovieSchedule> ds = new ArrayList<>();
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();
            String sql = "Select * from MovieSchedule";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                String schedID = rs.getString(1);
                String movieID = rs.getString(2);
                String roomID = rs.getString(3);
                LocalDateTime start= rs.getTimestamp(4).toLocalDateTime();
                LocalDateTime end  = rs.getTimestamp(5).toLocalDateTime();
                MovieSchedule obj = new MovieSchedule(schedID, null, null, start, end);
                // TODO: load Movie and Room
                ds.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }
}