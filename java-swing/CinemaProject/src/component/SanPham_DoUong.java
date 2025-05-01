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

public class SanPham_DoUong extends JPanel {

    private JPanel pnlFood;
    private JTextField txtSearch;
    private JButton btnSearch;
    private JButton btnAdd;

    public SanPham_DoUong(int index) {
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
        txtSearch = new JTextField(80);
         btnSearch = new JButton("Tìm");
        btnSearch.setBackground(Color.red);
        btnAdd = new JButton("Thêm SP");
        btnAdd.setBackground(Color.green);
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

            dialog.pack();

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
            if (p.getProductType().equalsIgnoreCase("Thức uống") && (keyword.isEmpty() || p.getProductName().toLowerCase().contains(keyword.toLowerCase()))) {
                pnlFood.add(createProductCard(p, nf));
            }
        }
        pnlFood.revalidate();
        pnlFood.repaint();
    }

    private JPanel createProductCard(Product p, NumberFormat nf) {
        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(400, 300));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY,4),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel lblImg = new JLabel();
        String path = p.getPosterPath();
        URL url = getClass().getResource(path);
        ImageIcon icon = new ImageIcon(url);
        Image img = icon.getImage().getScaledInstance(160, 160, Image.SCALE_SMOOTH);
        lblImg.setIcon(new ImageIcon(img));
        lblImg.setAlignmentX(Component.CENTER_ALIGNMENT);


//        JLabel lblName = new JLabel("<html><div style='width:120px; text-align:center;'>" + p.getProductName() + "</div></html>");

        JLabel lblName = new JLabel(p.getProductName(), SwingConstants.CENTER);
        lblName.setFont(lblName.getFont().deriveFont(Font.BOLD, 14f));
        lblName.setMaximumSize(new Dimension(Short.MAX_VALUE, 40));
        lblName.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblQty = new JLabel("Số lượng: " + p.getQuantity());
        lblQty.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblQty.setFont(lblQty.getFont().deriveFont(14f));

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
        
        //Nút xóa
        btnDelete.addActionListener(e -> {
    int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa sản phẩm này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
    if (confirm == JOptionPane.YES_OPTION) {
        try {
            Product_DAO dao = new Product_DAO();
            boolean deleted = dao.xoaProduct(p.getProductID());
            if (deleted) {
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
                loadProducts(txtSearch.getText().trim()); // load lại danh sách
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi xóa sản phẩm!");
        }
    }
});
        
        // nút sửa
 btnUpdate.addActionListener(e -> {
    int confirm = JOptionPane.showConfirmDialog(this, "Bạn có muốn cập nhật sản phẩm này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
    if (confirm == JOptionPane.YES_OPTION) {
        JDialog dialog=new JDialog((Frame) SwingUtilities.getWindowAncestor(this),"Cập Nhật Sản Phẩm",true);
        dialog.setContentPane(new CapNhatSanPham(p));
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        loadProducts(txtSearch.getText().trim());
    }
 });
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
