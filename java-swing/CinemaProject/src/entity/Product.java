package entity;

import java.util.Objects;

public class Product {

    private String productID;
    private String productName;
    private int quantity;
    private String productType;
    private double price;
    private String posterPath;
    public Product(String productID) {
        this.productID = productID;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public Product(String productID, String productName, int quantity,
            String productType, double price, String posterPath) {
        this.productID = productID;
        this.productName = productName;
        this.quantity = quantity;
        this.productType = productType;
        this.price = price;
        this.posterPath = posterPath;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        Product other = (Product) o;
        return Objects.equals(productID, other.productID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productID);
    }
}
