package dao;

import connectDB.ConnectDB;
import entity.Product;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Product_DAO {
    public Product_DAO(){}

    public ArrayList<Product> getalltbProduct() {
        ArrayList<Product> dsProduct = new ArrayList<>();
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();
            String sql = "Select * from Product";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                String productID = rs.getString(1);
                String productName = rs.getNString(2);
                int quantity = rs.getInt(3);
                String productType = rs.getString(4);
                double price = rs.getDouble(5);
                Product obj = new Product(productID, productName, quantity, productType, price);
                dsProduct.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsProduct;
    }
}