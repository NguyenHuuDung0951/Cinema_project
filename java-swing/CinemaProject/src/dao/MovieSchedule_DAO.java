package dao;

import connectDB.ConnectDB;
import entity.Movie;
import entity.MovieSchedule;
import entity.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class MovieSchedule_DAO {
    public MovieSchedule_DAO() {}

    public ArrayList<MovieSchedule> getalltbMovieSchedule() {
        ArrayList<MovieSchedule> ds = new ArrayList<>();
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT ms.scheduleID, m.movieID, m.movieName, m.status, m.duration, "
                       + "r.room, r.roomName, r.numberOfSeats, "
                       + "ms.startTime, ms.endTime "
                       + "FROM MovieSchedule ms "
                       + "JOIN Movie m ON ms.movieID = m.movieID "
                       + "JOIN Room r ON ms.room = r.room";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String scheduleID = rs.getString("scheduleID");
                Movie movie = new Movie(
                    rs.getString("movieID"),
                    rs.getString("movieName"),
                    rs.getString("status"),
                    rs.getInt("duration")
                );
                Room room = new Room(
                    rs.getString("room"),
                    rs.getString("roomName"),
                    rs.getInt("numberOfSeats")
                );
                LocalDateTime start = rs.getTimestamp("startTime").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("endTime").toLocalDateTime();

                MovieSchedule schedule = new MovieSchedule(scheduleID, movie, room, start, end);
                ds.add(schedule);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    public Map<Movie, ArrayList<MovieSchedule>> getMovieWithSchedules() {
        Map<Movie, ArrayList<MovieSchedule>> map = new LinkedHashMap<>();
        try (Connection con = ConnectDB.getInstance().getConnection()) {
            String sql = "SELECT m.movieID, m.movieName, m.status, m.duration, "
                       + "ms.scheduleID, r.room, r.roomName, r.numberOfSeats, "
                       + "ms.startTime, ms.endTime "
                       + "FROM Movie m "
                       + "JOIN MovieSchedule ms ON m.movieID = ms.movieID "
                       + "JOIN Room r ON ms.room = r.room";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Movie movie = new Movie(
                    rs.getString("movieID"),
                    rs.getString("movieName"),
                    rs.getString("status"),
                    rs.getInt("duration")
                );
                Room room = new Room(
                    rs.getString("room"),
                    rs.getString("roomName"),
                    rs.getInt("numberOfSeats")
                );
                MovieSchedule schedule = new MovieSchedule(
                    rs.getString("scheduleID"),
                    movie,
                    room,
                    rs.getTimestamp("startTime").toLocalDateTime(),
                    rs.getTimestamp("endTime").toLocalDateTime()
                );

                map.computeIfAbsent(movie, k -> new ArrayList<>()).add(schedule);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }
}
