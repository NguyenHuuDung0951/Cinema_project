package gui;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import dao.Product_DAO;
import entity.Product;
import model.BookingData;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ProductOrderForm extends JFrame {
    private JPanel productPanel;
    private JButton btnTatCa, btnDoAn, btnThucUong;
    private CartPanel cartPanel;
    private BookingData bookingData;

    public ProductOrderForm(BookingData bookingData) {
        this.bookingData = bookingData;
        initComponents();
    }

    private void initComponents() {
        setTitle("Chọn đồ ăn & thức uống");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Top filter buttons
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnTatCa = new JButton("Tất cả");
        btnDoAn = new JButton("Đồ ăn");
        btnThucUong = new JButton("Thức uống");

        btnTatCa.putClientProperty("FlatLaf.style", "focusWidth:0;focusColor:#00000000;");
        btnDoAn.putClientProperty("FlatLaf.style", "focusWidth:0;");
        btnThucUong.putClientProperty("FlatLaf.style", "focusWidth:0;");

        filterPanel.add(btnTatCa);
        filterPanel.add(btnDoAn);
        filterPanel.add(btnThucUong);

        add(filterPanel, BorderLayout.NORTH);

        // Center product panel (scrollable)
        productPanel = new JPanel();
        productPanel.setLayout(new WrapLayout(FlowLayout.LEFT, 15, 15));
        JScrollPane scrollPane = new JScrollPane(productPanel);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);

        // Right cart panel
        cartPanel = new CartPanel(bookingData, this);
        cartPanel.setPreferredSize(new Dimension(280, 0));
        add(cartPanel, BorderLayout.EAST);

        // Load all products initially
        loadProducts("Tất cả");

        // Filter actions
        btnTatCa.addActionListener(e -> loadProducts("Tất cả"));
        btnDoAn.addActionListener(e -> loadProducts("Đồ ăn"));
        btnThucUong.addActionListener(e -> loadProducts("Thức uống"));
    }

    private void loadProducts(String filterType) {
        productPanel.removeAll();
        Product_DAO dao = new Product_DAO();
        ArrayList<Product> list = dao.getalltbProduct();

        for (Product p : list) {
            if (!filterType.equals("Tất cả") && !p.getProductType().equalsIgnoreCase(filterType))
                continue;
            productPanel.add(createProductCard(p));
        }

        productPanel.revalidate();
        productPanel.repaint();
    }

    private JPanel createProductCard(Product p) {
        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(160, 230));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        card.setBackground(Color.WHITE);
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel lblImg = new JLabel();
        String imgPath = "src/image/" + p.getProductID().toLowerCase() + ".jpg";
        ImageIcon icon = new ImageIcon(imgPath);
        Image scaled = icon.getImage().getScaledInstance(130, 130, Image.SCALE_SMOOTH);
        lblImg.setIcon(new ImageIcon(scaled));
        lblImg.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblName = new JLabel("<html><div style='text-align:center;'>" + p.getProductName() + "</div></html>", JLabel.CENTER);
        lblName.setFont(lblName.getFont().deriveFont(Font.BOLD, 14f));
        lblName.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblQty = new JLabel("Số lượng: " + p.getQuantity(), JLabel.CENTER);
        lblQty.setFont(lblQty.getFont().deriveFont(12f));
        lblQty.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblPrice = new JLabel("Giá: " + formatVND(p.getPrice()), JLabel.CENTER);
        lblPrice.setFont(lblPrice.getFont().deriveFont(Font.BOLD, 12f));
        lblPrice.setForeground(new Color(0, 102, 204));
        lblPrice.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(Box.createVerticalStrut(10));
        card.add(lblImg);
        card.add(Box.createVerticalStrut(8));
        card.add(lblName);
        card.add(lblQty);
        card.add(lblPrice);

        card.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cartPanel.addToCart(p);
            }
        });

        return card;
    }

    private String formatVND(double amount) {
        NumberFormat nf = NumberFormat.getNumberInstance(new Locale("vi", "VN"));
        return nf.format(amount) + " VND";
    }

    public static void main(String[] args) {
        FlatMacLightLaf.setup();
        SwingUtilities.invokeLater(() -> {
            BookingData bookingData = new BookingData();  // Dummy
            new ProductOrderForm(bookingData).setVisible(true);
        });
    }
}
