package dao;

import connectDB.ConnectDB;
import entity.Account;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Account_DAO {
    public Account_DAO(){}

    public ArrayList<Account> getalltbAccount() {
        ArrayList<Account> dsAccount = new ArrayList<>();
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();
            String sql = "Select * from Account";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()){
                String accountID = rs.getString(1);
                String username = rs.getString(2);
                String password = rs.getString(3);
                Account obj = new Account(accountID, username, password);
                dsAccount.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsAccount;
    }
}