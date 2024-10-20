package pl.edu.pjatk.MPR_2_Spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjatk.MPR_2_Spring.model.Car;
import pl.edu.pjatk.MPR_2_Spring.service.CarService;

import java.util.List;
import java.util.Optional;

//Ta adnotacja będzie się komunikowała z siecią (Wystawiamy ją na sieć)
//Dostajemy żądanie HTTP od klienta i kontroler je odbiera. Kontroler jedynie odsyła logikę do dalszych komponentów.

@RestController
public class MyRestController {
    private final CarService carService;

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
    public Iterable<Car> getAll() {
        return carService.getCarsList();
    }

    @GetMapping("cars/make/{make}")
    public List<Car> getAllByMake(@PathVariable String make) {
        //podnosimy pierwsza litere
        return this.carService.getCarsByMake(make.substring(0,1).toUpperCase() + make.substring(1));
    }

    @GetMapping("cars/color/{color}")
    public List<Car> getAllByColor(@PathVariable String color) {
        //podnosimy pierwsza litere
        return this.carService.getCarsByColor(color.substring(0,1).toUpperCase() + color.substring(1));
    }

    //GET
    @GetMapping("cars/{id}")
    public Optional<Car> get(@PathVariable Integer id) {
        return this.carService.getCar(id);
    }

    //POST - będziemy tu dodawać nowe obiekty typu Car
    @PostMapping("cars")
    public void add(@RequestBody Car car) {
        carService.add(car);
    }

    @DeleteMapping("cars/{id}")
    public void delete(@PathVariable long id) {
        carService.delete(id);
    }

    @PutMapping("cars/{id}")
    public void update(@PathVariable long id, @RequestBody Car car) {
        carService.update(id, car);
    }
}
