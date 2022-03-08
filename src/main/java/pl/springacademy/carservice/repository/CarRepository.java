package pl.springacademy.carservice.repository;

import java.util.List;
import java.util.Optional;

import pl.springacademy.carservice.model.Car;

public interface CarRepository {

    List<Car> findAll();

    Optional<Car> findById(Long id);

    void save(Car car);

    void delete(Long id);

    void update(Long id, Car newCar);

    List<Car> findByProductionYearRange(Integer beginProductionYear, Integer endYear);
}
