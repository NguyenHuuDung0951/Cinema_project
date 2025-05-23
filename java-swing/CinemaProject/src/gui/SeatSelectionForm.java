package gui;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import connectDB.ConnectDB;
import dao.Seat_DAO;
import dao.MovieScheduleSeat_DAO;
import dao.MovieSchedule_DAO;
import dao.Room_DAO;
import entity.MovieSchedule;
import entity.Seat;
import entity.MovieScheduleSeat;
import entity.Room;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import model.BookingData;
import util.SeatPriceUtil;

public class SeatSelectionForm extends JFrame {

    private JPanel pnlScreen, pnlSeats, pnlInfo, pnlLegend;
    private JLabel lblTitle, lblDate, lblTime, lblRoom, lblPrice, lblSeats, lblTotal;
    private JButton btnContinue;

    private static final Color SELECTED_COLOR = new Color(0xFF8800);

    private Map<String, Seat> mapLoc = new HashMap<>();
    private String scheduleID;

    private static final int POSTER_MAX_W = 150;
    private static final Color ORANGE_BAR = new Color(0xFFA000);
    private JLabel lblSeatsVal;
    private JLabel lblTotalVal;
    private JLabel lblPoster;
    private String roomName;

    private ImageIcon loadScaledIcon(String path, int maxW) {
        URL url = getClass().getResource(path);
        if (url == null) {
            return new ImageIcon();
        }

        ImageIcon raw = new ImageIcon(url);
        int origW = raw.getIconWidth();
        int origH = raw.getIconHeight();

        int newW = Math.min(origW, maxW);
        int newH = origH * newW / origW;

        Image scaledImg = raw.getImage()
                .getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImg);
    }

    public SeatSelectionForm() {

        BookingData bd = BookingData.getInstance();
        scheduleID = bd.getScheduleID();
        initComponents();

        lblTitle.setText(bd.getMovieName());
        lblDate.setText(LocalDate.parse(bd.getShowDate())
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        lblTime.setText(bd.getShowTime());
        lblRoom.setText(bd.getRoomName());

        String posterPath = bd.getPosterPath();
        ImageIcon icon = loadScaledIcon(posterPath, POSTER_MAX_W);
        lblPoster.setIcon(icon);
    }

    private void initComponents() {
        setTitle("Chọn ghế xem phim");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel pnlTop = new JPanel();
        pnlTop.setLayout(new BoxLayout(pnlTop, BoxLayout.Y_AXIS));

        JPanel bar = new JPanel();
        bar.setBackground(ORANGE_BAR);
        bar.setPreferredSize(new Dimension(0, 2));
        bar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 2));

        JLabel lblScreen = new JLabel("Màn hình", SwingConstants.CENTER);
        lblScreen.setFont(lblScreen.getFont().deriveFont(Font.BOLD, 25f));
        lblScreen.setForeground(new Color(0x9E9E9E));
        lblScreen.setBorder(BorderFactory.createEmptyBorder(6, 0, 6, 0));

        JPanel pnlScreen = new JPanel(new BorderLayout());
        pnlScreen.add(lblScreen, BorderLayout.CENTER);

        pnlTop.add(bar);
        pnlTop.add(pnlScreen);

        add(pnlTop, BorderLayout.NORTH);

        pnlSeats = new JPanel(new GridBagLayout());
        pnlSeats.putClientProperty("FlatLaf.style", "background:#00000000;");

        loadSeats();
        JScrollPane scroll = new JScrollPane(pnlSeats);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        add(scroll, BorderLayout.CENTER);

        pnlInfo = new JPanel();
        pnlInfo.setLayout(new BoxLayout(pnlInfo, BoxLayout.Y_AXIS));
        pnlInfo.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        ImageIcon posterIcon = loadScaledIcon("/image/avenger.jpg", POSTER_MAX_W);
        lblPoster = new JLabel(posterIcon);
        lblPoster.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlInfo.add(lblPoster);

        pnlInfo.add(Box.createVerticalStrut(10));

        lblTitle = new JLabel("Venom: Kẻ Cuối");
        lblTitle.setFont(lblTitle.getFont().deriveFont(Font.BOLD, 16f));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlInfo.add(lblTitle);
        pnlInfo.add(Box.createVerticalStrut(15));

        JPanel pnlMeta = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = new Insets(10, 8, 4, 5);

        int row = 0;
        lblDate = new JLabel("17/11/2024");
        row = addRow(pnlMeta, gc, row, "Ngày chiếu:", lblDate);

        lblTime = new JLabel("19:47");
        row = addRow(pnlMeta, gc, row, "Giờ chiếu:", lblTime);

        lblRoom = new JLabel("Phòng 1");
        row = addRow(pnlMeta, gc, row, "Phòng:", lblRoom);

        row = addRow(pnlMeta, gc, row, "Giá vé:", formatVND(60000));
        row = addRow(pnlMeta, gc, row, "Ghế:", "");
        lblSeatsVal = (JLabel) pnlMeta.getComponent(pnlMeta.getComponentCount() - 1);
        row = addRow(pnlMeta, gc, row, "Tổng:", formatVND(0));
        lblTotalVal = (JLabel) pnlMeta.getComponent(pnlMeta.getComponentCount() - 1);
        pnlInfo.add(pnlMeta);
        pnlInfo.add(Box.createVerticalGlue());

        btnContinue = new JButton("Tiếp tục");
        btnContinue.putClientProperty("FlatLaf.style",
                "arc:999;"
                + "background:#FFA000;"
                + "foreground:#ffffff;"
                + "hoverBackground:#FFB733;");

        btnContinue.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnContinue.setMargin(new Insets(10, 16, 10, 16));
        pnlInfo.add(btnContinue);

        add(pnlInfo, BorderLayout.EAST);

        pnlLegend = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        pnlLegend.putClientProperty("FlatLaf.style", "background:#00000000;");
        pnlLegend.add(createLegend("Ghế thường", Color.WHITE));
        pnlLegend.add(createLegend("Ghế VIP", Color.YELLOW));
        pnlLegend.add(createLegend("Ghế đôi SweetBox", new Color(255, 200, 200)));
        pnlLegend.add(createLegend("Ghế đã bán", Color.LIGHT_GRAY));
        pnlLegend.add(createLegend("Ghế đang chọn", SELECTED_COLOR));

        add(pnlLegend, BorderLayout.SOUTH);
        btnContinue.addActionListener(e -> {
            BookingData bd = BookingData.getInstance();

            String selectedSeats = lblSeatsVal.getText().replaceAll("<[^>]*>", "");
            bd.setSelectedSeats(selectedSeats);

            bd.setTicketTotal(parseVND(lblTotalVal.getText()));

            new ProductOrderForm().setVisible(true);
            dispose();
        });

        pack();
        setSize(1300, 700);
        setLocationRelativeTo(null);
    }

    private double parseVND(String vndText) {
        try {
            return NumberFormat.getNumberInstance(new Locale("vi", "VN"))
                    .parse(vndText.replace("VND", "").trim()).doubleValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private void loadSeats() {
        pnlSeats.removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 3, 3);
        try {

            ArrayList<Seat> seats = new Seat_DAO().getalltbSeat();
            mapLoc = seats.stream().collect(Collectors.toMap(Seat::getLocation, s -> s));

            Set<String> sold = new HashSet<>();
            for (MovieScheduleSeat mss : new MovieScheduleSeat_DAO().getalltbMovieScheduleSeat()) {
                if (scheduleID.equals(mss.getSchedule().getScheduleID()) && !mss.isAvailable()) {
                    sold.add(mss.getSeat().getSeatID());
                }
            }

            int rowIndex = 0;
            for (char r = 'A'; r <= 'L'; r++, rowIndex++) {
                for (int c = 1; c <= 16; c++) {
                    gbc.gridx = c - 1;
                    gbc.gridy = rowIndex;
                    addSeatButton(String.format("%c%d", r, c), sold, gbc, 1);
                }
            }

            gbc.gridy = rowIndex;
            for (int i = 0; i < 8; i++) {
                gbc.gridx = i * 2;
                addSeatButton(String.format("M%02d-%02d", 2 * i + 1, 2 * i + 2), sold, gbc, 2);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        pnlSeats.revalidate();
        pnlSeats.repaint();
    }

    private void addSeatButton(String loc, Set<String> sold, GridBagConstraints gbc, int span) {
        JToggleButton btn = new JToggleButton(loc);
        btn.setPreferredSize(new Dimension(56, 32));
        btn.setMinimumSize(new Dimension(56, 32));
        btn.setMaximumSize(new Dimension(56, 32));
        btn.putClientProperty("FlatLaf.style",
                "arc:8;"
                + "focusWidth:0;"
                + "selectedBackground:#ff8800;"
                + "selectedForeground:#ffffff;"
                + "hoverBackground:#FFE2AD;"
        );

        btn.setOpaque(true);
        Seat seat = mapLoc.get(loc);
        String type = seat != null ? seat.getSeatType().getSeatTypeID() : "ST01";
        switch (type) {
            case "ST02":
                btn.setBackground(Color.YELLOW);
                break;
            case "ST03":
                btn.setBackground(new Color(255, 200, 200));
                break;
            default:
                btn.setBackground(Color.WHITE);
        }
        if (seat != null && sold.contains(seat.getSeatID())) {
            btn.putClientProperty("FlatLaf.style",
                    "disabledBackground:#C0C0C0;");
            btn.setEnabled(false);
        }
        btn.addActionListener(e -> updateSelection());
        gbc.gridwidth = span;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        pnlSeats.add(btn, gbc);
    }

    private JPanel createLegend(String name, Color col) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));

        JButton legend = new JButton();
        legend.setPreferredSize(new Dimension(16, 16));
        legend.setFocusable(false);
        legend.setBorderPainted(false);

        String colorHex = String.format("#%02X%02X%02X",
                col.getRed(), col.getGreen(), col.getBlue());
        legend.putClientProperty("FlatLaf.style",
                "arc:4;"
                + "borderWidth:0;"
                + "focusWidth:0;"
                + "background:" + colorHex + ";");

        JLabel lbl = new JLabel(name);

        p.setOpaque(false);
        p.add(legend);
        p.add(lbl);
        return p;
    }

    private void updateSelection() {
        ArrayList<String> sel = new ArrayList<>();
        double total = 0;

        for (Component c : pnlSeats.getComponents()) {
            if (!(c instanceof JToggleButton)) {
                continue;
            }
            JToggleButton b = (JToggleButton) c;

            if (!b.isEnabled()) {
                continue;
            }

            Seat seat = mapLoc.get(b.getText());
            String type = seat != null ? seat.getSeatType().getSeatTypeID() : "ST01";

            if (b.isSelected()) {
                b.setBackground(SELECTED_COLOR);
                b.setForeground(Color.WHITE);
                sel.add(b.getText());
                double price = SeatPriceUtil.getPriceByType(type);
                total += price;
            } else {
                b.setForeground(Color.BLACK);
                switch (type) {
                    case "ST02":
                        b.setBackground(Color.YELLOW);
                        break;
                    case "ST03":
                        b.setBackground(new Color(255, 200, 200));
                        break;
                    default:
                        b.setBackground(Color.WHITE);
                        break;
                }
            }
        }

        lblSeatsVal.setText("<html><body style='width:90px'>"
                + String.join(", ", sel) + "</body></html>");
        lblTotalVal.setText(String.format(formatVND(total)));
    }

    public static void main(String[] args) {
        FlatMacLightLaf.setup();
        SwingUtilities.invokeLater(() -> {
            try {
                ConnectDB.getInstance().connect();

                ArrayList<MovieSchedule> list = new MovieSchedule_DAO().getalltbMovieSchedule();
                if (list.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Chưa có lịch chiếu.");
                    return;
                }
                MovieSchedule s = list.get(0);
                BookingData bd = BookingData.getInstance();
                bd.setScheduleID(s.getScheduleID());
                bd.setMovieID(s.getMovie().getMovieID());
                bd.setMovieName(s.getMovie().getMovieName());
                bd.setRoomID(s.getRoom().getRoomID());
                bd.setRoomName(s.getRoom().getRoomName());
                bd.setShowDate(s.getStartTime().toLocalDate().toString());
                bd.setShowTime(s.getStartTime().toLocalTime().toString());

                new SeatSelectionForm().setVisible(true);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private int addRow(JPanel panel, GridBagConstraints gc,
            int row, String key, String val) {
        JLabel lblKey = new JLabel(key);
        lblKey.setFont(lblKey.getFont().deriveFont(Font.PLAIN, 13f));

        JLabel lblVal = new JLabel(val);
        lblVal.setFont(lblVal.getFont().deriveFont(Font.BOLD, 13f));

        gc.gridx = 0;
        gc.gridy = row;
        gc.weightx = 0;
        panel.add(lblKey, gc);

        gc.gridx = 1;
        gc.weightx = 1;
        panel.add(lblVal, gc);

        return row + 1;
    }

    private String formatVND(double amount) {
        NumberFormat nf = NumberFormat.getIntegerInstance(new Locale("vi", "VN"));
        return nf.format(amount) + " VND";
    }

    private int addRow(JPanel panel, GridBagConstraints gc, int row, String key, JLabel lblVal) {
        JLabel lblKey = new JLabel(key);
        lblKey.setFont(lblKey.getFont().deriveFont(Font.PLAIN, 13f));
        lblVal.setFont(lblVal.getFont().deriveFont(Font.BOLD, 13f));

        gc.gridx = 0;
        gc.gridy = row;
        gc.weightx = 0;
        panel.add(lblKey, gc);

        gc.gridx = 1;
        gc.weightx = 1;
        panel.add(lblVal, gc);

        return row + 1;
    }

}
