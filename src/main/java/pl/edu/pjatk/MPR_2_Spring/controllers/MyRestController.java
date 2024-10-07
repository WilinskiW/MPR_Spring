package pl.edu.pjatk.MPR_2_Spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjatk.MPR_2_Spring.model.Car;
import pl.edu.pjatk.MPR_2_Spring.services.CarService;

import java.util.List;

//Ta adnotacja będzie się komunikowała z siecią (Wystawiamy ją na sieć)
//Dostajemy żądanie HTTP od klienta i kontroler je odbiera. Kontroler jedynie odsyła logikę do dalszych komponentów.

@RestController
public class MyRestController {
    private CarService carService;

    @Autowired   //Autowired - dzięki niemu spring tworzy automatycznie nowy obiekt, kiedy go potrzebuje
    public MyRestController(CarService carService) {
        this.carService = carService;
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
    public List<Car> getAll() {
        return carService.getCarsList();
    }

    //GET
    @GetMapping("cars/{id}")
    public Car get(@PathVariable Integer id) {
        return this.carService.getCar(id);
    }

    //POST - będziemy tu dodawać nowe obiekty typu Car
    @PostMapping("cars")
    public void add(@RequestBody Car car) {
        carService.add(car);
    }

    @DeleteMapping("cars/{id}")
    public void delete(@PathVariable int id) {
        carService.delete(id);
    }

    @PutMapping("cars/{id}")
    public void update(@PathVariable int id, @RequestBody Car car) {
        carService.update(id, car);
    }
}
