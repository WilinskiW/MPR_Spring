package pl.edu.pjatk.MPR_2_Spring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pjatk.MPR_2_Spring.model.Car;

import java.util.List;

@Repository
public interface CarRepository extends CrudRepository<Car, Long> {
    List<Car> findByMake(String make);
    List<Car> findByColor(String color);
    List<Car> findByIdentification(long identification);
}
