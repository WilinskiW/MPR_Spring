package pl.edu.pjatk.MPR_2_Spring.service;

import org.springframework.stereotype.Component;
import pl.edu.pjatk.MPR_2_Spring.model.Car;

@Component
public class StringUtilsService {
    public void goToUpperCase(Car car) {
        car.setMake(car.getMake().toUpperCase());
        car.setColor(car.getColor().toUpperCase());
    }

    public void goToLowerCaseExceptFirstLetter(Car car) {
        car.setMake(car.getMake().toLowerCase());
        car.setColor(car.getColor().toLowerCase());

        car.setMake(car.getMake().substring(0, 1).toUpperCase() + car.getMake().substring(1));
        car.setColor(car.getColor().substring(0, 1).toUpperCase() + car.getColor().substring(1));
    }
}
