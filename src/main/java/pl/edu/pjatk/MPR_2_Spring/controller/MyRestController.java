package pl.edu.pjatk.MPR_2_Spring.controller;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjatk.MPR_2_Spring.model.Car;
import pl.edu.pjatk.MPR_2_Spring.service.CarService;
import pl.edu.pjatk.MPR_2_Spring.service.PdfService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

//Ta adnotacja będzie się komunikowała z siecią (Wystawiamy ją na sieć)
//Dostajemy żądanie HTTP od klienta i kontroler je odbiera. Kontroler jedynie odsyła logikę do dalszych komponentów.

@RestController
public class MyRestController {
    private final CarService carService;
    private final PdfService pdfService;

    @Autowired   //Autowired - dzięki niemu spring tworzy automatycznie nowy obiekt, kiedy go potrzebuje
    public MyRestController(CarService carService, PdfService pdfService) {
        this.carService = carService;
        this.pdfService = pdfService;
    }

    //Przeglądarka zawsze działa na GET i POST
    //GET - żądamy o zasób
    //POST - wsadzamy nowe dane
    //JSON to zazwyczaj klucz wartość
    //Struktura JSON'a:
    //{
    //  klucz = wartość
    //}

    //GET
    @GetMapping("cars/all")
    public ResponseEntity<Iterable<Car>> getAll() {
        return new ResponseEntity<>(this.carService.getCarsList(), HttpStatus.OK);
    }

    @GetMapping("cars/make/{make}")
    public ResponseEntity<List<Car>> getAllByMake(@PathVariable String make) {
        //podnosimy pierwsza litere
        return new ResponseEntity<>(this.carService.getCarsByMake(make.substring(0, 1).toUpperCase()
                + make.substring(1)), HttpStatus.OK);
    }

    @GetMapping("cars/color/{color}")
    public ResponseEntity<List<Car>> getAllByColor(@PathVariable String color) {
        //podnosimy pierwsza litere
        return new ResponseEntity<>(this.carService.getCarsByColor(color.substring(0, 1).toUpperCase()
                + color.substring(1)), HttpStatus.OK);
    }

    //GET
    @GetMapping("cars/{id}")
    public ResponseEntity<Car> get(@PathVariable Integer id) {
        return new ResponseEntity<>(this.carService.getCar(id), HttpStatus.OK);
    }

    @RequestMapping(value = "cars/pdf/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getPDFBox(@PathVariable Integer id) throws IOException {

        ByteArrayInputStream inputStream = null;
        try (PDDocument document = pdfService.createPdfInfo(this.carService.getCar(id))) {
            ByteArrayOutputStream ous = document.getDocument().
            document.save(ous);
            document.close();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=example.pdf");

            byte[] bytes = ous.toByteArray();

            inputStream = new ByteArrayInputStream(bytes);
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(inputStream));

        } catch (Exception e) {
            System.out.println("Something went wrong: " + e.getMessage());
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return ResponseEntity.ok().build();
    }

    //POST - będziemy tu dodawać nowe obiekty typu Car
    @PostMapping("cars")
    public ResponseEntity<Void> add(@RequestBody Car car) {
        this.carService.add(car);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("cars/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        this.carService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("cars/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody Car car) {
        this.carService.update(id, car);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
