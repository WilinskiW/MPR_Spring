package pl.edu.pjatk.MPR_2_Spring.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.stereotype.Service;
import pl.edu.pjatk.MPR_2_Spring.model.Car;

import java.io.IOException;

@Service
public class PdfService {

    public PDDocument createPdfInfo(Car car) throws IOException {
        PDDocument doc = new PDDocument();
        try {
            PDPage page = new PDPage();
            doc.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(doc, page);

            contentStream.beginText();
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
            contentStream.setLeading(14.5f);
            contentStream.newLineAtOffset(25, 725);

            contentStream.showText("ID: " + car.getId());
            contentStream.newLine();
            contentStream.showText("Marka: " + car.getMake());
            contentStream.newLine();
            contentStream.showText("Kolor: " + car.getColor());
            contentStream.newLine();
            contentStream.showText("Identyfikacja: " + car.getIdentification());
            contentStream.endText();

            contentStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return doc;
    }
}
