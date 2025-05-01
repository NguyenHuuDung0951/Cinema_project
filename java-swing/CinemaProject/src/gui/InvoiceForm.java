package gui;

import entity.CartItem;
import entity.Voucher;
import model.BookingData;
import service.PaymentService;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;
import util.PromotionUtil;

/**
 * InvoiceForm: hiển thị chi tiết hóa đơn và xử lý thanh toán
 */
public class InvoiceForm extends JFrame {

    private final BookingData bookingData;
    private JLabel lblTotalTicket, lblTotalFood, lblDiscount, lblVat, lblTotalFinal;

    public InvoiceForm() {
        // Dùng singleton BookingData để lấy thông tin
        this.bookingData = BookingData.getInstance();
        initComponents();
    }

    private void initComponents() {
        setTitle("THÔNG TIN HÓA ĐƠN");
        setSize(800, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel header = new JLabel("THÔNG TIN HÓA ĐƠN", JLabel.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 22));
        add(header, BorderLayout.NORTH);

        JPanel content = new JPanel(new GridLayout(1, 3));
        content.add(createMoviePanel());
        content.add(createFoodPanel());
        content.add(createPaymentPanel());
        add(content, BorderLayout.CENTER);
    }

    private JPanel createMoviePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.LIGHT_GRAY));

        JPanel north = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel lbl = new JLabel("Phim");
        lbl.setFont(new Font("Arial", Font.BOLD, 18));
        north.add(lbl);
        panel.add(north, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        int row = 0;

        // Poster
        JLabel lblPoster = new JLabel();
        String posterPath = bookingData.getPosterPath();
        if (posterPath != null) {
            ImageIcon icon = new ImageIcon(getClass().getResource(posterPath));
            Image img = icon.getImage().getScaledInstance(120, 160, Image.SCALE_SMOOTH);
            lblPoster.setIcon(new ImageIcon(img));
        }
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        center.add(lblPoster, gbc);
        gbc.gridwidth = 1;

        // Thông tin phim
        gbc.gridx = 0;
        gbc.gridy = row;
        center.add(new JLabel("Tiêu đề: " + bookingData.getMovieName()), gbc);
        gbc.gridy = ++row;
        center.add(new JLabel("Ngày chiếu: " + bookingData.getShowDate()), gbc);
        gbc.gridy = ++row;
        center.add(new JLabel("Giờ chiếu: " + bookingData.getShowTime()), gbc);
        gbc.gridy = ++row;
        center.add(new JLabel("Phòng: " + bookingData.getRoomName()), gbc);
        gbc.gridy = ++row;
        center.add(new JLabel("Ghế: " + bookingData.getSelectedSeats()), gbc);

        panel.add(center, BorderLayout.CENTER);

        JPanel south = new JPanel(new FlowLayout(FlowLayout.LEFT));
        south.add(new JLabel("Tổng vé: " + formatVND(bookingData.getTicketTotal())));
        panel.add(south, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createFoodPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.LIGHT_GRAY));

        JPanel north = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel lbl = new JLabel("Đồ ăn & uống");
        lbl.setFont(new Font("Arial", Font.BOLD, 18));
        north.add(lbl);
        panel.add(north, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        int row = 0;
        for (CartItem item : bookingData.getCartItems()) {
            JLabel imgLbl = new JLabel(new ImageIcon(
                    new ImageIcon(getClass().getResource("/image/" + item.getProduct().getProductID().toLowerCase() + ".jpg"))
                            .getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
            gbc.gridx = 0;
            gbc.gridy = row;
            center.add(imgLbl, gbc);
            gbc.gridx = 1;
            center.add(new JLabel(item.getProduct().getProductName() + " x" + item.getQuantity()), gbc);
            row++;
        }
        panel.add(center, BorderLayout.CENTER);

        JPanel south = new JPanel(new FlowLayout(FlowLayout.LEFT));
        south.add(new JLabel("Tổng món: " + formatVND(bookingData.getProductTotal())));
        panel.add(south, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createPaymentPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel north = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel lbl = new JLabel("Thanh toán");
        lbl.setFont(new Font("Arial", Font.BOLD, 18));
        north.add(lbl);
        panel.add(north, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        int row = 0;
        lblTotalTicket = new JLabel();
        gbc.gridy = row++;
        center.add(lblTotalTicket, gbc);
        lblTotalFood = new JLabel();
        gbc.gridy = row++;
        center.add(lblTotalFood, gbc);
        lblDiscount = new JLabel();
        gbc.gridy = row++;
        center.add(lblDiscount, gbc);
        lblVat = new JLabel();
        gbc.gridy = row++;
        center.add(lblVat, gbc);
        lblTotalFinal = new JLabel();
        lblTotalFinal.setFont(new Font("Arial", Font.BOLD, 18));
        lblTotalFinal.setForeground(Color.RED);
        gbc.gridy = row++;
        center.add(lblTotalFinal, gbc);
        panel.add(center, BorderLayout.CENTER);

        JPanel south = new JPanel();
        JButton btnPay = new JButton("Thanh toán");
        btnPay.setPreferredSize(new Dimension(170, 40));
        btnPay.setFont(new Font("Arial", Font.BOLD, 20));
        btnPay.setBackground(new Color(0x1A237E));
        btnPay.setForeground(Color.WHITE);
        btnPay.setFocusPainted(false);
        btnPay.addActionListener(e -> {
            boolean success = PaymentService.processPayment();
            if (success) {
                this.dispose();
            }
        });
        south.add(btnPay);
        panel.add(south, BorderLayout.SOUTH);

        calculatePayment();
        return panel;
    }

    private GridBagConstraints nextRow(GridBagConstraints gbc, int row) {
        gbc.gridy = row;
        return gbc;
    }

    private void calculatePayment() {
        double totalTicket = bookingData.getTicketTotal();
        double totalFood = bookingData.getProductTotal();
        double before = totalTicket + totalFood;
        Voucher best = PromotionUtil.findBestVoucher(before);
        double discountPct = best != null ? best.getValueVoucherAsDouble() : 0;
        double discountAmount = before * discountPct;
        bookingData.setDiscountAmount(discountAmount);
        bookingData.setVoucherID(best != null ? best.getVoucherID() : null);
        double after = before - discountAmount;
        double vat = after * 0.1;

        lblTotalTicket.setText("Tổng vé: " + formatVND(totalTicket));
        lblTotalFood.setText("Tổng món: " + formatVND(totalFood));
        lblDiscount.setText("Giảm giá: " + (int) (discountPct * 100) + "%");
        lblVat.setText("VAT (10%): " + formatVND(vat));
        lblTotalFinal.setText("Thành tiền: " + formatVND(after + vat));
    }

    private String formatVND(double amount) {
        NumberFormat nf = NumberFormat.getNumberInstance(new Locale("vi", "VN"));
        return nf.format(amount) + " VND";
    }
}
