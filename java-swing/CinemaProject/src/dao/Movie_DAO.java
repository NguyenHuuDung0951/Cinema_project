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
                String posterPath = rs.getString(5);
                Movie obj = new Movie(movieID, movieName, status, duration, posterPath);
                dsMovie.add(obj); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsMovie;
    }
    public boolean addMovie(Movie movie) {
        String sql = "INSERT INTO Movie (movieID, movieName, status, duration, posterPath) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

          
            stmt.setString(1, movie.getMovieID());
            stmt.setString(2, movie.getMovieName());
            stmt.setString(3, movie.getStatus());
            stmt.setInt(4, movie.getDuration());
            stmt.setString(5, movie.getPosterPath());
            
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
public boolean editMovie(Movie movie) {
    String sql = "UPDATE Movie SET movieName = ?, status = ?, duration = ?, posterPath = ? WHERE movieID = ?";
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, movie.getMovieName());
        stmt.setString(2, movie.getStatus());
        stmt.setInt(3, movie.getDuration());
        stmt.setString(4, movie.getPosterPath());
        stmt.setString(5, movie.getMovieID());
        return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

public boolean deleteMovie(String movieID) {
    Connection conn = null;
    PreparedStatement stmt = null;
    try {
        conn = ConnectDB.getConnection(); 
        String sql = "DELETE FROM Movie WHERE MovieID = ?";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, movieID);

        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0; 
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    } finally {
        try {
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
public ArrayList<Movie> searchMovieByName(String name) {
    ArrayList<Movie> result = new ArrayList<>();
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    try {
        conn = ConnectDB.getConnection();
        String sql = "SELECT * FROM Movie WHERE MovieName LIKE ?";
        ps = conn.prepareStatement(sql);
        ps.setString(1, "%" + name + "%");
        rs = ps.executeQuery();
        
        while (rs.next()) {
            String id = rs.getString("MovieID");
            String movieName = rs.getString("MovieName");
            String status = rs.getString("Status");
            int duration = rs.getInt("Duration");
            String posterPath = rs.getString("posterPath");
            result.add(new Movie(id, movieName, status, duration, posterPath));
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    return result;
}
}