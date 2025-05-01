package component;

import connectDB.ConnectDB;
import dao.Product_DAO;
import entity.Product;
import gui.WrapLayout;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class SanPham_DoAn_1 extends JPanel {

    private JPanel pnlFood;
    private JTextField txtSearch;
    private JButton btnSearch;
    private JButton btnAdd;

    public SanPham_DoAn_1(int index) {
        initComponents();
        try {
            ConnectDB.getInstance().connect();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        loadProducts("");
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(0xF5F5F5));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        topPanel.setBackground(getBackground());
        txtSearch = new JTextField(20);
        btnSearch = new JButton("Tìm");
        btnAdd = new JButton("Thêm SP");
        topPanel.add(txtSearch);
        topPanel.add(btnSearch);
        topPanel.add(btnAdd);
        add(topPanel, BorderLayout.NORTH);

        pnlFood = new JPanel(new WrapLayout(FlowLayout.LEFT, 20, 20));
        pnlFood.setBackground(getBackground());
        JScrollPane scroll = new JScrollPane(pnlFood);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(getBackground());
        add(scroll, BorderLayout.CENTER);

        btnSearch.addActionListener(e -> loadProducts(txtSearch.getText().trim()));
        btnAdd.addActionListener(e -> {
            JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Thêm sản phẩm", true);
            dialog.setContentPane(new ThemSanPham());
            dialog.pack(); // hoặc dialog.setSize(width, height);
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        });

    }

    private void loadProducts(String keyword) {
        pnlFood.removeAll();
        Product_DAO dao = new Product_DAO();
        ArrayList<Product> list = dao.getalltbProduct();
        NumberFormat nf = NumberFormat.getIntegerInstance(new Locale("vi", "VN"));
        for (Product p : list) {
            if ((p.getProductType().equalsIgnoreCase("Đồ ăn")) && (keyword.isEmpty() || p.getProductName().toLowerCase().contains(keyword.toLowerCase()))) {
                pnlFood.add(createProductCard(p, nf));
            }
        }
        pnlFood.revalidate();
        pnlFood.repaint();
    }

    private JPanel createProductCard(Product p, NumberFormat nf) {
        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(300, 280));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel lblImg = new JLabel();
        String path = p.getPosterPath();
        URL url = getClass().getResource(path);
        ImageIcon icon = new ImageIcon(url);
        Image img = icon.getImage().getScaledInstance(140, 140, Image.SCALE_SMOOTH);
        lblImg.setIcon(new ImageIcon(img));
        lblImg.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblName = new JLabel("<html><div style='text-align:center;'>" + p.getProductName() + "</div></html>");
        lblName.setFont(lblName.getFont().deriveFont(Font.BOLD, 14f));
        lblName.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblName.setMaximumSize(new Dimension(150, 40));

        JLabel lblQty = new JLabel("Số lượng: " + p.getQuantity());
        lblQty.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblQty.setFont(lblQty.getFont().deriveFont(12f));

        JLabel lblPrice = new JLabel(nf.format(p.getPrice()) + " VND");
        lblPrice.setFont(lblPrice.getFont().deriveFont(Font.BOLD, 13f));
        lblPrice.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        btnPanel.setBackground(Color.WHITE);
        JButton btnDelete = new JButton("Xóa");
        JButton btnUpdate = new JButton("Cập Nhật");
        Dimension btnDim = new Dimension(80, 32);
        for (JButton b : new JButton[]{btnDelete, btnUpdate}) {
            b.setPreferredSize(btnDim);
            b.setFont(b.getFont().deriveFont(11f));
            btnPanel.add(b);
        }

        card.add(lblImg);
        card.add(Box.createVerticalStrut(8));
        card.add(lblName);
        card.add(Box.createVerticalStrut(6));
        card.add(lblQty);
        card.add(Box.createVerticalStrut(4));
        card.add(lblPrice);
        card.add(Box.createVerticalGlue());
        card.add(btnPanel);

        return card;
    }
}
