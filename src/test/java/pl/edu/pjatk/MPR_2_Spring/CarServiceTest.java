package pl.edu.pjatk.MPR_2_Spring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.edu.pjatk.MPR_2_Spring.model.Car;
import pl.edu.pjatk.MPR_2_Spring.repository.CarRepository;
import pl.edu.pjatk.MPR_2_Spring.service.CarService;
import pl.edu.pjatk.MPR_2_Spring.service.StringUtilsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CarServiceTest {
    private CarService carService;
    private StringUtilsService stringUtilsService;

    @BeforeEach
    public void setUp() {
        this.stringUtilsService = Mockito.mock(StringUtilsService.class);
        CarRepository carRepository = Mockito.mock(CarRepository.class);
        this.carService = new CarService(carRepository, stringUtilsService);
    }

    @Test
    public void addCarTest() {
        Car car = new Car("Opel", "Green");
        carService.add(car);
        verify(stringUtilsService, times(1)).goToUpperCase(car);
        verify(stringUtilsService, times(0)).goToLowerCaseExceptFirstLetter(car);
    }

    @Test
    public void getCarsListTest() {
        List<Car> cars = (List<Car>) carService.getCarsList();
        verify(stringUtilsService, times(cars.size())).goToLowerCaseExceptFirstLetter(any());
        verify(stringUtilsService, times(3)).goToUpperCase(any());
    }

    @Test
    public void getCarsByMakeTest() {
        List<Car> cars = carService.getCarsByMake("Renault");
        verify(stringUtilsService, times(cars.size())).goToLowerCaseExceptFirstLetter(any());
        verify(stringUtilsService, times((0))).goToUpperCase(any());
    }

    @Test
    public void getCarsListByColorTest() {
        List<Car> cars = carService.getCarsByColor("Blue");
        verify(stringUtilsService, times(cars.size())).goToLowerCaseExceptFirstLetter(any());
        verify(stringUtilsService, times(3)).goToUpperCase(any());
    }

    @Test
    public void getCarTest() {
        Optional<Car> car = carService.getCar(1);
        if(car.isPresent()){
            verify(stringUtilsService, times(1)).goToLowerCaseExceptFirstLetter(any());
        }
        else{
            verify(stringUtilsService, times(0)).goToLowerCaseExceptFirstLetter(any());
        }
        verify(stringUtilsService, times(3)).goToUpperCase(any());
    }

}
