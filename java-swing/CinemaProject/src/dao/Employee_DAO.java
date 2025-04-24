package dao;

import connectDB.ConnectDB;
import entity.Employee;
import entity.Account;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class Employee_DAO {
    public Employee_DAO(){}

    public ArrayList<Employee> getalltbEmployee() {
        ArrayList<Employee> dsEmployee = new ArrayList<>();
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();
            String sql = "Select * from Employee";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                String employeeID = rs.getString(1);
                String fullName = rs.getString(2);
                boolean gender = rs.getBoolean(3);
                LocalDate dob = rs.getDate(4).toLocalDate();
                String phone = rs.getString(5);
                String email = rs.getString(6);
                String accID = rs.getString(7);
                Employee obj = new Employee(employeeID, fullName, gender, dob, phone, email, null);
                // TODO: load Account object for emp.setAccount(...)
                dsEmployee.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsEmployee;
    }
}