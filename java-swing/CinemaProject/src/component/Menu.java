package component;

import even.EventMenu;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component; 
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;  
import net.miginfocom.swing.MigLayout;

public class Menu extends javax.swing.JPanel {

    private EventMenu event;
    public Menu() {
        initComponents();   
        pnlMenu.setLayout(new MigLayout("wrap, fillx, inset 3", "[fill]", "[]0[]"));
     
       
    } 

    public void initMenu(EventMenu event){
        this.event = event;
        addMenu(new ImageIcon(getClass().getResource("/icon/1.png")), "Phim", 0);
        addMenu(new ImageIcon(getClass().getResource("/icon/3.png")), "Lịch Chiếu", 2);
          addMenu(new ImageIcon(getClass().getResource("/icon/3.png")), "Ưu đãi", 3);
        addMenuWithSub(
        new ImageIcon(getClass().getResource("/icon/5.png")),
    "Sản Phẩm", 4,
    new Object[][] {
        {"Đồ ăn", new ImageIcon(getClass().getResource("/icon/1.png"))},
        {"Nước uống", new ImageIcon(getClass().getResource("/icon/2.png"))}
    }
);


        addMenu(new ImageIcon(getClass().getResource("/icon/6.png")), "Thống kê", 5);
        addMenu(new ImageIcon(getClass().getResource("/icon/7.png")), "Hồ Sơ", 6);
          addEmpty();
        addMenu(new ImageIcon(getClass().getResource("/icon/8.png")), "Đăng Xuất", 7);
      
    }
    private void addEmpty() {
        pnlMenu.add(new JLabel(), "push");
    }
    public void addMenu(Icon icon,String text,int index){
        ButtonMenu menu = new ButtonMenu();
        menu.setIcon(icon);
        menu.setText(""+text);
        pnlMenu.add(menu);
        menu.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                event.selected(index);
                setSelected(menu);
            }
        
        });
    }
    private void setSelected(ButtonMenu menu){
        for (Component com : pnlMenu.getComponents()) {
            if (com instanceof ButtonMenu) {
                ButtonMenu b = (ButtonMenu) com;
                b.setSelected(false);
            }
        } 
        menu.setSelected(true);
    }
    
    
    
    
    
    @SuppressWarnings("unchecked")
    
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        pnlMenu = new javax.swing.JPanel();

        setBackground(new java.awt.Color(102, 102, 102));
        setPreferredSize(new java.awt.Dimension(240, 650));
        setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));
        jPanel1.setEnabled(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(100, 100));

        jLabel1.setBackground(new java.awt.Color(102, 102, 102));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 36)); 
        jLabel1.setForeground(new java.awt.Color(255, 102, 102));
        jLabel1.setText("  CGV Cinema");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        add(jPanel1, java.awt.BorderLayout.NORTH);

        pnlMenu.setBackground(new java.awt.Color(102, 102, 102));

        javax.swing.GroupLayout pnlMenuLayout = new javax.swing.GroupLayout(pnlMenu);
        pnlMenu.setLayout(pnlMenuLayout);
        pnlMenuLayout.setHorizontalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 252, Short.MAX_VALUE)
        );
        pnlMenuLayout.setVerticalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 484, Short.MAX_VALUE)
        );

        add(pnlMenu, java.awt.BorderLayout.CENTER);
    }
    public void addMenuWithSub(Icon icon, String text, int index, Object[][] subItems) {
    ButtonMenu mainMenu = new ButtonMenu();
    mainMenu.setIcon(icon);
    mainMenu.setText(text);

    pnlMenu.add(mainMenu, "wrap");

    for (int i = 0; i < subItems.length; i++) {
        String subText = (String) subItems[i][0];
        Icon subIcon = (Icon) subItems[i][1];

        ButtonMenu sub = new ButtonMenu();
        sub.setText("    " + subText);
        sub.setIcon(subIcon);
        sub.setAsSubMenu(true);
        sub.setVisible(false);

        int subIndex = index * 10 + i;
        sub.addActionListener(e -> {
            event.selected(subIndex);
            setSelected(sub);
        });

        mainMenu.addSubMenu(sub);
    }

    mainMenu.addActionListener(e -> {
        boolean show = !mainMenu.isShowingSubMenu();
        mainMenu.setShowingSubMenu(show);

        for (int i = 0; i < mainMenu.getSubMenus().size(); i++) {
            ButtonMenu sub = mainMenu.getSubMenus().get(i);
            if (show) {
                int pos = pnlMenu.getComponentZOrder(mainMenu) + 1 + i;
                pnlMenu.add(sub, "wrap", pos);
                sub.setVisible(true);
            } else {
                pnlMenu.remove(sub);
            }
        }

        pnlMenu.revalidate();
        pnlMenu.repaint();
        setSelected(mainMenu);
    });
}


 
    
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel pnlMenu;
    



    }
