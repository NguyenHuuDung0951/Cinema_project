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

    public MovieSchedule_DAO() {
    }

    public ArrayList<MovieSchedule> getalltbMovieSchedule() {
        ArrayList<MovieSchedule> ds = new ArrayList<>();
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT ms.scheduleID, m.movieID, m.movieName, m.status, m.duration, m.posterPath,"
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
                        rs.getInt("duration"),
                        rs.getString("posterPath")
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
            String sql = "SELECT m.movieID, m.movieName, m.status, m.duration, m.posterPath,"
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
                        rs.getInt("duration"),
                        rs.getString("posterPath")
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

    public boolean addMovieSchedule(String movieID, String roomID,
            LocalDateTime start, LocalDateTime end) {
        String sql = "INSERT INTO MovieSchedule (movieID, room, startTime, endTime) "
                + "VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectDB.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, movieID);
            stmt.setString(2, roomID);
            stmt.setTimestamp(3, java.sql.Timestamp.valueOf(start));
            stmt.setTimestamp(4, java.sql.Timestamp.valueOf(end));
            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean deleteSchedulesByMovieID(String movieID) {
        String delOrderDetails
                = "DELETE od "
                + "FROM OrderDetail od "
                + "  JOIN MovieSchedule ms ON od.scheduleID = ms.scheduleID "
                + "WHERE ms.movieID = ?";

        String delTicketDetails = ""
                + "DELETE td "
                + "FROM TicketDetail td "
                + "  JOIN OrderDetail od ON td.orderID = od.orderID "
                + "  JOIN MovieSchedule ms ON od.scheduleID = ms.scheduleID "
                + "WHERE ms.movieID = ?";

        String delSeats
                = "DELETE FROM MovieScheduleSeat "
                + "WHERE scheduleID IN (SELECT scheduleID FROM MovieSchedule WHERE movieID = ?)";

        String delSchedules
                = "DELETE FROM MovieSchedule WHERE movieID = ?";

        try (Connection conn = ConnectDB.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement p1 = conn.prepareStatement(delOrderDetails); PreparedStatement p2 = conn.prepareStatement(delTicketDetails); PreparedStatement p3 = conn.prepareStatement(delSeats); PreparedStatement p4 = conn.prepareStatement(delSchedules)) {

                p1.setString(1, movieID);
                p1.executeUpdate();

                p2.setString(1, movieID);
                p2.executeUpdate();

                p3.setString(1, movieID);
                p3.executeUpdate();

                p4.setString(1, movieID);
                int rows = p4.executeUpdate();

                conn.commit();
                return rows > 0;
            } catch (SQLException ex) {
                conn.rollback();
                throw ex;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
