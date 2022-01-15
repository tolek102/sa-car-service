package pl.springacademy.carservice.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.springacademy.carservice.model.Car;
import pl.springacademy.carservice.model.Color;
import pl.springacademy.carservice.repository.CarRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    @EventListener(ApplicationReadyEvent.class)
    public ResponseEntity<List<Car>> getAllCars() {
        final List<Car> carList = carRepository.getAllCars();

        if (CollectionUtils.isEmpty(carList)) {
            log.error("There are no cars in database");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(carList, HttpStatus.OK);
    }

    public ResponseEntity<Car> getCarById(final int id) {
        final Optional<Car> queriedCar = findCarById(id);

        if (queriedCar.isEmpty()) {
            log.error("Car with id {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(queriedCar.get(), HttpStatus.OK);
    }

    public ResponseEntity<List<Car>> getCarsByColor(final Color color) {
        final List<Car> carList = carRepository.getAllCars();

        final List<Car> cars = carList.stream()
                .filter(car -> car.getColor().equals(color))
                .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(cars)) {
            log.error("No car with color {} was found", color);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    public ResponseEntity<Car> addCar(final Car newCar) {
        final Optional<Car> queriedCar = findCarById(newCar.getId());

        if (queriedCar.isPresent()) {
            log.error("Car with id {} already exist", newCar.getId());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        carRepository.addCar(newCar);
        return new ResponseEntity<>(newCar, HttpStatus.CREATED);
    }

    public ResponseEntity<Car> addOrUpdateCar(final int id, final Car newCar) {
        final Optional<Car> queriedCar = findCarById(id);

        if (queriedCar.isEmpty()) {
            log.info("Car with id {} not found. Creating new database entry", id);
            carRepository.addCar(newCar);
            return new ResponseEntity<>(newCar, HttpStatus.CREATED);
        }

        log.info("Car with id {} was found. Performing update", id);
        carRepository.updateFullCar(queriedCar.get(), newCar);
        return new ResponseEntity<>(newCar, HttpStatus.OK);
    }

    public ResponseEntity<Car> updateCarById(final int id, final Map<Object, Object> fieldsToUpdate) {
        final Optional<Car> queriedCar = findCarById(id);

        if (queriedCar.isEmpty()) {
            log.error("Car with id {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        final Car updatedCar = carRepository.updatePartialCarData(queriedCar.get(), fieldsToUpdate);
        return new ResponseEntity<>(updatedCar, HttpStatus.OK);
    }

    public ResponseEntity<Car> deleteCarById(final int id) {
        final Optional<Car> queriedCar = findCarById(id);

        if (queriedCar.isEmpty()) {
            log.error("Car with id {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        carRepository.deleteCar(queriedCar.get());
        return new ResponseEntity<>(queriedCar.get(), HttpStatus.ACCEPTED);
    }

    private Optional<Car> findCarById(final int id) {
        final List<Car> carList = carRepository.getAllCars();

        return carList.stream()
                .filter(car -> car.getId() == id)
                .findFirst();
    }
}
