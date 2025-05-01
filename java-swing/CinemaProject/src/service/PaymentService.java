package service;

import dao.*;
import entity.*;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import model.BookingData;
import util.SeatPriceUtil;

import javax.swing.*;
import java.sql.SQLException;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import util.PDFGenerator;
import util.PromotionUtil;

public class PaymentService {

    public static boolean processPayment() {
        try {
            BookingData bd = BookingData.getInstance();
            double totalTicket = bd.getTicketTotal();
            double totalFood = bd.getProductTotal();
            double before = totalTicket + totalFood;
            Voucher best = PromotionUtil.findBestVoucher(before);
            double discountPct = best != null ? best.getValueVoucherAsDouble() : 0;
            double after = before * (1 - discountPct);
            double vat = after * 0.1;
            double total = after + vat;
            // 1. Tạo Order
            Orders order = new Orders(null, LocalDate.now(),
                    total,
                    new Employee("EM001"),
                    bd.getVoucherID() != null
                    ? new Voucher(bd.getVoucherID())
                    : null);
            new Orders_DAO().addOrder(order);

            // 2. Ghi TicketDetail cho mỗi ghế
            TicketDetail_DAO ticketDao = new TicketDetail_DAO();
            Seat_DAO seatDao = new Seat_DAO();

            // parse ngày + giờ
            LocalDate date = LocalDate.parse(bd.getShowDate());
            LocalTime time = LocalTime.parse(bd.getShowTime());
            LocalDateTime dateTime = LocalDateTime.of(date, time);
            List<TicketDetail> tickets = new ArrayList<>();
            MovieScheduleSeat_DAO mssDao = new MovieScheduleSeat_DAO();
            for (String seatLoc : bd.getSelectedSeats().split("\\s*,\\s*")) {
                // lấy Seat
                Seat seat = seatDao.getSeatByLocation(seatLoc);
                if (seat == null) {
                    throw new SQLException("Không tìm thấy ghế " + seatLoc);
                }
                String seatID = seat.getSeatID();
                // tính giá theo loại
                String typeID = seat.getSeatType().getSeatTypeID();
                double pricePerSeat = SeatPriceUtil.getPriceByType(typeID);

                TicketDetail ticket = new TicketDetail(
                        null,
                        new Movie(bd.getMovieID()),
                        dateTime,
                        seat,
                        new Room(bd.getRoomID()),
                        pricePerSeat
                );
                ticketDao.addTicket(ticket);
                tickets.add(ticket);
                mssDao.updateAvailability(
                        bd.getScheduleID(),
                        seat.getSeatID(),
                        false // đã bán
                );
            }
            bd.setLastTickets(tickets);
            String qrContent = order.getOrderID();
            bd.generateQrCode(qrContent, 150);
            // 3. Ghi OrderDetail cho sản phẩm
            OrderDetail_DAO odDao = new OrderDetail_DAO();
            MovieSchedule sched = new MovieSchedule(bd.getScheduleID());
            for (CartItem ci : bd.getCartItems()) {
                OrderDetail od = new OrderDetail(
                        null,
                        order,
                        new Product(ci.getProduct().getProductID()),
                        sched,
                        ci.getQuantity()
                );
                odDao.addOrderDetail(od);
            }

            JOptionPane.showMessageDialog(null, "Thanh toán thành công!");
            String invoicePath = "export/HD_" + order.getOrderID() + ".pdf";
            String ticketFolder = "export/VE_" + order.getOrderID();
            PDFGenerator.generateInvoicePDF(invoicePath);
            PDFGenerator.generateTicketPDFs(ticketFolder);
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                try {
                    File invoiceFile = new File(invoicePath);
                    if (invoiceFile.exists()) {
                        desktop.open(invoiceFile);
                    }

                    // 4b. Mở tất cả ticket PDF trong thư mục
                    File ticketDir = new File(ticketFolder);
                    if (ticketDir.exists() && ticketDir.isDirectory()) {
                        for (File f : ticketDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".pdf"))) {
                            desktop.open(f);
                        }
                    }
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                    // Nếu muốn: thông báo lỗi mở file
                }
            }
            bd.reset();
            // Đường dẫn thư mục sẽ lưu hóa đơn & vé

            return true;

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Thanh toán thất bại: " + ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static void handlePaymentButtonClick() {
        processPayment();
    }
}
