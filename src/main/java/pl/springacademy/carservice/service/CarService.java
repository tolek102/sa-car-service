package pl.springacademy.carservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.springacademy.carservice.model.Car;
import pl.springacademy.carservice.repository.CarRepository;

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

    public Optional<Car> addCar(final Car newCar) {

        carRepository.save(newCar);
        return Optional.of(newCar);
    }

    public void updateCarById(final Long id, final Car newCar) {

        if (!carRepository.existsById(id)) {
            throw new IllegalArgumentException("Car with id " + id + " not found");
        }

        carRepository.update(newCar.getMark(), newCar.getModel(), newCar.getProductionYear(), newCar.getColor().name(), newCar.getId());
    }

    public void deleteCarById(final Long id) {

        carRepository.deleteById(id);
    }

    public List<Car> getCarByProductionYearRange(final Integer beginProductionYear, final Integer endProductionYear) {

        if (beginProductionYear > endProductionYear) {
            throw new IllegalStateException("Begin of production year range can't be greater than end year");
        }
        return carRepository.findCarsByProductionYearIsBetween(beginProductionYear, endProductionYear);
    }
}
