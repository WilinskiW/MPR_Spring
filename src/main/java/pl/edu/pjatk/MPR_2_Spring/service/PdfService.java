package pl.edu.pjatk.MPR_2_Spring.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.stereotype.Service;
import pl.edu.pjatk.MPR_2_Spring.model.Car;

import java.io.File;
import java.io.IOException;

@Service
public class PdfService {

    public PdfService() {
    }

    public PDDocument createPdfInfo(Car car) {
        try {
            PDDocument doc = new PDDocument();

            PDPage page = new PDPage();
            doc.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(doc, page);

            //Begin the Content stream
            contentStream.beginText();

            //Setting the font to the Content stream
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);

            //Setting the leading
            contentStream.setLeading(14.5f);

            //Setting the position for the line
            contentStream.newLineAtOffset(25, 725);

            //Adding text in the form of string
            contentStream.showText("ID" + car.getId());
            contentStream.newLine();

            contentStream.showText("Marka" + car.getMake());
            contentStream.newLine();

            contentStream.showText("Kolor: " + car.getColor());
            contentStream.newLine();

            contentStream.showText("Kolor: " + car.getIdentification());
            //Ending the content stream
            contentStream.endText();

            //Closing the content stream
            contentStream.close();

            //Closing the document
            doc.close();
            return doc;
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }

        return new PDDocument();
    }
}
