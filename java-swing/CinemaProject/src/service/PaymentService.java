// ===== PaymentService.java =====
package service;

import dao.OrderDetail_DAO;
import dao.Orders_DAO;
import dao.TicketDetail_DAO;
import entity.*;
import model.BookingData;

import javax.swing.*;
import java.time.LocalDate;

public class PaymentService {

    public static boolean processPayment() {
        try {
            BookingData booking = BookingData.getInstance();

            // Tạo employee giả định (vì hiện tại chưa login)
            Employee employee = new Employee("EM001", "Nhân viên mặc định", false, null, "", "", null);

            // Tạm không có voucher
            Voucher voucher = null;

            // 1. Insert đơn hàng mới
            Orders order = new Orders(null, LocalDate.now(), 0.0, employee, voucher);
            new Orders_DAO().addOrder(order);

            double totalOrderPrice = 0.0;

            // 2. Insert vé ghế
            String[] selectedSeats = booking.getSelectedSeats().split(", ");
            for (String seatName : selectedSeats) {
                Movie movie = new Movie(null, booking.getMovieName(), null, 0);
                Seat seat = new Seat(null, seatName, null, null);
                Room room = new Room(null, booking.getRoomName(), 0);

                TicketDetail ticket = new TicketDetail(null, movie,
                        LocalDate.parse(booking.getShowDate()), seat, room,
                        booking.getTicketTotal() / selectedSeats.length);
                new TicketDetail_DAO().addTicket(ticket);

                OrderDetail od = new OrderDetail(null, ticket, order, null, null, 1);
                new OrderDetail_DAO().addOrderDetail(od);

                totalOrderPrice += ticket.getTicketPrice();
            }

            // 3. Insert sản phẩm đồ ăn nước uống
            for (CartItem item : booking.getCartItems()) {
                Product product = item.getProduct();
                OrderDetail od = new OrderDetail(null, null, order, product, null, item.getQuantity());
                new OrderDetail_DAO().addOrderDetail(od);

                totalOrderPrice += product.getPrice() * item.getQuantity();
            }

            // 4. Update tổng tiền đơn hàng
            order.setTotalPrice(totalOrderPrice);
            new Orders_DAO().updateTotalPrice(order);

            // 5. Xuất hóa đơn PDF

            // 6. Reset dữ liệu BookingData
            BookingData.getInstance().reset();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void handlePaymentButtonClick() {
        boolean success = processPayment();
        if (success) {
            JOptionPane.showMessageDialog(null, "Thanh toán thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Thanh toán thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
