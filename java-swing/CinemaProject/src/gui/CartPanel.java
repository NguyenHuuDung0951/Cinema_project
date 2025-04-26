package gui;

import entity.Product;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CartPanel extends JPanel {
    private final JPanel itemsPanel;
    private final JLabel lblTotal;
    private final Map<Product, Integer> cartItems = new HashMap<>();

    public CartPanel() {
        setLayout(new BorderLayout(10, 10));
        setPreferredSize(new Dimension(250, 0));
        setBackground(Color.WHITE);

        itemsPanel = new JPanel();
        itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));
        itemsPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(itemsPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottomPanel.setBackground(Color.WHITE);

        JButton btnClear = new JButton("Xóa tất cả");
        btnClear.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnClear.addActionListener(e -> {
            cartItems.clear();
            refreshCart();
        });

        lblTotal = new JLabel("Tổng tiền: 0 VND", JLabel.CENTER);
        lblTotal.setForeground(Color.RED);
        lblTotal.setFont(new Font("Arial", Font.BOLD, 14));
        lblTotal.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTotal.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JButton btnContinue = new JButton("Tiếp tục");
        btnContinue.setAlignmentX(Component.CENTER_ALIGNMENT);

        bottomPanel.add(btnClear);
        bottomPanel.add(lblTotal);
        bottomPanel.add(btnContinue);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void addToCart(Product p) {
        cartItems.put(p, cartItems.getOrDefault(p, 0) + 1);
        refreshCart();
    }

    private void refreshCart() {
        itemsPanel.removeAll();
        double total = 0;

        for (Map.Entry<Product, Integer> entry : cartItems.entrySet()) {
            Product p = entry.getKey();
            int quantity = entry.getValue();

            JPanel itemPanel = new JPanel(new GridBagLayout());
            itemPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
            itemPanel.setBackground(Color.WHITE);
            itemPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.anchor = GridBagConstraints.WEST;

            // Ảnh sản phẩm
            JLabel imgLabel = new JLabel();
            imgLabel.setPreferredSize(new Dimension(40, 40));
            String imgPath = "src/image/" + p.getProductID().toLowerCase() + ".jpg";
            File imgFile = new File(imgPath);
            if (imgFile.exists()) {
                ImageIcon icon = new ImageIcon(imgPath);
                Image scaled = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                imgLabel.setIcon(new ImageIcon(scaled));
            }

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridheight = 2;
            itemPanel.add(imgLabel, gbc);

            // Tên và giá
            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
            infoPanel.setBackground(Color.WHITE);

            JLabel lblName = new JLabel(p.getProductName());
            lblName.setFont(new Font("Arial", Font.PLAIN, 12));

            JLabel lblPrice = new JLabel("Giá: " + formatVND(p.getPrice()));
            lblPrice.setFont(new Font("Arial", Font.PLAIN, 11));

            infoPanel.add(lblName);
            infoPanel.add(lblPrice);

            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.gridheight = 2;
            itemPanel.add(infoPanel, gbc);

            // Số lượng (JSpinner)
            JSpinner spnQty = new JSpinner(new SpinnerNumberModel(quantity, 1, 100, 1));
            spnQty.setPreferredSize(new Dimension(50, 25));
            spnQty.addChangeListener(e -> {
                cartItems.put(p, (int) spnQty.getValue());
                refreshCart();
            });

            gbc.gridx = 2;
            gbc.gridy = 0;
            gbc.gridheight = 1;
            gbc.anchor = GridBagConstraints.EAST;
            gbc.fill = GridBagConstraints.NONE;
            itemPanel.add(spnQty, gbc);

            // Nút xoá
            JButton btnRemove = new JButton("Xóa");
            btnRemove.addActionListener(e -> {
                cartItems.remove(p);
                refreshCart();
            });

            gbc.gridx = 2;
            gbc.gridy = 1;
            itemPanel.add(btnRemove, gbc);

            itemsPanel.add(itemPanel, 0);
            total += p.getPrice() * quantity;
        }

        lblTotal.setText("Tổng tiền: " + formatVND(total));
        itemsPanel.revalidate();
        itemsPanel.repaint();
        KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();

    }

    private String formatVND(double amount) {
        NumberFormat nf = NumberFormat.getNumberInstance(new Locale("vi", "VN"));
        return nf.format(amount) + " VND";
    }
}
