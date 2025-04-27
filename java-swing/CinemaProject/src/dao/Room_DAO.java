package dao;

import connectDB.ConnectDB;
import entity.Room;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Room_DAO {
    public Room_DAO(){}

    public ArrayList<Room> getalltbRoom() {
        ArrayList<Room> dsRoom = new ArrayList<>();
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();
            String sql = "Select * from Room";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                String roomID = rs.getString(1);
                String roomName = rs.getString(2);
                int seats = rs.getInt(3);
                Room obj = new Room(roomID, roomName, seats);
                dsRoom.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsRoom;
    }
    public Room getRoomByID(String roomID) {
        Room room = null;
        try {
            Connection con = ConnectDB.getInstance().getConnection();
            String sql = "SELECT * FROM Room WHERE room = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, roomID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                room = new Room(
                    rs.getString("room"),
                    rs.getString("roomName"),
                    rs.getInt("numberOfSeats")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return room;
    }
}
