package dao;

import connectDB.ConnectDB;
import entity.Voucher;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class Voucher_DAO {
    public Voucher_DAO(){}
    
    public ArrayList<Voucher> getalltbVoucher() {
        ArrayList<Voucher> dsVoucher = new ArrayList<Voucher>();
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();
            String sql = "Select * from Voucher";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()){
                String voucherID = rs.getString(1);
                String voucherName = rs.getString(2);
                LocalDate startDate = rs.getDate(3).toLocalDate();
                LocalDate endDate = rs.getDate(4).toLocalDate();
                double minimumPrice = rs.getDouble(5);
                String valueVoucher = rs.getString(6);
                Voucher vc = new Voucher(voucherID, voucherName, startDate, endDate, minimumPrice, valueVoucher);
                dsVoucher.add(vc);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return dsVoucher;
    }
    public boolean insertVoucher(Voucher vc) {
    try {
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();

        String sql = "INSERT INTO Voucher (voucherID, voucherName, startDate, endDate, minimumPrice, valueVoucher) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, vc.getVoucherID());
        ps.setString(2, vc.getVoucherName());
        ps.setDate(3, java.sql.Date.valueOf(vc.getStartDate()));
        ps.setDate(4, java.sql.Date.valueOf(vc.getEndDate()));
        ps.setDouble(5, vc.getMinimumPrice());
        ps.setString(6, vc.getValueVoucher());

        int rowsInserted = ps.executeUpdate();
        return rowsInserted > 0; 
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
      public boolean deleteVoucher(String voucherID) {
        try {
            ConnectDB.getInstance();
            Connection con;
            con = ConnectDB.getConnection();
            String query = "DELETE FROM Voucher WHERE voucherID = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, voucherID);
            
            int result = stmt.executeUpdate();
            return result > 0; 
        } catch (SQLException e) {
            e.printStackTrace();
            return false; 
        }
    }
public boolean updateVoucher(Voucher voucher) {
        String sql = "UPDATE voucher SET voucherName = ?, startDate = ?, endDate = ?, minimumPrice = ?, valueVoucher = ? WHERE voucherID = ?";
        
        try (Connection conn = ConnectDB.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            java.sql.Date startDate = java.sql.Date.valueOf(voucher.getStartDate());
            java.sql.Date endDate = java.sql.Date.valueOf(voucher.getEndDate());
            
            stmt.setString(1, voucher.getVoucherName());
            stmt.setDate(2, startDate);
            stmt.setDate(3, endDate);
            stmt.setDouble(4, voucher.getMinimumPrice());
            stmt.setString(5, voucher.getValueVoucher());
            stmt.setString(6, voucher.getVoucherID());
            
            int rowsUpdated = stmt.executeUpdate();
            
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
public Voucher getVoucherByID(String voucherID) {
        Voucher voucher = null;
        String sql = "SELECT * FROM Voucher WHERE voucherID = ?";  

        try (Connection conn = ConnectDB.getConnection();  
             PreparedStatement ps = conn.prepareStatement(sql)) {
             
            ps.setString(1, voucherID);  
            ResultSet rs = ps.executeQuery();                      
            if (rs.next()) {
                voucher = new Voucher(
                    rs.getString("voucherID"),
                    rs.getString("voucherName"),
                    rs.getDate("startDate").toLocalDate(),
                    rs.getDate("endDate").toLocalDate(),
                    rs.getDouble("minimumPrice"),
                    rs.getString("valueVoucher")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return voucher;
    }
}

