package gui;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import component.AddForm;
import dao.Movie_DAO;
import entity.Movie;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import raven.modal.ModalDialog;
import raven.modal.Toast;
import raven.modal.component.SimpleModalBorder;
import raven.popup.DefaultOption;
import raven.popup.GlassPanePopup;
import raven.popup.component.SimplePopupBorder;
import raven.toast.Notifications;



public class Phim extends javax.swing.JPanel {
    private Movie movie;
    public Phim() {
        initComponents();
        init();
        loadTableMovie();
    } 
private void init() {

             
        pnlbody.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:25;"
                + "background:$Table.background");

        SwingUtilities.invokeLater(() -> {
            JTableHeader header = table.getTableHeader();
            if (header != null) {
                header.putClientProperty(FlatClientProperties.STYLE, ""
                        + "height:60;"
                        + "hoverBackground:null;"
                        + "pressedBackground:null;"
                        + "separatorColor:$TableHeader.background;"
                        + "font:bold;");
            }

            table.putClientProperty(FlatClientProperties.STYLE, ""
                    + "rowHeight:50;"
                    + "showHorizontalLines:true;"
                    + "intercellSpacing:0,1;"
                    + "cellFocusColor:$TableHeader.hoverBackground;"
                    + "selectionBackground:$TableHeader.hoverBackground;"
                    + "selectionForeground:$Table.foreground;");

            scroll.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, ""
                    + "trackArc:999;"
                    + "trackInsets:3,3,3,3;"
                    + "thumbInsets:3,3,3,3;"
                    + "background:$Table.background;");
            txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tìm kiếm...");
            txtSearch.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON, new FlatSVGIcon("icon/search.svg")); 
            txtSearch.putClientProperty(FlatClientProperties.STYLE, ""
                    + "arc:15;"
                    + "borderWidth:0;"
                    + "focusWidth:0;"
                    + "innerFocusWidth:0;"
                    + "margin:5,20,5,20;"
                    + "background:$Panel.background");
//            table.getColumnModel().getColumn(2).setCellRenderer(new ProfileTableRenderer(table));
        });
    }

