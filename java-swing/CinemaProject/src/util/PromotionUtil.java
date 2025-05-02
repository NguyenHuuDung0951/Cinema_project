package util;

import java.time.LocalDate;
import java.util.ArrayList;

import dao.Voucher_DAO;
import entity.Voucher;

public class PromotionUtil {

    static class Promotion {

        private String promoID;
        private LocalDate startDate;
        private LocalDate endDate;
        private double minimumTotal;
        private double discountPercent;

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
