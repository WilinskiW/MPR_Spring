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

    public CarService(CarRepository repository) {
        this.repository = repository;
        add(new Car("Ford", "Blue"));
        add(new Car("Ferrari", "Red"));
        add(new Car("Fiat", "White"));
    }

    public void add(Car car) {
        repository.save(car);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }

    public Iterable<Car> getCarsList() {
        return repository.findAll();
    }

    public List<Car> getCarsByMake(String make) {
        return repository.findByMake(make);
    }

    public List<Car> getCarsByColor(String color) {
        return repository.findByColor(color);
    }

    public Optional<Car> getCar(long id) {
        return repository.findById(id);
    }

    public void update(long id, Car newCar) {
        if(repository.findById(id).isPresent()){
            repository.save(newCar);
        }
    }
}
