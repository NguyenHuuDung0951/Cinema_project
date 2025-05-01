/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import dao.MovieScheduleSeat_DAO;
import dao.Room_DAO;
import entity.Movie;
import entity.MovieSchedule;
import entity.Room;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.BookingData;

/**
 *
 * @author khang
 */
public class ScheduleItemPanel extends JPanel {

    public ScheduleItemPanel(Movie movie, ArrayList<MovieSchedule> schedules) {
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createLineBorder(Color.GRAY));

        // Tên phim
        JLabel lblTitle = new JLabel(movie.getMovieName(), JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        add(lblTitle, BorderLayout.NORTH);

        String posterPath = movie.getPosterPath();      

        URL url = getClass().getResource(posterPath);
        ImageIcon icon = null;
        if (url != null) {
            icon = new ImageIcon(
                    new ImageIcon(url)
                            .getImage()
                            .getScaledInstance(160, 220, Image.SCALE_SMOOTH)
            );
        }
        JLabel lblPoster = (icon != null
                ? new JLabel(icon, JLabel.CENTER)
                : new JLabel("Không tìm thấy ảnh", JLabel.CENTER));
        add(lblPoster, BorderLayout.CENTER);

        // Nút trailer + chi tiết
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnTrailer = new JButton("Trailer");
        JButton btnDetail = new JButton("Thông tin chi tiết");

        actionPanel.add(btnTrailer);
        actionPanel.add(btnDetail);

        // Khung giờ chiếu
        JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        for (MovieSchedule schedule : schedules) {
            try {
                LocalTime start = schedule.getStartTime().toLocalTime();
                LocalTime end = schedule.getEndTime().toLocalTime();
                Room_DAO roomDao = new Room_DAO();
                Room room = schedule.getRoom();
                if (room == null) {
                    room = new Room_DAO().getRoomByID("R001");
                }
                String roomName = (room != null) ? room.getRoomName() : "Không rõ";
                JButton btnTime = new JButton(start + " ~ " + end);
                btnTime.addActionListener(e -> {
                    BookingData bd = BookingData.getInstance();
                    bd.setMovieID(movie.getMovieID());
                    bd.setMovieName(movie.getMovieName());
                    bd.setRoomID(schedule.getRoom().getRoomID());
                    bd.setRoomName(schedule.getRoom().getRoomName());
                    bd.setScheduleID(schedule.getScheduleID());
                    bd.setShowDate(schedule.getStartTime().toLocalDate().toString());
                    bd.setShowTime(schedule.getStartTime().toLocalTime().toString());
                    bd.setPosterPath(movie.getPosterPath());
                    // Khởi tạo ghế trong DB nếu cần
                    try {
                        new MovieScheduleSeat_DAO().initSeatsForSchedule(schedule.getScheduleID());
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this,
                                "Không thể khởi tạo ghế: " + ex.getMessage(),
                                "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    // Mở form chọn ghế
                    new SeatSelectionForm().setVisible(true);
                });
                timePanel.add(btnTime);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        // Gộp 2 phần lại
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(actionPanel, BorderLayout.NORTH);
        bottomPanel.add(timePanel, BorderLayout.CENTER);

        add(bottomPanel, BorderLayout.SOUTH);
    }
}
