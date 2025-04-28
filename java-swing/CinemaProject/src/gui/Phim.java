package gui;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import component.AddForm;
import dao.Movie_DAO;
import entity.Movie;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
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
private void loadTableMovie(List<Movie> list) {
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
private List<Movie> getSelectedMovies() {
    List<Movie> selectedMovies = new ArrayList<>();
    int[] selectedRows = table.getSelectedRows(); 
    for (int row : selectedRows) {
        String movieID = table.getValueAt(row, 0).toString().trim();
        String movieName = table.getValueAt(row, 1).toString().trim();
        String status = table.getValueAt(row, 2).toString().trim();
        int duration = Integer.parseInt(table.getValueAt(row, 3).toString().trim());
        selectedMovies.add(new Movie(movieID, movieName, status, duration));
    }
    return selectedMovies;
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

        jLabel1.setFont(new java.awt.Font("Arial", 1, 28)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Danh Sách Phim");
        jLabel1.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        txtSearch.setText(" ");
        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });

        btnAdd.setText("buttonAction1");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(270, 270, 270)
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(444, Short.MAX_VALUE))
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlHeaderLayout.setVerticalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSearch, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
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
            .addGroup(pnlbodyLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 537, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(pnlbody, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    
    
    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
    String keyword = txtSearch.getText().trim(); 

    if (!keyword.isEmpty()) {
        Movie_DAO dao = new Movie_DAO();
        ArrayList<Movie> searchResults = dao.searchMovieByName(keyword);

        if (!searchResults.isEmpty()) {
            loadTableMovie(searchResults);
        } else {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Không tìm thấy phim nào!");
            loadTableMovie(new ArrayList<>()); 
        }
    } else {
        loadTableMovie();
    }
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
      List<Movie> list = getSelectedMovies();

    if (!list.isEmpty()) {
        Movie data = list.get(0);

    if (data == null) {
        Notifications.getInstance().show(Notifications.Type.WARNING, "Không tìm thấy phim để xóa");
        return;
    }

    DefaultOption option = new DefaultOption() {
        @Override
        public boolean closeWhenClickOutside() {
            return true;
        }
    };

    String[] actions = new String[]{"Xóa", "Hủy"};

    GlassPanePopup.showPopup(new SimplePopupBorder(new JPanel(), "Xóa Phim [" + data.getMovieName() + "]", actions, (popupController, selectedIndex) -> {
        if (selectedIndex == 0) {
            try {
                Movie_DAO dao = new Movie_DAO(); 
                if (dao.deleteMovie(data.getMovieID())) {
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, "Xóa phim thành công");
                    loadTableMovie();
                    popupController.closePopup();
                } else {
                    Notifications.getInstance().show(Notifications.Type.ERROR, "Xóa phim thất bại");
                }
            } catch (Exception e) {
                e.printStackTrace();
                Notifications.getInstance().show(Notifications.Type.ERROR, "Đã xảy ra lỗi khi xóa phim!");
            }
        } else { 
            popupController.closePopup();
        }
    }), option);

} else {
    Notifications.getInstance().show(Notifications.Type.WARNING, "Vui lòng chọn phim để xóa");
}
    }//GEN-LAST:event_btnDelActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
    List<Movie> list = getSelectedMovies();
    
    if (!list.isEmpty()) {
        if (list.size() == 1) {
            Movie data = list.get(0);
            AddForm form = new AddForm();
            form.setMovie(data);

            DefaultOption option = new DefaultOption() {
                @Override
                public boolean closeWhenClickOutside() {
                    return true;
                }
            };
 
            String[] actions = new String[]{"Sửa", "Hủy"};

            GlassPanePopup.showPopup(new SimplePopupBorder(form, "Sửa Phim [" + data.getMovieName() + "]", actions, (popupController, selectedIndex) -> {
                if (selectedIndex == 0) {
                    try {
                        if (form.validateInput()) {
                            Movie editedMovie = form.getMovie();
                            editedMovie.setMovieID(data.getMovieID());

                            Movie_DAO dao = new Movie_DAO();
                            if (dao.editMovie(editedMovie)) {
                                Notifications.getInstance().show(Notifications.Type.SUCCESS, "Cập nhật phim thành công");
                                loadTableMovie();
                                popupController.closePopup();
                            } else {
                                Notifications.getInstance().show(Notifications.Type.ERROR, "Cập nhật phim thất bại");
                            }
                        } else {
                            Notifications.getInstance().show(Notifications.Type.WARNING, "Thông tin chưa hợp lệ!");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Notifications.getInstance().show(Notifications.Type.ERROR, "Đã xảy ra lỗi khi cập nhật phim!");
                    }
                } else {
                    popupController.closePopup();
                }
            }), option);

        } else {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Vui lòng chỉ chọn một phim để chỉnh sửa");
        }
    } else {
        Notifications.getInstance().show(Notifications.Type.WARNING, "Vui lòng chọn phim để chỉnh sửa");
    }


    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAction1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonAction1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private component.ButtonAction btnAdd;
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
