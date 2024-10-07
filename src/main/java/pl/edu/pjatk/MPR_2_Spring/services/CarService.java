package pl.edu.pjatk.MPR_2_Spring.services;

import org.springframework.stereotype.Component;
import pl.edu.pjatk.MPR_2_Spring.model.Car;

import java.util.ArrayList;
import java.util.List;

//Pakiet "services" - dodajemy do niego wszelkie komponenty, które coś obsługują
//Przykład - kucharz w restauracji
@Component
public class CarService {
    private List<Car> cars = new ArrayList<>();

    public CarService() {
        this.cars.add(new Car("Ford","Blue"));
        this.cars.add(new Car("Ferrari","Red"));
        this.cars.add(new Car("Fiat","White"));
    }

    public void add(Car car) {
        this.cars.add(car);
    }

    public void delete(int id) {
        this.cars.remove(id);
    }

    public List<Car> getCarsList() {
        return cars;
    }

    public Car getCar(int id) {
        return cars.get(id);
    }

    public void update(int id, Car newCar) {
        cars.set(id, newCar);
    }
}
