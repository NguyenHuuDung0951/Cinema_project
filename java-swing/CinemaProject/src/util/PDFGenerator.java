package util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.*;
import entity.CartItem;
import entity.TicketDetail;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import model.BookingData;
import java.io.File;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import javax.imageio.ImageIO;

public class PDFGenerator {

    private static final String FONT = "resources/fonts/NotoSans-Regular.ttf";
    private static final String FONT_B = "resources/fonts/NotoSans-Bold.ttf";

    /**
     * In HÓA ĐƠN
     */
    public static void generateInvoicePDF(String dest) throws Exception {
        BookingData bd = BookingData.getInstance();
        new File(dest).getParentFile().mkdirs();
        PageSize page = new PageSize(400, 1300);
        try (PdfWriter writer = new PdfWriter(dest); PdfDocument pdf = new PdfDocument(writer); Document doc = new Document(pdf, page)) {

            PdfFont f = PdfFontFactory.createFont(FONT, PdfEncodings.IDENTITY_H, true);
            PdfFont fb = PdfFontFactory.createFont(FONT_B, PdfEncodings.IDENTITY_H, true);
            doc.setFont(f).setFontSize(12).setMargins(10, 10, 10, 10);

            // --- HEADER ---
            doc.add(new Paragraph("HÓA ĐƠN")
                    .setFont(fb).setFontSize(30)
                    .setTextAlignment(TextAlignment.CENTER));
            doc.add(new Paragraph("RẠP CHIẾU PHIM CGV")
                    .setFontSize(12).setTextAlignment(TextAlignment.CENTER));
            doc.add(new Paragraph("12 Nguyễn Văn Bảo, P.4, Q.Gò Vấp")
                    .setFontSize(12).setTextAlignment(TextAlignment.CENTER));
            doc.add(new Paragraph("=".repeat(47))
                    .setTextAlignment(TextAlignment.CENTER));

            // --- Thông tin phim ---
            doc.add(new Paragraph("Thông tin phim")
                    .setFont(fb).setFontSize(16));
            doc.add(new Paragraph("Phim: " + bd.getMovieName()));
            doc.add(new Paragraph("Thời gian: " + bd.getShowDate() + "  " + bd.getShowTime()));
            doc.add(new Paragraph("Phòng: " + bd.getRoomName()));
            doc.add(new Paragraph("=".repeat(47))
                    .setTextAlignment(TextAlignment.CENTER));

            // --- Ghế ---
            Table seatTable = new Table(UnitValue.createPercentArray(new float[]{3, 1}))
                    .useAllAvailableWidth()
                    .setBorder(Border.NO_BORDER);
            seatTable.addHeaderCell(new Cell().add(new Paragraph("Ghế"))
                    .setBorder(Border.NO_BORDER).setBold());
            seatTable.addHeaderCell(new Cell().add(new Paragraph("Thành tiền"))
                    .setBorder(Border.NO_BORDER)
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setBold());
            for (TicketDetail t : bd.getLastTickets()) {
                seatTable.addCell(new Cell().add(new Paragraph(t.getSeat().getLocation()))
                        .setBorder(Border.NO_BORDER));
                seatTable.addCell(new Cell().add(new Paragraph(formatVND(t.getTicketPrice())))
                        .setBorder(Border.NO_BORDER)
                        .setTextAlignment(TextAlignment.RIGHT));
            }
            doc.add(seatTable);

            doc.add(new Paragraph("=".repeat(47))
                    .setTextAlignment(TextAlignment.CENTER));

            // --- Đồ ăn & uống ---
            Table foodTable = new Table(UnitValue.createPercentArray(new float[]{5, 1, 2}))
                    .useAllAvailableWidth()
                    .setBorder(Border.NO_BORDER);
            foodTable.addHeaderCell(new Cell().add(new Paragraph("Đồ ăn & uống"))
                    .setBorder(Border.NO_BORDER).setBold());
            foodTable.addHeaderCell(new Cell().add(new Paragraph("SL"))
                    .setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER).setBold());
            foodTable.addHeaderCell(new Cell().add(new Paragraph("Thành tiền"))
                    .setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.RIGHT).setBold());
            for (CartItem ci : bd.getCartItems()) {
                String name = ci.getProduct().getProductName();
                foodTable.addCell(new Cell().add(new Paragraph(name))
                        .setBorder(Border.NO_BORDER));
                foodTable.addCell(new Cell().add(new Paragraph(String.valueOf(ci.getQuantity())))
                        .setBorder(Border.NO_BORDER)
                        .setTextAlignment(TextAlignment.CENTER));
                foodTable.addCell(new Cell().add(new Paragraph(formatVND(
                        ci.getProduct().getPrice() * ci.getQuantity())))
                        .setBorder(Border.NO_BORDER)
                        .setTextAlignment(TextAlignment.RIGHT));
            }
            doc.add(foodTable);

            doc.add(new Paragraph("=".repeat(47))
                    .setTextAlignment(TextAlignment.CENTER));

            // --- Tổng cộng ---
            doc.add(new Paragraph("Chi tiết thanh toán")
                    .setFont(fb).setFontSize(16));

            float[] colWidths = {3, 1};
            Table sum = new Table(UnitValue.createPercentArray(colWidths))
                    .useAllAvailableWidth();

            sum.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
            sum.addCell(new Cell().add(new Paragraph("Thành tiền"))
                    .setBorder(Border.NO_BORDER)
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setBold());

