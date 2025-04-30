package util;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.element.Table;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.events.PdfDocumentEvent;
//import com.itextpdf.kernel.events.PdfDocumentEventDispatcher;
//import com.itextpdf.kernel.events.PdfEvent;
//import com.itextpdf.kernel.events.PdfEventListener;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.*;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import entity.CartItem;
import entity.TicketDetail;
import model.BookingData;

import java.io.File;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class PDFGenerator {

    private static final String FONT = "resources/fonts/NotoSans-Regular.ttf";
    private static final String FONT_B = "resources/fonts/NotoSans-Bold.ttf";

    /**
     * Xuất hóa đơn chung
     */
    public static void generateInvoicePDF(String dest) throws Exception {
        BookingData bd = BookingData.getInstance();
        File file = new File(dest);
        file.getParentFile().mkdirs();

        try (PdfWriter writer = new PdfWriter(dest); PdfDocument pdf = new PdfDocument(writer)) {
            PdfFont font = PdfFontFactory.createFont(FONT, PdfEncodings.IDENTITY_H, true);
            PdfFont fontB = PdfFontFactory.createFont(FONT_B, PdfEncodings.IDENTITY_H, true);

            Document doc = new Document(pdf);
            doc.setFont(font);
            doc.setFontSize(12);
            // Tiêu đề
            doc.add(new Paragraph("HÓA ĐƠN THANH TOÁN")
                    .setFontSize(16)
                    .setFont(fontB)
                    .setTextAlignment(TextAlignment.CENTER));

            doc.add(new Paragraph("\n"));

            // Thông tin chung
            Table info = new Table(UnitValue.createPercentArray(new float[]{2, 4}));
            info.setWidth(UnitValue.createPercentValue(100));
            info.addCell(cell("Phim:"));
            info.addCell(cell(bd.getMovieName()));
            info.addCell(cell("Ngày chiếu:"));
            info.addCell(cell(bd.getShowDate() + " " + bd.getShowTime()));
            info.addCell(cell("Phòng:"));
            info.addCell(cell(bd.getRoomName()));
            info.addCell(cell("Ghế:"));
            info.addCell(cell(bd.getSelectedSeats()));
            info.addCell(cell("Voucher:"));
            info.addCell(cell(bd.getVoucherID() != null ? bd.getVoucherID() : "Không"));
            doc.add(info);

            doc.add(new Paragraph("\n"));

            // Bảng chi tiết vé
            doc.add(new Paragraph("Chi tiết vé").setBold());
            Table tkt = new Table(UnitValue.createPercentArray(new float[]{4, 2}));
            tkt.setWidth(UnitValue.createPercentValue(100));
            tkt.addHeaderCell(header("Vị trí"));
            tkt.addHeaderCell(header("Giá"));
            for (TicketDetail t : bd.getLastTickets()) {
                tkt.addCell(cell(t.getSeat().getLocation()));
                tkt.addCell(cell(formatVND(t.getTicketPrice())));
            }
            doc.add(tkt);

            doc.add(new Paragraph("\n"));

            // Bảng chi tiết món ăn
            doc.add(new Paragraph("Đồ ăn & uống").setBold());
            Table food = new Table(UnitValue.createPercentArray(new float[]{4, 1, 2}));
            food.setWidth(UnitValue.createPercentValue(100));
            food.addHeaderCell(header("Tên SP"));
            food.addHeaderCell(header("SL"));
            food.addHeaderCell(header("Thành tiền"));
            for (CartItem ci : bd.getCartItems()) {
                food.addCell(cell(ci.getProduct().getProductName()));
                food.addCell(cell(String.valueOf(ci.getQuantity())));
                food.addCell(cell(formatVND(ci.getProduct().getPrice() * ci.getQuantity())));
            }
            doc.add(food);

            doc.add(new Paragraph("\n"));

            // Tóm tắt thanh toán
            Table sum = new Table(UnitValue.createPercentArray(new float[]{6, 2}));
            sum.setWidth(UnitValue.createPercentValue(100));
            sum.addCell(cell("Tổng vé:"));
            sum.addCell(cell(formatVND(bd.getTicketTotal())));
            sum.addCell(cell("Tổng ăn uống:"));
            sum.addCell(cell(formatVND(bd.getProductTotal())));
            double discount = PromotionUtil.findBestDiscount(bd.getTicketTotal() + bd.getProductTotal());
            sum.addCell(cell("Khuyến mãi:"));
            sum.addCell(cell((int) (discount * 100) + "%"));
            double after = (bd.getTicketTotal() + bd.getProductTotal()) * (1 - discount);
            sum.addCell(cell("VAT 10%:"));
            sum.addCell(cell(formatVND(after * 0.1)));
            sum.addCell(cell("Tổng thanh toán:").setBold());
            sum.addCell(cell(formatVND(after * 1.1)).setBold());
            doc.add(sum);
        }
    }

    /**
     * Xuất từng vé riêng biệt
     */
    public static void generateTicketPDFs(String folder) throws Exception {
        BookingData bd = BookingData.getInstance();
        // Giả sử bạn có list TicketDetail lưu lại ở PaymentService, hoặc lấy trực tiếp:
        List<TicketDetail> tickets = bd.getLastTickets();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (TicketDetail t : tickets) {
            String file = folder + "/Ticket_" + t.getSeat().getLocation() + ".pdf";
            new File(folder).mkdirs();
            try (PdfWriter writer = new PdfWriter(file); PdfDocument pdf = new PdfDocument(writer)) {
                PdfFont font = PdfFontFactory.createFont(FONT, PdfEncodings.IDENTITY_H, true);
                PdfFont fontB = PdfFontFactory.createFont(FONT_B, PdfEncodings.IDENTITY_H, true);

                Document doc = new Document(pdf);
                doc.setFont(font);
                doc.setFontSize(12);
                String title = t.getSeat().getLocation();
                doc.add(new Paragraph(title).setFont(fontB).setFontSize(16).setTextAlignment(TextAlignment.CENTER));
                doc.add(new Paragraph("\n"));

                Table info = new Table(UnitValue.createPercentArray(new float[]{3, 5}));
                info.setWidth(UnitValue.createPercentValue(100));
                info.addCell(cell("Phim:"));
                info.addCell(cell(bd.getMovieName()));
                info.addCell(cell("Phòng:"));
                info.addCell(cell(bd.getRoomName()));
                info.addCell(cell("Ngày giờ:"));
                info.addCell(cell(fmt.format(t.getShowDate())));
                info.addCell(cell("Vị trí:"));
                info.addCell(cell(t.getSeat().getLocation()));
                info.addCell(cell("Giá vé:"));
                info.addCell(cell(formatVND(t.getTicketPrice())));
                doc.add(info);
            }
        }
    }

    // Helpers
    private static Cell header(String txt) {
        return new Cell().add(new Paragraph(txt))
                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                .setBold();
    }

    private static Cell cell(String txt) {
        return new Cell().add(new Paragraph(txt));
    }

    private static String formatVND(double amt) {
        NumberFormat nf = NumberFormat.getNumberInstance(new Locale("vi", "VN"));
        return nf.format(amt) + " VND";
    }
}
