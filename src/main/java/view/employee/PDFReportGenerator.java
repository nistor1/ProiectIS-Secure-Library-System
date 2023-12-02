package view.employee;

import model.Order;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.List;

public class PDFReportGenerator {

    public static void generatePDFReport(List<Order> orders) {
        String outputPath = "pdf/ordersReport.pdf";
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {

                int offset = 700;
                for (Order o : orders) {
                    contentStream.setFont(PDType1Font.TIMES_ROMAN, 9);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(10, offset);
                    contentStream.showText(o.toString());
                    offset -= 20;
                    contentStream.newLineAtOffset(10, offset);
                    contentStream.endText();
                }

            }
            document.save(outputPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}