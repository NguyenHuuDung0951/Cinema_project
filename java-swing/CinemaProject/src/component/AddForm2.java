
package component;

import entity.Voucher;
import gui.UuDai_Form;
import java.time.LocalDate;
import java.util.Date;
import javax.swing.JOptionPane;
import raven.datetime.component.date.DatePicker;
import raven.toast.Notifications;


public class AddForm2 extends javax.swing.JPanel {
    private DatePicker datePickerStart;
    private DatePicker datePickerEnd;
    public AddForm2() {
        initComponents();
        datePickerStart = new DatePicker();
        datePickerEnd = new DatePicker();

        datePickerStart.setEditor(txtStart);
        datePickerEnd.setEditor(txtEnd);
    }

   public boolean validateInput() {
    String id = txtMa.getText().trim();
    String name = txtName.getText().trim();
    String percentStr = txtPercent.getText().trim();
    String totalStr = txtTotal.getText().trim();
    LocalDate startDate = datePickerStart.getSelectedDate();
    LocalDate endDate = datePickerEnd.getSelectedDate();

    
    String idRegex = "^KM\\d{3}$";
    String nameRegex = "^[\\p{L}\\s\\d]+$"; 
    String percentRegex = "^(100|[1-9]?\\d)$"; 
    String moneyRegex = "^[0-9]+(\\.[0-9]+)?$"; 

    if (!id.matches(idRegex)) {
        Notifications.getInstance().show(Notifications.Type.WARNING, "Mã khuyến mãi không hợp lệ. Định dạng: KM###");

        txtMa.requestFocus();
        return false;
    }

    if (!name.matches(nameRegex)) {
         Notifications.getInstance().show(Notifications.Type.WARNING, "Tên khuyến mãi không hợp lệ");
        txtName.requestFocus();
        return false;
    }
        if (startDate == null || endDate == null) {
      Notifications.getInstance().show(Notifications.Type.WARNING, "Vui lòng chọn ngày bắt đầu và ngày kết thúc");
      return false;
    }

    if (startDate.isAfter(endDate)) {
        Notifications.getInstance().show(Notifications.Type.WARNING, "Ngày bắt đầu phải trước hoặc bằng ngày kết thúc");
        return false;
    }

    double totalValue = Double.parseDouble(totalStr);
    if (totalValue <= 0) {
        Notifications.getInstance().show(Notifications.Type.WARNING, "Tổng tiền phải là số hợp lệ (vd: 1000 hoặc 1500.5)");
        txtTotal.requestFocus();
        return false;
    }
    if (!percentStr.matches(percentRegex)) {
        Notifications.getInstance().show(Notifications.Type.WARNING, "Phần trăm khuyến mãi phải nằm trong khoảng 0–100 (vd: 10)");
        txtPercent.requestFocus();
        return false;
    }


    return true;
}

public Voucher getUuDai() {
    String ma = txtMa.getText().trim();
    String name = txtName.getText().trim();
    double total = Double.parseDouble(txtTotal.getText().trim());
    String valueVoucher = txtPercent.getText().trim() + "%";
    LocalDate startDate = datePickerStart.getSelectedDate();
    LocalDate endDate = datePickerEnd.getSelectedDate();

    return new Voucher(ma, name, startDate, endDate, total, valueVoucher);
}
public void setUuDai(Voucher voucher) {
   
    if (voucher != null) {    
        txtMa.setText(voucher.getVoucherID());  
        txtName.setText(voucher.getVoucherName());  
        txtTotal.setText(String.valueOf(voucher.getMinimumPrice()));  
        txtPercent.setText(voucher.getValueVoucher().replace("%", ""));              
        datePickerStart.setSelectedDate(voucher.getStartDate());
        datePickerEnd.setSelectedDate(voucher.getEndDate());
    }
}
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtMa = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        txtStart = new javax.swing.JFormattedTextField();
        txtEnd = new javax.swing.JFormattedTextField();
        txtTotal = new javax.swing.JTextField();
        txtPercent = new javax.swing.JTextField();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Mã Khuyến Mãi");

        jLabel2.setText("Tên Khuyến Mãi");

        jLabel3.setText("Ngày Kết Thúc");

        jLabel4.setText("Ngày Bắt Đầu");

        jLabel5.setText("Tổng Tiền Tối Thiểu");

        jLabel6.setText("Phần Trăm Khuyến Mãi");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtStart, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 74, Short.MAX_VALUE))
                            .addComponent(txtName)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMa)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtTotal)
                            .addComponent(txtPercent))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtStart, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPercent, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(43, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JFormattedTextField txtEnd;
    private javax.swing.JTextField txtMa;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPercent;
    private javax.swing.JFormattedTextField txtStart;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
