package application;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import component.Menu;
import component.Form;
import component.Header;
import component.InforOrders;
import component.LoadingScreen;
import component.SanPham_DoAn_1;
import component.SanPham_DoUong;
import gui.UuDai_Form;
import connectDB.ConnectDB;
import dao.OrderDetail_DAO;
import dao.Orders_DAO;
import dao.TicketDetail_DAO;
import dao.Voucher_DAO;
import entity.OrderDetail;
import entity.Orders;
import entity.TicketDetail;
import entity.Voucher;
import even.EventMenu;
import gui.LoginForm;
import gui.Phim;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.UIManager;
import raven.popup.GlassPanePopup;
import raven.toast.Notifications;
import gui.ShowScheduleForm;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Main extends javax.swing.JFrame {

    private Menu menu;
    private Voucher_DAO vc_dao;
    private UuDai_Form uudai;
    private JPanel searchHeader;
    private JTextField txtInvoiceSearch;

    public Main() {
        initComponents();
        // thay vì head = new Header();
        searchHeader = createSearchHeader();

        setExtendedState(MAXIMIZED_BOTH);
        try {
            ConnectDB.getInstance().connect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        vc_dao = new Voucher_DAO();

        menu = new Menu();
        main.add(menu, java.awt.BorderLayout.WEST);
        EventMenu event = new EventMenu() {
            @Override
            public void selected(int index) {
                if (index == 0) {
                    Phim phim = new Phim();
                    showForm(phim);
                } else if (index == 2) {
                    ShowScheduleForm schedule = new ShowScheduleForm();
                    showForm(schedule);
                } else if (index == 3) {
                    uudai = new UuDai_Form();
                    showForm(uudai);
                    ArrayList<Voucher> dsVoucher = vc_dao.getalltbVoucher();
                    uudai.loadDataToTable(dsVoucher);
                } else if (index == 40) {
                    SanPham_DoAn_1 spDoAn = new SanPham_DoAn_1(index);
                    showForm(spDoAn);
                } else if (index == 41) {
                    SanPham_DoUong spDoUong = new SanPham_DoUong(index);
                    showForm(spDoUong);
                } else if (index == 7) {
                    System.out.println("Logout");
                    // Thực hiện logout ở đây nếu cần
                } else {
                    showForm(new Form(index));
                }
            }
        };
        menu.initMenu(event);
    }

    private void showForm(Component com) {
        body.removeAll();
        body.setLayout(new BorderLayout());
        body.add(searchHeader, BorderLayout.NORTH);    // dùng searchHeader
        body.add(com, BorderLayout.CENTER);
        body.repaint();
        body.revalidate();
    }

    private JPanel createSearchHeader() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        p.setBackground(Color.WHITE);

        JLabel lbl = new JLabel("Tìm hóa đơn:");
        lbl.setFont(lbl.getFont().deriveFont(Font.BOLD, 14f));
        p.add(lbl);

        txtInvoiceSearch = new JTextField(15);
        txtInvoiceSearch.setFont(txtInvoiceSearch.getFont().deriveFont(14f));
        txtInvoiceSearch.addActionListener(e -> {
            String orderID = txtInvoiceSearch.getText().trim();
            InforOrders info = new InforOrders();

            try {
                Orders ord = new Orders_DAO().getOrderByID(orderID);
                info.getTxtOrderID().setText(ord.getOrderID());
                info.getTxtDate().setText(ord.getOrderDate().toString());
                info.getTxtTotal().setText(String.valueOf(ord.getTotalPrice()));
                info.getTxtEmployee().setText(ord.getEmployee().getFullName());
            } catch (SQLException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            List<TicketDetail> tickets;
            try {
                tickets = new TicketDetail_DAO().getByOrderID(orderID);
            } catch (SQLException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
            String movieName = "";
            if (!tickets.isEmpty()) {
                movieName = tickets.get(0).getMovie().getMovieName();
            }
            if (movieName == null || movieName.isEmpty()) {
                JOptionPane.showMessageDialog(Main.this,
                        "Phim đã bị xóa! Hóa đơn không hợp lệ",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            info.getTxtMovieName().setText(movieName);

            try {
                List<OrderDetail> products = new OrderDetail_DAO().getByOrderID(orderID);
                StringBuilder sbPro = new StringBuilder();
                double sumPro = 0;

                for (OrderDetail od : products) {
                    int qty = od.getQuantity();
                    double unitPrice = od.getProduct().getPrice();
                    double lineTotal = qty * unitPrice;

                    sbPro
                            .append(od.getProduct().getProductName())
                            .append(" (").append(qty).append("): ")
                            .append(lineTotal) // thành tiền dòng
                            .append("\n");

                    sumPro += lineTotal;
                }

                sbPro.append("Tổng sản phẩm: ").append(sumPro);
                info.getProductsTextArea().setText(sbPro.toString());

            } catch (SQLException ex) {
                Logger.getLogger(Main.class.getName())
                        .log(Level.SEVERE, null, ex);
            }

            try {
                tickets = new TicketDetail_DAO().getByOrderID(orderID);
                StringBuilder sbTick = new StringBuilder();
                double sumTick = 0;
                for (TicketDetail td : tickets) {
                    sbTick.append(td.getSeat().getLocation())
                            .append(": ").append(td.getTicketPrice())
                            .append("\n");
                    sumTick += td.getTicketPrice();
                }
                sbTick.append("Tổng vé: ").append(sumTick);
                info.getTicketsTextArea().setText(sbTick.toString());
            } catch (SQLException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }

            JDialog dialog = new JDialog(this, "Thông tin Hóa Đơn " + orderID, true);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.getContentPane().add(info);
            dialog.pack();
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        });
        p.add(txtInvoiceSearch);

        return p;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        main = new javax.swing.JPanel();
        body = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Cinema\n");
        setBackground(new java.awt.Color(102, 102, 102));

        main.setBackground(new java.awt.Color(255, 255, 255));
        main.setPreferredSize(new java.awt.Dimension(1300, 700));
        main.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout bodyLayout = new javax.swing.GroupLayout(body);
        body.setLayout(bodyLayout);
        bodyLayout.setHorizontalGroup(
            bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1312, Short.MAX_VALUE)
        );
        bodyLayout.setVerticalGroup(
            bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 700, Short.MAX_VALUE)
        );

        main.add(body, java.awt.BorderLayout.CENTER);

        getContentPane().add(main, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        try {
            FlatRobotoFont.install();
            UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));
            FlatMacLightLaf.setup();
            FlatLightLaf.setup();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // 1. Mở màn hình Loading trước
                LoadingScreen loadingScreen = new LoadingScreen();
                loadingScreen.setVisible(true);

                // 2. Tạo 1 Thread để chạy loading
                new Thread(() -> {
                    try {
                        for (int i = 0; i <= 100; i++) {
                            Thread.sleep(5); // 30ms tăng 1% (nhanh/chậm chỉnh tại đây)
                            loadingScreen.updateProgress(i);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    loadingScreen.dispose();

                    LoginForm login = new LoginForm();
                    login.setVisible(true);

                    // 4. Chờ login xong
                    while (login.isDisplayable()) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    if (login.isLoginSuccessful()) {
                        Main mainFrame = new Main();
                        GlassPanePopup.install(mainFrame);
                        Notifications.getInstance().setJFrame(mainFrame);
                        mainFrame.setVisible(true);
                    } else {
                        System.exit(0);
                    }
                }).start();
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel body;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel main;
    // End of variables declaration//GEN-END:variables
}
