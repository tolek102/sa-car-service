package pl.springacademy.carservice.service;

import java.time.LocalDate;
import java.util.List;
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

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    @EventListener(ApplicationReadyEvent.class)
    public List<Car> getAllCars() {

        return carRepository.findAll();
    }

    public Optional<Car> getCarById(final Long id) {

        return carRepository.findById(id);
    }

    public List<Car> getCarsByColor(final Color color) {
        final List<Car> carList = carRepository.findAll();

        return carList.stream()
                .filter(car -> car.getColor().equals(color))
                .collect(Collectors.toList());
    }

    public Optional<Car> addCar(final Car newCar) {

        carRepository.save(newCar);
        return Optional.of(newCar);
    }

    public void updateCarById(final Long id, final Car newCar) {
        final Optional<Car> queriedCar = carRepository.findById(id);

        if (queriedCar.isEmpty()) {
            throw new IllegalArgumentException("Car with id " + id + " not found");
        }
        carRepository.update(id, newCar);
    }

    public void deleteCarById(final Long id) {

        carRepository.delete(id);
    }

    public List<Car> getCarByProductionYearRange(final Integer beginProductionYear, final Integer endProductionYear) {

        if (beginProductionYear > endProductionYear) {
            throw new IllegalStateException("Begin of production year range can't be greater than end year");
        }
        return carRepository.findByProductionYearRange(beginProductionYear, endProductionYear);
    }
}
