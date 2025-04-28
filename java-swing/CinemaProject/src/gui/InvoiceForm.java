package gui;

import entity.CartItem;
import model.BookingData;
import service.PaymentService;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

public class InvoiceForm extends JFrame {
    private final BookingData bookingData;
    private JLabel lblTotalTicket, lblTotalFood, lblDiscount, lblVat, lblTotalFinal;

    public InvoiceForm(BookingData bookingData) {
        this.bookingData = bookingData;
        initComponents();
    }

    private void initComponents() {
        setTitle("THÔNG TIN HÓA ĐƠN");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel lblHeader = new JLabel("THÔNG TIN HÓA ĐƠN", JLabel.CENTER);
        lblHeader.setFont(new Font("Arial", Font.BOLD, 22));
        add(lblHeader, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(1, 3));

        contentPanel.add(createMoviePanel());
        contentPanel.add(createFoodPanel());
        contentPanel.add(createPaymentPanel());

        add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel createMoviePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.LIGHT_GRAY));
        JPanel panelTemp = new JPanel();
        panelTemp.setLayout(new BoxLayout(panelTemp, BoxLayout.Y_AXIS));
        JPanel panelHeader = new JPanel();
        panelHeader.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel lblHeader = new JLabel("Phim");
        lblHeader.setFont(new Font("Arial", Font.BOLD, 18));
        panelHeader.add(lblHeader);
        panelTemp.add(panelHeader);

        JPanel inner = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;

        JLabel lblPoster = new JLabel();
        ImageIcon icon = new ImageIcon("src/image/avenger.jpg");
        Image img = icon.getImage().getScaledInstance(120, 160, Image.SCALE_SMOOTH);
        lblPoster.setIcon(new ImageIcon(img));
        gbc.gridx = 0; gbc.gridy = row++;
        gbc.gridwidth = 2;
        inner.add(lblPoster, gbc);

        gbc.gridwidth = 1;
        JLabel lblTitle = new JLabel(bookingData.getMovieName());
        lblTitle.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridy = row++;
        inner.add(lblTitle, gbc);

        inner.add(new JLabel("Ngày chiếu: " + bookingData.getShowDate()), gbc = nextRow(gbc, row++));
        inner.add(new JLabel("Giờ chiếu: " + bookingData.getShowTime()), gbc = nextRow(gbc, row++));
        inner.add(new JLabel("Phòng: " + bookingData.getRoomName()), gbc = nextRow(gbc, row++));
        inner.add(new JLabel("Ghế: " + bookingData.getSelectedSeats()), gbc = nextRow(gbc, row++));

        panelTemp.add(inner);
        JPanel panelTotal = new JPanel();
        panelTotal.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelTotal.add(new JLabel("Tổng tiền vé: " + formatVND(bookingData.getTicketTotal())));
        panel.add(panelTemp, BorderLayout.NORTH);
        panel.add(panelTotal, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createFoodPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.LIGHT_GRAY));
        JPanel panelTemp = new JPanel();
        panelTemp.setLayout(new BoxLayout(panelTemp, BoxLayout.Y_AXIS));
        JPanel panelHeader = new JPanel();
        panelHeader.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel lblHeader = new JLabel("Đồ ăn & uống", JLabel.CENTER);
        lblHeader.setFont(new Font("Arial", Font.BOLD, 18));
        panelHeader.add(lblHeader);
        panelTemp.add(panelHeader);

        JPanel inner = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;
        for (CartItem item : bookingData.getCartItems()) {
            gbc.gridx = 0; gbc.gridy = row;
            ImageIcon icon = new ImageIcon("src/image/" + item.getProduct().getProductID().toLowerCase() + ".jpg");
            JLabel lblImg = new JLabel(new ImageIcon(icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
            inner.add(lblImg, gbc);

            gbc.gridx = 1;
            JLabel lblInfo = new JLabel(item.getProduct().getProductName() + " x" + item.getQuantity());
            inner.add(lblInfo, gbc);

            row++;
        }

        gbc.gridx = 0; gbc.gridy = row;
        gbc.gridwidth = 2;
//        inner.add(new JLabel("Tổng: " + formatVND(bookingData.getProductTotal())), gbc);
        JPanel panelTotal = new JPanel();
        panelTotal.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelTotal.add(new JLabel("Tổng: " + formatVND(bookingData.getProductTotal())));
        panelTemp.add(inner);
        panel.add(panelTemp, BorderLayout.NORTH);
        panel.add(panelTotal, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createPaymentPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel panelTemp = new JPanel();
        panelTemp.setLayout(new BoxLayout(panelTemp, BoxLayout.Y_AXIS));
        JPanel panelHeader = new JPanel();
        panelHeader.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel lblHeader = new JLabel("Thanh toán", JLabel.CENTER);
        lblHeader.setFont(new Font("Arial", Font.BOLD, 18));
        panelHeader.add(lblHeader);
        panelTemp.add(panelHeader);

        JPanel inner = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;
        lblTotalTicket = new JLabel();
        gbc.gridx = 0; gbc.gridy = row++;
        inner.add(lblTotalTicket, gbc);

        lblTotalFood = new JLabel();
        gbc.gridy = row++;
        inner.add(lblTotalFood, gbc);

        lblDiscount = new JLabel();
        gbc.gridy = row++;
        inner.add(lblDiscount, gbc);

        lblVat = new JLabel();
        gbc.gridy = row++;
        inner.add(lblVat, gbc);

        lblTotalFinal = new JLabel();
        lblTotalFinal.setFont(new Font("Arial", Font.BOLD, 18));
        lblTotalFinal.setForeground(Color.RED);
        gbc.gridy = row++;
        inner.add(lblTotalFinal, gbc);

        panelTemp.add(inner);
        JPanel panelPay = new JPanel();
        JButton btnPay = new JButton("Thanh toán");
        btnPay.setPreferredSize(new Dimension(170, 40));
        btnPay.setFont(new Font("Arial", Font.BOLD, 20));
        btnPay.setBackground(new Color(0x1A237E));
        btnPay.setForeground(Color.WHITE);
        btnPay.setFocusPainted(false);
        btnPay.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPay.add(btnPay);
        panel.add(panelPay, BorderLayout.SOUTH);
        panel.add(panelTemp, BorderLayout.NORTH);
        btnPay.addActionListener(e -> {
            PaymentService.handlePaymentButtonClick();
        });

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
        double totalBeforeDiscount = totalTicket + totalFood;

        double discountPercent = util.PromotionUtil.findBestDiscount(totalBeforeDiscount);
        double totalAfterDiscount = totalBeforeDiscount * (1 - discountPercent);
        double vat = totalAfterDiscount * 0.1;

        lblTotalTicket.setText("Tổng tiền vé: " + formatVND(totalTicket));
        lblTotalFood.setText("Tổng tiền đồ ăn & uống: " + formatVND(totalFood));
        lblDiscount.setText("Khuyến mãi: " + (int)(discountPercent * 100) + "%");
        lblVat.setText("VAT: " + formatVND(vat));
        lblTotalFinal.setText("Tổng tiền: " + formatVND(totalAfterDiscount + vat));
    }

    private String formatVND(double amount) {
        NumberFormat nf = NumberFormat.getNumberInstance(new Locale("vi", "VN"));
        return nf.format(amount) + " VND";
    }
}
