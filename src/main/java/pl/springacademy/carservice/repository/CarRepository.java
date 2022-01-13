package pl.springacademy.carservice.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;
import pl.springacademy.carservice.model.Car;
import pl.springacademy.carservice.model.Color;
import pl.springacademy.carservice.model.UpdateCarAllowedFields;

@Slf4j
@Repository
public class CarRepository {

    private List<Car> carList;

    public CarRepository() {
        this.carList = new ArrayList<>();
        carList.addAll(Arrays.asList(
                Car.builder().id(1).mark("Ford").model("Mustang").color(Color.BLACK).build(),
                Car.builder().id(2).mark("Opel").model("Astra").color(Color.WHITE).build(),
                Car.builder().id(3).mark("Mazda").model("MX3").color(Color.RED).build()
        ));
    }

    public List<Car> getAllCars() {
        return carList;
    }

    public void addCar(final Car car) {
        carList.add(car);
        log.info("Car {} was added to list", car);
    }

    public void deleteCar(final Car car) {
        carList.remove(car);
        log.info("Car with id {} was removed from list", car.getId());
    }

    public void updateFullCar(final Car oldCar, final Car newCar) {
        carList.remove(oldCar);
        carList.add(newCar);
        log.info("Car with id {} was fully updated", oldCar.getId());
    }

    public Car updatePartialCar(final Car carToUpdate, final UpdateCarAllowedFields newCar) {
        return Car.builder().id(Optional.ofNullable(newCar.getId()).orElse(carToUpdate.getId()))
                .model(Optional.ofNullable(newCar.getModel()).orElse(carToUpdate.getModel()))
                .mark(Optional.ofNullable(newCar.getMark()).orElse(carToUpdate.getMark()))
                .color(Optional.ofNullable(newCar.getColor()).orElse(carToUpdate.getColor())).build();
    }
}
