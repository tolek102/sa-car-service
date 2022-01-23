package pl.springacademy.carservice.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.springacademy.carservice.model.Car;
import pl.springacademy.carservice.model.Color;
import pl.springacademy.carservice.repository.CarRepository;

import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    @EventListener(ApplicationReadyEvent.class)
    public List<Car> getAllCars() {

        return carRepository.getAllCars();
    }

    public Optional<Car> getCarById(final int id) {

        return findCarById(id);
    }

    public List<Car> getCarsByColor(final Color color) {
        final List<Car> carList = carRepository.getAllCars();

        return carList.stream()
                .filter(car -> car.getColor().equals(color))
                .collect(Collectors.toList());
    }

    public Optional<Car> addCar(final Car newCar) {
        final Optional<Car> queriedCar = findCarById(newCar.getId());

        if (queriedCar.isPresent()) {
            log.error("Car with id {} already exist", newCar.getId());
            return Optional.empty();
        }

        carRepository.addCar(newCar);
        return Optional.of(newCar);
    }

    public Car addOrUpdateCar(final int id, final Car newCar) {
        final Optional<Car> queriedCar = findCarById(id);

        if (queriedCar.isEmpty()) {
            log.info("Car with id {} not found. Creating new database entry", id);
            carRepository.addCar(newCar);
            return newCar;
        }

        log.info("Car with id {} was found. Performing update", id);
        carRepository.updateFullCar(queriedCar.get(), newCar);
        return newCar;
    }

    public Optional<Car> updateCarById(final int id, final Map<Object, Object> fieldsToUpdate) {
        final Optional<Car> queriedCar = findCarById(id);

        if (queriedCar.isEmpty()) {
            return Optional.empty();
        }

        final Car updatedCar = carRepository.updatePartialCarData(queriedCar.get(), fieldsToUpdate);
        return Optional.of(updatedCar);
    }

    public Optional<Car> updateCarById(final int id, final Car newCar) {
        final Optional<Car> queriedCar = findCarById(id);

        if (queriedCar.isEmpty()) {
            return Optional.empty();
        }

        final Map<Object, Object> fieldsToUpdate = new HashMap<>();

        if (nonNull(newCar.getMark()) && !queriedCar.get().getMark().equals(newCar.getMark())) {
            fieldsToUpdate.put("mark", newCar.getMark());
        }
        if (nonNull(newCar.getModel()) && !queriedCar.get().getModel().equals(newCar.getModel())) {
            fieldsToUpdate.put("model", newCar.getModel());
        }
        if (nonNull(newCar.getColor()) && !queriedCar.get().getColor().equals(newCar.getColor())) {
            fieldsToUpdate.put("color", newCar.getColor());
        }

        final Car updatedCar = carRepository.updatePartialCarData(queriedCar.get(), fieldsToUpdate);
        return Optional.of(updatedCar);
    }

    public Optional<Car> deleteCarById(final int id) {
        final Optional<Car> queriedCar = findCarById(id);

        if (queriedCar.isEmpty()) {
            return Optional.empty();
        }
        carRepository.deleteCar(queriedCar.get());
        return Optional.of(queriedCar.get());
    }

    private Optional<Car> findCarById(final int id) {
        final List<Car> carList = carRepository.getAllCars();

        return carList.stream()
                .filter(car -> car.getId() == id)
                .findFirst();
    }
}
