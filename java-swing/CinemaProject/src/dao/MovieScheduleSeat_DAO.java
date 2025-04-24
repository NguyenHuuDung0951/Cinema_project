package dao;

import connectDB.ConnectDB;
import entity.MovieScheduleSeat;
import entity.Seat;
import entity.MovieSchedule;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MovieScheduleSeat_DAO {
    public MovieScheduleSeat_DAO(){}

    public ArrayList<MovieScheduleSeat> getalltbMovieScheduleSeat() throws SQLException {
        // 1) Load toàn bộ Seat và MovieSchedule trước
        Map<String, Seat> seatMap = new Seat_DAO()
            .getalltbSeat()
            .stream()
            .collect(Collectors.toMap(Seat::getSeatID, Function.identity()));
        Map<String, MovieSchedule> schedMap = new MovieSchedule_DAO()
            .getalltbMovieSchedule()
            .stream()
            .collect(Collectors.toMap(MovieSchedule::getScheduleID, Function.identity()));

        ArrayList<MovieScheduleSeat> ds = new ArrayList<>();
        ConnectDB.getInstance().connect();
        try (Connection con = ConnectDB.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM MovieScheduleSeat")) {

            while (rs.next()) {
                String schedID  = rs.getString("scheduleID");
                String seatID   = rs.getString("seatID");
                boolean avail   = rs.getBoolean("isAvailable");

                MovieSchedule schedule = schedMap.get(schedID);
                Seat seat               = seatMap.get(seatID);

                MovieScheduleSeat obj = new MovieScheduleSeat(schedule, seat, avail);
                ds.add(obj);
            }
        }
        return ds;
    }
}
