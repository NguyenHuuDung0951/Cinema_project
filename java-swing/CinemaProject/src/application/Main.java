package application;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import component.Menu;
import component.Form; 
import gui.PhimForm1;
import gui.UuDai_Form; 
import connectDB.ConnectDB;
import dao.Voucher_DAO;
import entity.Voucher;
import even.EventMenu; 
import java.awt.Component;
import java.sql.SQLException;
import java.util.ArrayList;
import gui.ShowScheduleForm;
public class Main extends javax.swing.JFrame {
    private Menu menu;
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
        
            menu = new Menu();
            main.add(menu, java.awt.BorderLayout.WEST); 
           EventMenu event = new EventMenu() {
            @Override
            public void selected(int index) {
                 if (index == 2) {  
//                     PhimForm1 phim = new PhimForm1();
//                  showForm(phim);
                ShowScheduleForm schedule = new ShowScheduleForm();
                     showForm(schedule);
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
        FlatMacLightLaf.setup();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel body;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel main;
    // End of variables declaration//GEN-END:variables
}
