package pl.edu.pjatk.MPR_2_Spring.service;

import org.springframework.stereotype.Service;
import pl.edu.pjatk.MPR_2_Spring.model.Car;
import pl.edu.pjatk.MPR_2_Spring.repository.CarRepository;

import java.util.List;
import java.util.Optional;

//Pakiet "services" - dodajemy do niego wszelkie komponenty, które coś obsługują
//Przykład - kucharz w restauracji
@Service
public class CarService {
    private final CarRepository repository;
    private final StringUtilsService stringUtilsService;

    public CarService(CarRepository repository, StringUtilsService stringUtilsService) {
        this.repository = repository;
        this.stringUtilsService = stringUtilsService;
        initializeCars();
    }

    private void initializeCars(){
        add(new Car("Ford", "Blue"));
        add(new Car("Ferrari", "Red"));
        add(new Car("Fiat", "White"));
    }

    public void add(Car car) {
        stringUtilsService.goToUpperCase(car);
        repository.save(car);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }

    public Iterable<Car> getCarsList() {
        Iterable<Car> cars = repository.findAll();
        cars.forEach(car -> stringUtilsService.goToLowerCaseExceptFirstLetter(car));
        return cars;
    }

    public List<Car> getCarsByMake(String make) {
        List<Car> cars = repository.findByMake(make);
        cars.forEach(car -> stringUtilsService.goToLowerCaseExceptFirstLetter(car));
        return cars;
    }

    public List<Car> getCarsByColor(String color) {
        List<Car> cars = repository.findByColor(color);
        cars.forEach(car -> stringUtilsService.goToLowerCaseExceptFirstLetter(car));
        return cars;
    }

    public Optional<Car> getCar(long id) {
        Optional<Car> car = repository.findById(id);
        if (car.isPresent()) {
            stringUtilsService.goToLowerCaseExceptFirstLetter(car.get());
        }
        return car;
    }

    public void update(long id, Car newCar) {
        Optional<Car> existingCarOptional = repository.findById(id);
        if (existingCarOptional.isPresent()) {
            Car existingCar = existingCarOptional.get();
            existingCar.setColor(newCar.getColor());
            existingCar.setMake(newCar.getMake());
            repository.save(existingCar);
        }
    }
}
