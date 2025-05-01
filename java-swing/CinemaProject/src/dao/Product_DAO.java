package dao;

import connectDB.ConnectDB;
import entity.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Product_DAO {

    public Product_DAO() {
    }

    public ArrayList<Product> getalltbProduct() {
        ArrayList<Product> dsProduct = new ArrayList<>();
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();
            String sql = "Select * from Product";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String productID = rs.getString(1);
                String productName = rs.getString(2);
                int quantity = rs.getInt(3);
                String productType = rs.getString(4);
                double price = rs.getDouble(5);
                String posterPath = rs.getString(6);
                Product obj = new Product(productID, productName, quantity, productType, price, posterPath);
                dsProduct.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsProduct;
    }

    public boolean addProduct(Product p) {
        String sql = "INSERT INTO Product (productID, productName, quantity, productType, price , posterPath) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = ConnectDB.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, p.getProductID());
            stmt.setString(2, p.getProductName());
            stmt.setInt(3, p.getQuantity());
            stmt.setString(4, p.getProductType());
            stmt.setDouble(5, p.getPrice());
            stmt.setString(6, p.getPosterPath());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
