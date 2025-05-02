package dao;

import connectDB.ConnectDB;
import entity.Seat;
import entity.Room;
import entity.SeatType;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Seat_DAO {

    public Seat_DAO() {
    }

    public ArrayList<Seat> getalltbSeat() throws SQLException {
        
        Map<String, Room> roomMap = new Room_DAO()
                .getalltbRoom()
                .stream()
                .collect(Collectors.toMap(Room::getRoomID, Function.identity()));
        Map<String, SeatType> typeMap = new SeatType_DAO()
                .getalltbSeatType()
                .stream()
                .collect(Collectors.toMap(SeatType::getSeatTypeID, Function.identity()));

        ArrayList<Seat> ds = new ArrayList<>();
        ConnectDB.getInstance().connect();
        try (Connection con = ConnectDB.getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery("SELECT * FROM Seat")) {

            while (rs.next()) {
                String seatID = rs.getString("seatID");
                String location = rs.getString("location");
                String roomID = rs.getString("room");
                String typeID = rs.getString("seatTypeID");

                Room room = roomMap.get(roomID);
                SeatType seatType = typeMap.get(typeID);

                Seat obj = new Seat(seatID, location, room, seatType);
                ds.add(obj);
            }
        }
        return ds;
    }

    public Seat getSeatByID(String seatID) throws SQLException {
        for (Seat s : getalltbSeat()) {
            if (s.getSeatID().equals(seatID)) {
                return s;
            }
        }
        return null;
    }

    public Seat getSeatByLocation(String location) throws SQLException {
        for (Seat s : getalltbSeat()) {
            if (s.getLocation().equals(location)) {
                return s;
            }
        }
        return null;
    }
}
