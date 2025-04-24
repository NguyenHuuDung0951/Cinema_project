package application;

import component.Form; 
import component.PhimForm;
import gui.PhimForm1;
import gui.LichChieu_Form;
import component.SanPham;
import gui.UuDai_Form; 
import connectDB.ConnectDB;
import dao.Voucher_DAO;
import entity.Voucher;
import even.EventMenu; 
import java.awt.Component;
import java.sql.SQLException;
import java.util.ArrayList;
  
public class Main extends javax.swing.JFrame {
     private Voucher_DAO vc_dao;
    private UuDai_Form uudai;
    public Main() { 
        initComponents();
        setExtendedState(MAXIMIZED_BOTH);
              try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        vc_dao = new Voucher_DAO();
        
        
        
           EventMenu event = new EventMenu() {
            @Override
            public void selected(int index) {
                 if (index == 2) {  
                     PhimForm1 phim = new PhimForm1();
                  showForm(phim);
                }else if(index==5){
                 
                }  
                 else if(index==3){
                     uudai = new UuDai_Form();
                     showForm(uudai);
                        ArrayList<Voucher> dsVoucher = vc_dao.getalltbVoucher();
                     uudai.loadDataToTable(dsVoucher);       
                }  
//                 else if(index == 4){
//                     SanPham sanpham = new SanPham();
//                     showForm(sanpham);
//                 }
                 else if (index == 7) { 
                    System.out.println("Logout");
                } else {
                     showForm(new Form(index)); 
                }
                   
                } 
             
        };
        menu.initMenu(event);
     
    } 
    private void showForm(Component com){
           body.removeAll();
           body.add(com);
           body.repaint();
           body.revalidate();
           com.setSize(body.getSize());
           com.setPreferredSize(body.getSize());
    }    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        main = new javax.swing.JPanel();
        menu = new component.Menu();
        body = new javax.swing.JPanel();
        header1 = new component.Header();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Cinema\n");
        setBackground(new java.awt.Color(102, 102, 102));

        main.setBackground(new java.awt.Color(255, 255, 255));
        main.setPreferredSize(new java.awt.Dimension(1300, 700));
        main.setLayout(new java.awt.BorderLayout());
        main.add(menu, java.awt.BorderLayout.WEST);

        javax.swing.GroupLayout header1Layout = new javax.swing.GroupLayout(header1);
        header1.setLayout(header1Layout);
        header1Layout.setHorizontalGroup(
            header1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1072, Short.MAX_VALUE)
        );
        header1Layout.setVerticalGroup(
            header1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 39, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout bodyLayout = new javax.swing.GroupLayout(body);
        body.setLayout(bodyLayout);
        bodyLayout.setHorizontalGroup(
            bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(header1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        bodyLayout.setVerticalGroup(
            bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bodyLayout.createSequentialGroup()
                .addComponent(header1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 661, Short.MAX_VALUE))
        );

        main.add(body, java.awt.BorderLayout.CENTER);

        getContentPane().add(main, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {  
      
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel body;
    private component.Header header1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel main;
    private component.Menu menu;
    // End of variables declaration//GEN-END:variables
}
