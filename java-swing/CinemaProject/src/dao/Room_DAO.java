package dao;

import connectDB.ConnectDB;
import entity.Room;
import java.sql.Connection;
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
                String roomName = rs.getNString(2);
                int seats = rs.getInt(3);
                Room obj = new Room(roomID, roomName, seats);
                dsRoom.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsRoom;
    }
}
