/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

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
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

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

        // (Có thể thêm poster ở đây nếu có ảnh)
        // Load ảnh dựa vào movieID
        String imgFileName = movieImageMap.get(movie.getMovieID());
        if (imgFileName != null) {
            String imgPath = "src/image/" + imgFileName; // <-- đúng thư mục ảnh bạn nói

            File imgFile = new File(imgPath);
            if (imgFile.exists()) {
                ImageIcon icon = new ImageIcon(imgPath);
                Image scaled = icon.getImage().getScaledInstance(160, 220, Image.SCALE_SMOOTH);
                JLabel lblPoster = new JLabel(new ImageIcon(scaled));
                lblPoster.setHorizontalAlignment(JLabel.CENTER);
                add(lblPoster, BorderLayout.CENTER);
            } else {
                JLabel lblPoster = new JLabel("Không tìm thấy ảnh", JLabel.CENTER);
                add(lblPoster, BorderLayout.CENTER);
            }
        } else {
            JLabel lblPoster = new JLabel("Không có ảnh", JLabel.CENTER);
            add(lblPoster, BorderLayout.CENTER);
        }

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
                    // Nếu getRoom() == null, thì phải load thủ công từ Room_DAO
                    room = new Room_DAO().getRoomByID("R001"); // hoặc schedule.getRoomID() nếu có
                }
                String roomName = (room != null) ? room.getRoomName() : "Không rõ";
                JButton btnTime = new JButton(start + " ~ " + end);
                btnTime.addActionListener(e -> {
                    new SeatSelectionForm(
                        movie.getMovieName(),
                        schedule.getStartTime().toLocalDate(),
                        schedule.getStartTime().toLocalTime(),
                        roomName,
                        "/image/avenger.jpg" // đường dẫn poster
                    ).setVisible(true);
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
    // Map tên phim/movieID -> tên file ảnh
    private static final Map<String, String> movieImageMap = new HashMap<>();
    static {
        movieImageMap.put("M001", "avenger.jpg");
        movieImageMap.put("M002", "nhabanu.jpg");
        movieImageMap.put("M003", "johnwick4.jpg");
        movieImageMap.put("M004", "sieuluagapsieulay6.jpg");
        movieImageMap.put("M005", "spiderman.jpg");
    }

}



