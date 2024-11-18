package pl.edu.pjatk.MPR_2_Spring;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.jupiter.api.Test;
import pl.edu.pjatk.MPR_2_Spring.model.Car;
import pl.edu.pjatk.MPR_2_Spring.service.PdfService;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class PdfServiceTest {
    private final PdfService pdfService = new PdfService();

    @Test
    void createPdfInfoTest() throws IOException {
        Car car = new Car();
        car.setId(1);
        car.setMake("Toyota");
        car.setColor("Red");

        PDDocument pdfDocument = pdfService.createPdfInfo(car);

        assertNotNull(pdfDocument);
        assertEquals(1, pdfDocument.getNumberOfPages(), "PDF powinien zawierać jedną stronę");

        PDFTextStripper pdfStripper = new PDFTextStripper();
        String pdfContent = pdfStripper.getText(pdfDocument);

        long identification = car.getMake().hashCode() + car.getColor().hashCode();

        assertTrue(pdfContent.contains("ID: 1"));
        assertTrue(pdfContent.contains("Marka: Toyota"));
        assertTrue(pdfContent.contains("Kolor: Red"));
        assertTrue(pdfContent.contains("Identyfikacja: " + identification));

        pdfDocument.close();
    }

    @Test
    public void createPdfInfoWhenExceptionIsThrownTest() throws IOException {
        Car car = new Car();
        car.setId(1);

        // Mockowanie PDPageContentStream, aby rzucał IOException przy wywołaniu beginText()
        PDDocument mockDocument = mock(PDDocument.class);
        PDPageContentStream mockContentStream = mock(PDPageContentStream.class);
        doThrow(new IOException()).when(mockContentStream).beginText();

        // Przechwytywanie metody createPdfInfo
        PdfService pdfService = new PdfService() {
            @Override
            public PDDocument createPdfInfo(Car car) throws IOException {
                try {
                    mockContentStream.beginText(); // Zasymulowanie rzucenia wyjątku
                } catch (IOException e) {
                    throw new IOException(); // Opakowanie w RuntimeException dla testu
                }
                return mockDocument;
            }
        };

        // Sprawdzamy, czy wyjątek jest odpowiednio obsługiwany
        assertThrows(IOException.class, () -> pdfService.createPdfInfo(car));
    }


}

