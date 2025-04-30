package gui;

import com.toedter.calendar.JDateChooser;
import dao.MovieSchedule_DAO;
import entity.Movie;
import entity.MovieSchedule;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author khang
 */
public class ShowScheduleForm extends JPanel {
    private JPanel panelGrid;
    private JPanel bottomPanel;
    
    public ShowScheduleForm() {
        setLayout(new BorderLayout(0, 0));
        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        JLabel title = new JLabel("THÔNG TIN LỊCH CHIẾU", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setBorder(BorderFactory.createEmptyBorder(20, 10, 5, 10));
        add(title, BorderLayout.NORTH);

        panelGrid = new JPanel(new GridLayout(0, 3, 20, 20)); // 3 cột
        panelGrid.setBackground(new java.awt.Color(255, 255, 255));
        JScrollPane scrollPane = new JScrollPane(panelGrid);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(new java.awt.Color(255, 255, 255));
        add(scrollPane, BorderLayout.CENTER);
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd-MM-yyyy");
        dateChooser.setPreferredSize(new Dimension(200, 35));
        dateChooser.getDateEditor().addPropertyChangeListener("date", evt -> {
            Date selectedDate = dateChooser.getDate();
            if (selectedDate != null) {
                LocalDate date = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                filterScheduleByDate(date);
            }
        });
        
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        datePanel.setBackground(new java.awt.Color(255, 255, 255));
        datePanel.add(dateChooser);
        bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.setBackground(new java.awt.Color(255, 255, 255));
        bottomPanel.add(datePanel); // thay vì add(dateChooser) + add(btnSearch)
        add(bottomPanel, BorderLayout.SOUTH);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date defaultDate = sdf.parse("19-04-2025");
            dateChooser.setDate(defaultDate);
            filterScheduleByDate(defaultDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        loadData(); (hiển thị tất cả lịch chiếu)
    }

    private void loadData() {
        MovieSchedule_DAO dao = new MovieSchedule_DAO();
        Map<Movie, ArrayList<MovieSchedule>> data = dao.getMovieWithSchedules();

        for (Map.Entry<Movie, ArrayList<MovieSchedule>> entry : data.entrySet()) {
            Movie movie = entry.getKey();
            ArrayList<MovieSchedule> schedules = entry.getValue();

            ScheduleItemPanel item = new ScheduleItemPanel(movie, schedules);
            panelGrid.add(item);
        }

        revalidate();
        repaint();
    }
    private void filterScheduleByDate(LocalDate date) {
        panelGrid.removeAll();
        panelGrid.setBorder(null);
        MovieSchedule_DAO dao = new MovieSchedule_DAO();
        Map<Movie, ArrayList<MovieSchedule>> allData = dao.getMovieWithSchedules();

        for (Map.Entry<Movie, ArrayList<MovieSchedule>> entry : allData.entrySet()) {
            List<MovieSchedule> filtered = entry.getValue().stream()
                .filter(s -> s.getStartTime().toLocalDate().equals(date))
                .collect(Collectors.toList());

            if (!filtered.isEmpty()) {
                ScheduleItemPanel item = new ScheduleItemPanel(entry.getKey(), (ArrayList<MovieSchedule>) filtered);
                panelGrid.add(item);
            }
        }

        panelGrid.revalidate();
        panelGrid.repaint();
    }


}