private void loadTableMovie() {
    Movie_DAO movieDao = new Movie_DAO();
    ArrayList<Movie> list = movieDao.getalltbMovie(); 
    
    DefaultTableModel model = (DefaultTableModel) table.getModel();
    model.setRowCount(0); 

    for (Movie movie : list) {
        Object[] row = {
            movie.getMovieID(),
            movie.getMovieName(),
            movie.getStatus(),
            movie.getDuration()
        };
        model.addRow(row); 
    }
}
public Movie getSelectedData() {
    int selectedRow = table.getSelectedRow();
    if (selectedRow >= 0) {
        movie = new Movie(
            table.getValueAt(selectedRow, 0).toString(),
            table.getValueAt(selectedRow, 1).toString(),
            table.getValueAt(selectedRow, 2).toString(),
            Integer.parseInt(table.getValueAt(selectedRow, 3).toString())
        );
        return movie;
    }
    return null;
}



    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 50), new java.awt.Dimension(0, 20), new java.awt.Dimension(32767, 50));
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(50, 0), new java.awt.Dimension(50, 0), new java.awt.Dimension(50, 32767));
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(50, 0), new java.awt.Dimension(50, 0), new java.awt.Dimension(50, 32767));
        pnlHeader = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        btnAdd = new component.ButtonAction();
        btnDel = new component.ButtonAction();
        btnUpdate = new component.ButtonAction();
        btnSearch = new component.ButtonAction();
        pnlbody = new javax.swing.JPanel();
        scroll = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.BorderLayout());
        add(filler1, java.awt.BorderLayout.PAGE_END);

        filler2.setBackground(new java.awt.Color(255, 255, 255));
        add(filler2, java.awt.BorderLayout.LINE_END);
        add(filler3, java.awt.BorderLayout.LINE_START);

        pnlHeader.setBackground(new java.awt.Color(255, 255, 255));
        pnlHeader.setMinimumSize(new java.awt.Dimension(200, 100));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Danh Sách Phim");
        jLabel1.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        txtSearch.setText(" ");
        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });

        btnAdd.setText("Thêm");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnDel.setText("Xóa");
        btnDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelActionPerformed(evt);
            }
        });

        btnUpdate.setText("Sửa");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnSearch.setText("Tìm Kiếm");

        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 355, Short.MAX_VALUE)
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDel, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70))
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addGap(413, 413, 413)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlHeaderLayout.setVerticalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        add(pnlHeader, java.awt.BorderLayout.NORTH);

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã Phim", "Tên Phim", "Trạng Thái", "Thời Lượng(Phút)"
            }
        ));
        table.getTableHeader().setReorderingAllowed(false);
        scroll.setViewportView(table);

        javax.swing.GroupLayout pnlbodyLayout = new javax.swing.GroupLayout(pnlbody);
        pnlbody.setLayout(pnlbodyLayout);
        pnlbodyLayout.setHorizontalGroup(
            pnlbodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlbodyLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 988, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlbodyLayout.setVerticalGroup(
            pnlbodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scroll, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 549, Short.MAX_VALUE)
        );

        add(pnlbody, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    
    
    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
   
    }//GEN-LAST:event_txtSearchActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
     AddForm create = new AddForm();

    DefaultOption option = new DefaultOption() {
        @Override
        public boolean closeWhenClickOutside() {
            return true;
        }
    };

    String[] actions = new String[]{"Lưu", "Hủy"};

    // Hiển thị popup
    GlassPanePopup.showPopup(new SimplePopupBorder(create, "Thêm Phim", actions, (popupController, selectedIndex) -> {
        if (selectedIndex == 0) {  
            try {
              
                if (create.validateInput()) {  
                    Movie movie = create.getMovie();
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    model.addRow(new Object[]{
                        movie.getMovieID(),
                        movie.getMovieName(),
                        movie.getStatus(),
                        movie.getDuration()
                    });

                    Movie_DAO movieDao = new Movie_DAO();
                    boolean isSaved = movieDao.addMovie(movie);

                    if (isSaved) {
                        Notifications.getInstance().show(Notifications.Type.SUCCESS, "Phim đã được thêm thành công!");
                    } else {
                        Notifications.getInstance().show(Notifications.Type.ERROR, "Có lỗi xảy ra khi lưu phim vào cơ sở dữ liệu.");
                    }

                    popupController.closePopup();
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, "Thông tin phim chưa hợp lệ!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                Notifications.getInstance().show(Notifications.Type.ERROR, "Có lỗi xảy ra khi thêm phim!");
            }
        } else { 
            popupController.closePopup();
        }
    }), option);
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelActionPerformed
        
    }//GEN-LAST:event_btnDelActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
      Movie selectedMovie = getSelectedData();
if (selectedMovie != null) {
    AddForm addForm = new AddForm();
    
    SimpleModalBorder.Option[] options = new SimpleModalBorder.Option[]{
        new SimpleModalBorder.Option("Cancel", SimpleModalBorder.CANCEL_OPTION),
        new SimpleModalBorder.Option("Update", SimpleModalBorder.OK_OPTION)
    };

    ModalDialog.showModal(this, new SimpleModalBorder(addForm, "Edit Movie [" + selectedMovie.getMovieName() + "]", options, (mc, i) -> {
        if (i == SimpleModalBorder.OK_OPTION) {
            try {
                Movie updatedMovie = addForm.getMovie();
                Toast.show(this, Toast.Type.SUCCESS, "Movie has been updated");

                loadTableMovie();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.show(this, Toast.Type.ERROR, "Failed to update movie");
            }
        } else if (i == SimpleModalBorder.CANCEL_OPTION) {
            // Cancelled
        }
    }));
} else {
    Toast.show(this, Toast.Type.WARNING, "Please select a movie to edit.");
}

    }//GEN-LAST:event_btnUpdateActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private component.ButtonAction btnAdd;
    private component.ButtonAction btnDel;
    private component.ButtonAction btnSearch;
    private component.ButtonAction btnUpdate;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlbody;
    private javax.swing.JScrollPane scroll;
    private javax.swing.JTable table;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