            double ticketTotal = bd.getTicketTotal();
            double foodTotal = bd.getProductTotal();
            double discount = bd.getDiscountAmount();
            double vat = (ticketTotal + foodTotal - discount) * 0.1;

            sum.addCell(cellNoBorder("Tổng tiền vé"))
                    .addCell(cellNoBorder(formatVND(ticketTotal)).setTextAlignment(TextAlignment.RIGHT));
            sum.addCell(cellNoBorder("Tổng tiền đồ ăn & uống"))
                    .addCell(cellNoBorder(formatVND(foodTotal)).setTextAlignment(TextAlignment.RIGHT));
            sum.addCell(cellNoBorder("VAT 10%"))
                    .addCell(cellNoBorder(formatVND(vat)).setTextAlignment(TextAlignment.RIGHT));
            sum.addCell(cellNoBorder("Khuyến mãi"))
                    .addCell(cellNoBorder(formatVND(-discount)).setTextAlignment(TextAlignment.RIGHT));

            doc.add(sum);

            doc.add(new Paragraph("=".repeat(47))
                    .setTextAlignment(TextAlignment.CENTER));

            double grandTotal = ticketTotal + foodTotal + vat - discount;
            doc.add(new Paragraph("Tổng thanh toán: " + formatVND(grandTotal))
                    .setFont(fb).setFontSize(16)
                    .setTextAlignment(TextAlignment.RIGHT));
            doc.add(new Paragraph("\n"));

            byte[] qrBytes = bd.getQrCodeImageBytes();
            if (qrBytes != null) {
                Image qr = new Image(ImageDataFactory.create(qrBytes))
                        .setWidth(130)
                        .setHorizontalAlignment(HorizontalAlignment.CENTER);
                doc.add(qr);
            }

            doc.close();
        }
    }

    /**
     * In từng VÉ riêng
     */
    public static void generateTicketPDFs(String folder) throws Exception {
        BookingData bd = BookingData.getInstance();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        new File(folder).mkdirs();
        PageSize page = new PageSize(400, 700);
        for (TicketDetail t : bd.getLastTickets()) {
            String path = folder + "/Ticket_" + t.getSeat().getLocation() + ".pdf";
            try (PdfWriter writer = new PdfWriter(path); PdfDocument pdf = new PdfDocument(writer); Document doc = new Document(pdf, page)) {

                PdfFont f = PdfFontFactory.createFont(FONT, PdfEncodings.IDENTITY_H, true);
                PdfFont fb = PdfFontFactory.createFont(FONT_B, PdfEncodings.IDENTITY_H, true);
                doc.setFont(f).setFontSize(12).setMargins(20, 20, 20, 20);

                // --- HEADER ---
                doc.add(new Paragraph("Vé")
                        .setFont(fb).setFontSize(18)
                        .setTextAlignment(TextAlignment.CENTER));
                doc.add(new Paragraph("Rạp Chiếu Phim CGV")
                        .setFontSize(14).setTextAlignment(TextAlignment.CENTER));
                doc.add(new Paragraph("12 Nguyễn Văn Bảo, Phường 4, Quận Gò Vấp, TP. Hồ Chí Minh")
                        .setFontSize(10).setTextAlignment(TextAlignment.CENTER));
                doc.add(new Paragraph("=".repeat(50))
                        .setTextAlignment(TextAlignment.CENTER));

                // --- MÃ GHẾ TO ---
                doc.add(new Paragraph(t.getSeat().getLocation())
                        .setFont(fb).setFontSize(32)
                        .setTextAlignment(TextAlignment.CENTER));

                // --- THÔNG TIN VÉ ---
                doc.add(new Paragraph("Thông tin vé")
                        .setFont(fb).setFontSize(16));
                doc.add(new Paragraph("Phim: " + bd.getMovieName()));
                doc.add(new Paragraph("Thời gian: " + bd.getShowDate() + "  " + bd.getShowTime()));
                doc.add(new Paragraph("Phòng: " + bd.getRoomName()));
                doc.add(new Paragraph("Giá vé: " + t.getTicketPrice()));
                doc.add(new Paragraph("=".repeat(47))
                        .setTextAlignment(TextAlignment.CENTER));

                byte[] qrBytes = null;
                try {
                    BitMatrix matrix = new MultiFormatWriter()
                            .encode(
                                    t.getTicketID(),
                                    BarcodeFormat.QR_CODE,
                                    100, 100
                            );
                    BufferedImage img = MatrixToImageWriter.toBufferedImage(matrix);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(img, "PNG", baos);
                    qrBytes = baos.toByteArray();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (qrBytes != null) {
                    Image qrImg = new Image(ImageDataFactory.create(qrBytes))
                            .setWidth(100)
                            .setHorizontalAlignment(HorizontalAlignment.CENTER);
                    doc.add(qrImg);
                }
                doc.close();
            }
        }
    }

    // helper cells
    private static Cell header(String txt) {
        return new Cell()
                .add(new Paragraph(txt))
                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                .setBold();
    }

    private static Cell cell(String txt) {
        return new Cell()
                .add(new Paragraph(txt));
    }

    private static String formatVND(double amt) {
        return NumberFormat.getIntegerInstance(
                new Locale("vi", "VN")
        ).format(amt) + " VND";
    }

    private static Cell cellNoBorder(String txt) {
        return new Cell()
                .add(new Paragraph(txt))
                .setBorder(Border.NO_BORDER);
    }

}
