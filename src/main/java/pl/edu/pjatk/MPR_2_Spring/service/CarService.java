package pl.edu.pjatk.MPR_2_Spring.service;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Service;
import pl.edu.pjatk.MPR_2_Spring.exception.CarAlreadyExistException;
import pl.edu.pjatk.MPR_2_Spring.exception.CarNotFoundException;
import pl.edu.pjatk.MPR_2_Spring.exception.CarsNotFoundException;
import pl.edu.pjatk.MPR_2_Spring.exception.WrongFormatException;
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

    private void initializeCars() {
        add(new Car("Ford", "Blue"));
        add(new Car("Ferrari", "Red"));
        add(new Car("Fiat", "White"));
    }

    public void add(Car car) {
        if (repository.existsCarByIdentification(car.getIdentification())) {
            throw new CarAlreadyExistException();
        }

        if(isEmptyString(car)){
            throw new WrongFormatException();
        }

        stringUtilsService.goToUpperCase(car);
        repository.save(car);
    }

    public void delete(long id) {
        if (!repository.existsById(id)) {
            throw new CarNotFoundException();
        }
        repository.deleteById(id);
    }

    public List<Car> getCarsList() {
        List<Car> cars = (List<Car>) repository.findAll();

        if (cars.isEmpty()) {
            throw new CarsNotFoundException();
        }

        cars.forEach(stringUtilsService::goToLowerCaseExceptFirstLetter);
        return cars;
    }

    public List<Car> getCarsByMake(String make) {
        List<Car> cars = repository.findByMake(make);

        if (cars.isEmpty()) {
            throw new CarsNotFoundException();
        }

        cars.forEach(stringUtilsService::goToLowerCaseExceptFirstLetter);
        return cars;
    }

    public List<Car> getCarsByColor(String color) {
        List<Car> cars = repository.findByColor(color);

        if (cars.isEmpty()) {
            throw new CarsNotFoundException();
        }

        cars.forEach(stringUtilsService::goToLowerCaseExceptFirstLetter);
        return cars;
    }

    public Car getCar(long id) {
        Optional<Car> car = repository.findById(id);

        if (car.isEmpty()) {
            throw new CarNotFoundException();
        }

        stringUtilsService.goToLowerCaseExceptFirstLetter(car.get());

        return car.get();
    }

    public void update(long id, Car newCar) {
        Optional<Car> existingCarOptional = repository.findById(id);
        if (existingCarOptional.isPresent()) {
            verifyUpdate(existingCarOptional.get(), newCar);
            repository.save(existingCarOptional.get());
        } else {
            throw new CarNotFoundException();
        }
    }

    private void verifyUpdate(Car existingCar, Car newCar) {
        if (sameNameSameColor(existingCar, newCar) || isEmptyString(newCar)) {
            throw new WrongFormatException();
        }

        existingCar.setColor(newCar.getColor());
        existingCar.setMake(newCar.getMake());
    }

    private boolean sameNameSameColor(Car c1, Car c2) {
        return c1.getColor().equals(c2.getColor()) &&
                c1.getMake().equals(c2.getMake());
    }

    private boolean isEmptyString(Car newCar) {
        String make = newCar.getMake().trim();
        String color = newCar.getColor().trim();
        return make.isEmpty() || color.isEmpty();
    }
}
