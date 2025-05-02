package gui;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import component.AddForm2;
import dao.Voucher_DAO;
import entity.Voucher;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.table.DefaultTableModel;
import raven.popup.DefaultOption;
import raven.popup.GlassPanePopup;
import raven.popup.component.SimplePopupBorder;
import raven.toast.Notifications;

public class UuDai_Form extends javax.swing.JPanel {

    public UuDai_Form() {
        initComponents();
        jTable1.putClientProperty(FlatClientProperties.STYLE, ""
                    + "rowHeight:50;"
                    + "showHorizontalLines:true;"
                    + "intercellSpacing:0,1;"
                    + "cellFocusColor:$TableHeader.hoverBackground;"
                    + "selectionBackground:$TableHeader.hoverBackground;"
                    + "selectionForeground:$Table.foreground;");
        
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnAddPromotionActionPerformed(evt);  
    }
});
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnUpdateVoucherActionPerformed(evt);
    }
});
        jButton3.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnDeletePromotionActionPerformed(evt);  
    }
});
        jTextField1.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
    @Override
    public void insertUpdate(javax.swing.event.DocumentEvent e) {
        searchLive();
    }

    @Override
    public void removeUpdate(javax.swing.event.DocumentEvent e) {
        searchLive();
    }

    @Override
    public void changedUpdate(javax.swing.event.DocumentEvent e) {
        // Thường không cần thiết cho JTextField
    }

    private void searchLive() {
        String keyword = jTextField1.getText().trim();
        loadVoucher(keyword);
    }
});
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(new java.awt.Color(255, 255, 255));
        jPanel1 = new javax.swing.JPanel();   
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();

        jLabel1.setFont(new java.awt.Font("Arial", Font.BOLD, 30)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("THÔNG TIN KHUYẾN MÃI");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(20, 20, 20))
        );
        add(jPanel1, BorderLayout.NORTH);
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
                                .addContainerGap())
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Mã Khuyến Mãi", "Tên Khuyến Mãi", "Ngày Bắt Đầu", "Ngày Kết Thúc ", "Tổng Tiền Tối Thiểu", "Phần Trăm Khuyến Mãi"
                }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("Thêm");
        jButton1.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:15;"
                + "borderWidth:0;"
                + "focusWidth:0;"
                + "innerFocusWidth:0;"
                + "background:$Panel.background");
        jButton2.setText("Sửa");
        jButton2.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:15;"
                + "borderWidth:0;"
                + "focusWidth:0;"
                + "innerFocusWidth:0;"
                + "background:$Panel.background");
        jButton3.setText("Xóa");
        jButton3.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:15;"
                + "borderWidth:0;"
                + "focusWidth:0;"
                + "innerFocusWidth:0;"
                + "background:$Panel.background");
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"5 khuyến mãi gần nhất", "4 khuyến mãi gần nhất", "3 khuyến mãi gần nhất"}));
        jComboBox1.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:15;"
                + "borderWidth:0;"
                + "focusWidth:0;"
                + "innerFocusWidth:0;"
                + "background:#ffffff");
        jTextField1.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tìm kiếm...");
        jTextField1.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON, new FlatSVGIcon("icon/search.svg"));
        jTextField1.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:15;"
                + "borderWidth:0;"
                + "focusWidth:0;"
                + "innerFocusWidth:0;"
                + "margin:5,20,5,20;"
                + "background:$Panel.background");
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jScrollPane1)
                                                .addContainerGap())
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(25, 25, 25)
                                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(68, 68, 68)
                                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 271, Short.MAX_VALUE)
                                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(69, 69, 69)
                                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(69, 69, 69)
                                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(67, 67, 67))))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 510, Short.MAX_VALUE)
                                .addGap(20, 20, 20)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton1)
                                        .addComponent(jButton2)
                                        .addComponent(jButton3))
                                .addGap(20, 20, 20))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {
    }
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;

    
    
    
   private void btnAddPromotionActionPerformed(java.awt.event.ActionEvent evt) {
    AddForm2 form = new AddForm2(); 
    DefaultOption option = new DefaultOption() {
        @Override
        public boolean closeWhenClickOutside() {
            return true;
        }
    };

    String[] actions = {"Lưu", "Hủy"};
    GlassPanePopup.showPopup(new SimplePopupBorder(form, "Thêm Ưu Đãi", actions, (popupController, selectedIndex) -> {
        if (selectedIndex == 0) {
            try {
                if (form.validateInput()) {
                    Voucher_DAO dao = new Voucher_DAO();
                    Voucher voucher = form.getUuDai();

                    if (dao.insertVoucher(voucher)) {
                  
                        Notifications.getInstance().show(Notifications.Type.SUCCESS, "Thêm ưu đãi thành công!");

                        
                        ArrayList<Voucher> dsVoucher = dao.getalltbVoucher(); 
                        loadDataToTable(dsVoucher);
                        popupController.closePopup();
                    } else {
                        Notifications.getInstance().show(Notifications.Type.ERROR, "Lỗi khi lưu ưu đãi!");
                    }
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, "Thông tin chưa hợp lệ!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                Notifications.getInstance().show(Notifications.Type.ERROR, "Đã xảy ra lỗi khi thêm ưu đãi!");
            }
        } else {
            popupController.closePopup();
        }
    }), option);
}
private void btnDeletePromotionActionPerformed(java.awt.event.ActionEvent evt) {
    int selectedRow = jTable1.getSelectedRow(); 
    if (selectedRow == -1) {
        Notifications.getInstance().show(Notifications.Type.WARNING, "Vui lòng chọn khuyến mãi để xóa!");
        return;
    }
    String voucherID = jTable1.getValueAt(selectedRow, 0).toString();
    int confirm = javax.swing.JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa khuyến mãi này?", "Xác nhận xóa", javax.swing.JOptionPane.YES_NO_OPTION);
    
    if (confirm == javax.swing.JOptionPane.YES_OPTION) {
        Voucher_DAO dao = new Voucher_DAO();
        if (dao.deleteVoucher(voucherID)) {
            Notifications.getInstance().show(Notifications.Type.SUCCESS, "Xóa ưu đãi thành công!");
            ArrayList<Voucher> dsVoucher = dao.getalltbVoucher();
            loadDataToTable(dsVoucher);
        } else {
            Notifications.getInstance().show(Notifications.Type.ERROR, "Lỗi khi xóa ưu đãi!");
        }
    }
}
private void btnUpdateVoucherActionPerformed(java.awt.event.ActionEvent evt) {                                           
    List<Voucher> list = getSelectedVouchers();

    if (list.size() == 1) {  
        Voucher data = list.get(0);
        AddForm2 form = new AddForm2();
        form.setUuDai(data);
        showVoucherPopup(form, "Sửa Ưu Đãi [" + data.getVoucherName() + "]", data);
    } else {
        Notifications.getInstance().show(Notifications.Type.WARNING, list.isEmpty() ? 
            "Vui lòng chọn ưu đãi để chỉnh sửa" : "Vui lòng chỉ chọn một ưu đãi để chỉnh sửa");
    }
}

private void showVoucherPopup(AddForm2 form, String title, Voucher data) {
    String[] actions = {"Sửa", "Hủy"};
    
    DefaultOption option = new DefaultOption() {
        @Override
        public boolean closeWhenClickOutside() {
            return true;
        }
    };

    GlassPanePopup.showPopup(new SimplePopupBorder(form, title, actions, (popupController, selectedIndex) -> {
        if (selectedIndex == 0 && form.validateInput()) {  
            try {
                Voucher_DAO dao = new Voucher_DAO();
                Voucher editedVoucher = form.getUuDai();
                editedVoucher.setVoucherID(data.getVoucherID());  
                if (new Voucher_DAO().updateVoucher(editedVoucher)) {
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, "Cập nhật ưu đãi thành công");
                    ArrayList<Voucher> dsVoucher = dao.getalltbVoucher(); 
                        loadDataToTable(dsVoucher);; 
                } else {
                    Notifications.getInstance().show(Notifications.Type.ERROR, "Cập nhật ưu đãi thất bại");
                }
            } catch (Exception e) {
                e.printStackTrace();
                Notifications.getInstance().show(Notifications.Type.ERROR, "Đã xảy ra lỗi khi cập nhật ưu đãi!");
            } finally {
                popupController.closePopup();  
            }
        } else {
            popupController.closePopup(); 
        }
    }), option);
}





    public void loadDataToTable(ArrayList<Voucher> dsVoucher) {

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); 

        for (Voucher vc : dsVoucher) {
            model.addRow(new Object[]{
                vc.getVoucherID(),
                vc.getVoucherName(),
                vc.getStartDate(),
                vc.getEndDate(),
                vc.getMinimumPrice(),
                vc.getValueVoucher()
            });
        }
    }
    private List<Voucher> getSelectedVouchers() {
    int[] selectedRows = jTable1.getSelectedRows(); 
    List<Voucher> selectedVouchers = new ArrayList<>();

    for (int row : selectedRows) {
        String voucherID = jTable1.getValueAt(row, 0).toString();

        Voucher voucher = new Voucher_DAO().getVoucherByID(voucherID);
        selectedVouchers.add(voucher);
    }

    return selectedVouchers;
}
    private void loadVoucher(String keyword) {
    Voucher_DAO dao = new Voucher_DAO();
    ArrayList<Voucher> list = dao.getalltbVoucher();
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    model.setRowCount(0); 

    keyword = keyword.toLowerCase();

    for (Voucher v : list) {
        if (v.getVoucherID().toLowerCase().contains(keyword) ||
            v.getVoucherName().toLowerCase().contains(keyword)) {
            model.addRow(new Object[]{
                v.getVoucherID(),
                v.getVoucherName(),
                v.getStartDate(),
                v.getEndDate(),
                v.getMinimumPrice(),
                v.getValueVoucher()
            });
        }
    }
}
}
