/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import connectDB.ConnectDB;
import entity.Voucher;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author khang
 */
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
}

