package entity;

import java.time.LocalDate;

public class Voucher {
    private String voucherID ;
    private String voucherName;
    private LocalDate startDate;
    private LocalDate endDate;
    private double minimumPrice ;
    private String valueVoucher;

    public Voucher(String voucherID) {
        this.voucherID = voucherID;
    }

    
    public Voucher(String voucherID, String voucherName, LocalDate startDate, LocalDate endDate, double minimumPrice, String valueVoucher) {
        this.voucherID = voucherID;
        this.voucherName = voucherName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.minimumPrice = minimumPrice;
        this.valueVoucher = valueVoucher;
    }

    public String getVoucherID() {
        return voucherID;
    }

    public void setVoucherID(String voucherID) {
        this.voucherID = voucherID;
    }

    public String getVoucherName() {
        return voucherName;
    }

    public void setVoucherName(String voucherName) {
        this.voucherName = voucherName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public double getMinimumPrice() {
        return minimumPrice;
    }

    public void setMinimumPrice(double minimumPrice) {
        this.minimumPrice = minimumPrice;
    }

    public String getValueVoucher() {
        return valueVoucher;
    }

    public void setValueVoucher(String valueVoucher) {
        this.valueVoucher = valueVoucher;
    }

    @Override
    public String toString() {
        return "Voucher{" + "voucherID=" + voucherID + ", voucherName=" + voucherName + ", startDate=" + startDate + ", endDate=" + endDate + ", minimumPrice=" + minimumPrice + ", valueVoucher=" + valueVoucher + '}';
    }
    public double getValueVoucherAsDouble() {
        return Double.parseDouble(valueVoucher.replace("%", "")) / 100.0;
    }

}
