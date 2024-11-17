package pl.edu.pjatk.MPR_2_Spring.service;

import org.springframework.stereotype.Service;
import pl.edu.pjatk.MPR_2_Spring.exception.CarAlreadyExistException;
import pl.edu.pjatk.MPR_2_Spring.exception.CarNotFoundException;
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
        if (isExistCarWithIdentification(car.getIdentification())) {
            throw new CarAlreadyExistException();
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

    private boolean isExistCarWithIdentification(long identification) {
        return !repository.findByIdentification(identification).isEmpty();
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
            Car existingCar = existingCarOptional.get();
            existingCar.setColor(newCar.getColor());
            existingCar.setMake(newCar.getMake());

            if (sameNameSameColor(existingCar, newCar) || isEmptyString(existingCar, newCar)) {
                throw new CarAlreadyExistException();
            }

            repository.save(existingCar);
        }
    }

    private boolean sameNameSameColor(Car c1, Car c2) {
        return c1.getColor().equals(c2.getColor()) &&
                c1.getMake().equals(c2.getMake());
    }

    private boolean isEmptyString(Car c1, Car c2) {
        return c1.getMake().isEmpty() && c2.getMake().isEmpty() && c1.getColor().isEmpty() && c2.getColor().isEmpty();
    }
}
