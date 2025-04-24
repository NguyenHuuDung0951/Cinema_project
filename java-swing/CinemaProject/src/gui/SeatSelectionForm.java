package gui;

import connectDB.ConnectDB;
import dao.Seat_DAO;
import dao.MovieScheduleSeat_DAO;
import entity.Seat;
import entity.MovieScheduleSeat;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import com.formdev.flatlaf.FlatLightLaf;
public class SeatSelectionForm extends JFrame {
    private JPanel pnlScreen, pnlSeats, pnlInfo, pnlLegend;
    private JLabel lblTitle, lblDate, lblTime, lblRoom, lblPrice, lblSeats, lblTotal;
    private JButton btnContinue;

    // Giá theo loại ghế
    private final Map<String, Double> priceMap = Map.of(
        "ST01", 60000.0,
        "ST02", 90000.0,
        "ST03", 100000.0
    );
    // Bản đồ location -> Seat
    private Map<String, Seat> mapLoc = new HashMap<>();
    private String scheduleID = "SC001";

    public SeatSelectionForm() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Chọn ghế xem phim");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Màn hình
        pnlScreen = new JPanel();
        pnlScreen.add(new JLabel("Màn hình"));
        add(pnlScreen, BorderLayout.NORTH);

        // Bảng ghế
        pnlSeats = new JPanel(new GridBagLayout());
        loadSeats();
        add(new JScrollPane(pnlSeats), BorderLayout.CENTER);

        // Thông tin bên phải
        pnlInfo = new JPanel();
        pnlInfo.setLayout(new BoxLayout(pnlInfo, BoxLayout.Y_AXIS));
        pnlInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        URL posterURL = getClass().getResource("/images/venom.jpg");
        ImageIcon poster = posterURL != null ? new ImageIcon(posterURL) : new ImageIcon();
        JLabel lblPoster = new JLabel(poster);
        lblPoster.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlInfo.add(lblPoster);
        pnlInfo.add(Box.createVerticalStrut(10));

        lblTitle = new JLabel("Venom: Kẻ Cuối");
        lblTitle.setFont(lblTitle.getFont().deriveFont(Font.BOLD, 16f));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlInfo.add(lblTitle);
        pnlInfo.add(Box.createVerticalStrut(10));

        lblDate  = new JLabel("Ngày chiếu: 17/11/2024");
        lblTime  = new JLabel("Giờ chiếu : 19:47");
        lblRoom  = new JLabel("Phòng     : Phòng 1");
        lblPrice = new JLabel(String.format("Giá vé    : %.0f VND", priceMap.get("ST01")));
        lblSeats = new JLabel("<html><body style='width:100px'>Ghế: </body></html>");
        lblSeats.setVerticalAlignment(SwingConstants.TOP);
        lblTotal = new JLabel("Tổng      : 0 VND");

        for (JLabel l : Arrays.asList(lblDate, lblTime, lblRoom, lblPrice, lblSeats, lblTotal)) {
            l.setAlignmentX(Component.LEFT_ALIGNMENT);
            pnlInfo.add(l);
            pnlInfo.add(Box.createVerticalStrut(5));
        }

        btnContinue = new JButton("Tiếp tục");
        btnContinue.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlInfo.add(Box.createVerticalStrut(20));
        pnlInfo.add(btnContinue);

        add(pnlInfo, BorderLayout.EAST);

        // Chú giải màu
        pnlLegend = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        pnlLegend.add(createLegend("Ghế thường", Color.WHITE));
        pnlLegend.add(createLegend("Ghế VIP", Color.YELLOW));
        pnlLegend.add(createLegend("Ghế đôi SweetBox", new Color(255, 200, 200)));
        pnlLegend.add(createLegend("Ghế đã bán", Color.LIGHT_GRAY));
        add(pnlLegend, BorderLayout.SOUTH);

        pack();
        setSize(1200, 700);
        setLocationRelativeTo(null);
    }

    private void loadSeats() {
        pnlSeats.removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 3, 3);
        try {
            // Đọc danh sách ghế
            ArrayList<Seat> seats = new Seat_DAO().getalltbSeat();
            mapLoc = seats.stream().collect(Collectors.toMap(Seat::getLocation, s -> s));
            // Danh sách ghế đã bán
            Set<String> sold = new HashSet<>();
            for (MovieScheduleSeat mss : new MovieScheduleSeat_DAO().getalltbMovieScheduleSeat()) {
                if (scheduleID.equals(mss.getSchedule().getScheduleID()) && !mss.isAvailable()) {
                    sold.add(mss.getSeat().getSeatID());
                }
            }
            // Hàng A-L
            int rowIndex = 0;
            for (char r = 'A'; r <= 'L'; r++, rowIndex++) {
                for (int c = 1; c <= 16; c++) {
                    gbc.gridx = c - 1;
                    gbc.gridy = rowIndex;
                    addSeatButton(String.format("%c%d", r, c), sold, gbc, 1);
                }
            }
            // Hàng ghế đôi M
            gbc.gridy = rowIndex;
            for (int i = 0; i < 8; i++) {
                gbc.gridx = i * 2;
                addSeatButton(String.format("M%02d-%02d", 2*i+1, 2*i+2), sold, gbc, 2);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        pnlSeats.revalidate();
        pnlSeats.repaint();
    }

    private void addSeatButton(String loc, Set<String> sold, GridBagConstraints gbc, int span) {
        JToggleButton btn = new JToggleButton(loc);
        btn.setOpaque(true);
        Seat seat = mapLoc.get(loc);
        String type = seat != null ? seat.getSeatType().getSeatTypeID() : "ST01";
        switch (type) {
            case "ST02": btn.setBackground(Color.YELLOW); break;
            case "ST03": btn.setBackground(new Color(255, 200, 200)); break;
            default:     btn.setBackground(Color.WHITE);
        }
        if (seat != null && sold.contains(seat.getSeatID())) {
            btn.setEnabled(false);
            btn.setBackground(Color.LIGHT_GRAY);
        }
        btn.addActionListener(e -> updateSelection());
        gbc.gridwidth = span;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnlSeats.add(btn, gbc);
    }

    private JPanel createLegend(String name, Color col) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        JButton b = new JButton();
        b.setPreferredSize(new Dimension(16, 16));
        b.setOpaque(true);
        b.setBackground(col);
        b.setEnabled(false);
        p.add(b);
        p.add(new JLabel(name));
        return p;
    }

    private void updateSelection() {
        ArrayList<String> sel = new ArrayList<>();
        double total = 0;
        for (Component c : pnlSeats.getComponents()) {
            if (c instanceof JToggleButton) {
                JToggleButton b = (JToggleButton) c;
                if (b.isSelected() && b.isEnabled()) {
                    sel.add(b.getText());
                    Seat seat = mapLoc.get(b.getText());
                    String type = seat != null ? seat.getSeatType().getSeatTypeID() : "ST01";
                    total += priceMap.getOrDefault(type, 60000.0);
                }
            }
        }
        String seatsText = String.join(", ", sel);
        lblSeats.setText("<html><body style='width:100px'>Ghế: " + seatsText + "</body></html>");
        lblTotal.setText(String.format("Tổng: %.0f VND", total));
    }

    public static void main(String[] args) {
        FlatLightLaf.setup();
        SwingUtilities.invokeLater(() -> {
            try {
                ConnectDB.getInstance().connect();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            new SeatSelectionForm().setVisible(true);
        });
    }
}
