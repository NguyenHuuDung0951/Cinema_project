package util;

import java.time.LocalDate;
import java.util.ArrayList;

import dao.Voucher_DAO;
import entity.Voucher;

public class PromotionUtil {

    // Inner class đại diện 1 khuyến mãi
    static class Promotion {

        private String promoID;
        private LocalDate startDate;
        private LocalDate endDate;
        private double minimumTotal; // Tổng tiền tối thiểu để áp dụng
        private double discountPercent; // Phần trăm khuyến mãi (VD: 0.3 = 30%)

        public Promotion(String promoID, LocalDate startDate, LocalDate endDate, double minimumTotal, double discountPercent) {
            this.promoID = promoID;
            this.startDate = startDate;
            this.endDate = endDate;
            this.minimumTotal = minimumTotal;
            this.discountPercent = discountPercent;
        }

        public boolean isValid(LocalDate today, double totalAmount) {
            return !today.isBefore(startDate) && !today.isAfter(endDate) && totalAmount >= minimumTotal;
        }

        public double getDiscountPercent() {
            return discountPercent;
        }
    }

    // Mock data: danh sách khuyến mãi hiện tại (hoặc sau này bạn đọc từ DB)
//    private static ArrayList<Promotion> promotionList = new ArrayList<>();
//
//    static {
//        promotionList.add(new Promotion("KM005", LocalDate.of(2024,10,1), LocalDate.of(2024,12,1), 300000, 0.30));
//        promotionList.add(new Promotion("KM008", LocalDate.of(2024,9,1), LocalDate.of(2024,11,30), 39000, 0.30));
//        promotionList.add(new Promotion("KM010", LocalDate.of(2024,11,1), LocalDate.of(2025,1,1), 59000, 0.35));
//        promotionList.add(new Promotion("KM011", LocalDate.of(2024,11,1), LocalDate.of(2025,12,1), 1000000, 0.35));
//    }
    /**
     * Trả về phần trăm khuyến mãi cao nhất có thể áp dụng
     */
    public static Voucher findBestVoucher(double totalPrice) {
        Voucher_DAO dao = new Voucher_DAO();
        ArrayList<Voucher> vouchers = dao.getalltbVoucher();
        Voucher best = null;
        for (Voucher v : vouchers) {
            if (totalPrice >= v.getMinimumPrice()) {
                if (best == null || v.getValueVoucherAsDouble() > best.getValueVoucherAsDouble()) {
                    best = v;
                }
            }
        }
        return best;
    }

    public static double findBestDiscount(double totalPrice) {
        Voucher best = findBestVoucher(totalPrice);
        return best != null ? best.getValueVoucherAsDouble() : 0;
    }

}
