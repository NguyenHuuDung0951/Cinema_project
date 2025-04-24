package dao;

import connectDB.ConnectDB;
import entity.Movie;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Movie_DAO {
    public Movie_DAO(){}

    public ArrayList<Movie> getalltbMovie() {
        ArrayList<Movie> dsMovie = new ArrayList<>();
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();
            String sql = "Select * from Movie";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                String movieID = rs.getString(1);
                String movieName = rs.getNString(2);
                String status = rs.getNString(3);
                int duration = rs.getInt(4);
                Movie obj = new Movie(movieID, movieName, status, duration);
                dsMovie.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsMovie;
    }
}