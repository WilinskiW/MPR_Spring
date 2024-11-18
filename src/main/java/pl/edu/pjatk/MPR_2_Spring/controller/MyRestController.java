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

@RestController
@RequestMapping("/cars")
public class MyRestController {
    private final CarService carService;
    private final PdfService pdfService;

    @Autowired
    public MyRestController(CarService carService, PdfService pdfService) {
        this.carService = carService;
        this.pdfService = pdfService;
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<Car>> getAll() {
        return new ResponseEntity<>(this.carService.getCarsList(), HttpStatus.OK);
    }

    @GetMapping("/make/{make}")
    public ResponseEntity<List<Car>> getAllByMake(@PathVariable String make) {
        return new ResponseEntity<>(this.carService.getCarsByMake(make), HttpStatus.OK);
    }

    @GetMapping("/color/{color}")
    public ResponseEntity<List<Car>> getAllByColor(@PathVariable String color) {
        return new ResponseEntity<>(this.carService.getCarsByColor(color), HttpStatus.OK);
    }

    //GET
    @GetMapping("/{id}")
    public ResponseEntity<Car> get(@PathVariable Integer id) {
        return new ResponseEntity<>(this.carService.getCar(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/pdf/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getPDFBox(@PathVariable Integer id) {
        Car car = this.carService.getCar(id);
        try {
            PDDocument document = pdfService.createPdfInfo(car);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.save(outputStream);
            document.close();

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=car_info.pdf");

            ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(inputStream));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }


    @PostMapping("/")
    public ResponseEntity<Void> add(@RequestBody Car car) {
        this.carService.add(car);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        this.carService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody Car car) {
        this.carService.update(id, car);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
