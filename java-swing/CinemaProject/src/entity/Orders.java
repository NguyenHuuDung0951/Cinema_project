package entity;

import java.time.LocalDate;
import java.util.Objects;

public class Orders {
    private String orderID;
    private LocalDate orderDate;
    private double totalPrice;
    private Employee employee; // FK → Employee
    private Voucher voucher;   // FK → Voucher

    public Orders(String orderID,
                 LocalDate orderDate,
                 double totalPrice,
                 Employee employee,
                 Voucher voucher) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.employee = employee;
        this.voucher = voucher;
    }

    public String getOrderID() {
        return orderID;
    }
    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Employee getEmployee() {
        return employee;
    }
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Voucher getVoucher() {
        return voucher;
    }
    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Orders)) return false;
        Orders other = (Orders) o;
        return Objects.equals(orderID, other.orderID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderID);
    }
}
