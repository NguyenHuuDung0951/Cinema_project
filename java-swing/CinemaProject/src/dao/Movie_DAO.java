package dao;

import connectDB.ConnectDB;
import static connectDB.ConnectDB.getConnection;
import entity.Movie;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import raven.toast.Notifications;

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
                String movieName = rs.getString(2);
                String status = rs.getString(3);
                int duration = rs.getInt(4);
                Movie obj = new Movie(movieID, movieName, status, duration);
                dsMovie.add(obj); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsMovie;
    }
    public boolean addMovie(Movie movie) {
        String sql = "INSERT INTO Movie (movieID, movieName, status, duration) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

          
            stmt.setString(1, movie.getMovieID());
            stmt.setString(2, movie.getMovieName());
            stmt.setString(3, movie.getStatus());
            stmt.setInt(4, movie.getDuration());

            
            int rowsAffected = stmt.executeUpdate();

        
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  
        }
    }
    public Movie getMovieById(int id) {
    Movie movie = null;
    try {
        Connection con = getConnection(); 
        String sql = "SELECT * FROM Phim WHERE maPhim = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            
            movie.setMovieID(rs.getString("movieID"));
            movie.setMovieName(rs.getString("movieName"));
            movie.setStatus(rs.getString("status"));
            movie.setDuration(rs.getInt("duration"));
            
        }
        rs.close();
        ps.close();
        con.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return movie;
}

    private void updateMovieInDB(Movie movie) {
 
    String sql = "UPDATE phim SET movieName = ?, status = ?, duration = ? WHERE movieID = ?";
    
    try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, movie.getMovieName());
        ps.setString(2, movie.getStatus());
        ps.setInt(3, movie.getDuration());
        ps.setString(4, movie.getMovieID());
        ps.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
        Notifications.getInstance().show(Notifications.Type.ERROR, "Có lỗi xảy ra khi cập nhật dữ liệu!");
    }
}
}