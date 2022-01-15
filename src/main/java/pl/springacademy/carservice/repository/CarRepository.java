package pl.springacademy.carservice.repository;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.stereotype.Repository;
import org.springframework.util.ReflectionUtils;

import lombok.extern.slf4j.Slf4j;
import pl.springacademy.carservice.model.Car;
import pl.springacademy.carservice.model.Color;

import static java.util.Objects.nonNull;

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

    public Car updatePartialCarData(final Car carToUpdate, final Map<Object, Object> fieldsToUpdate) {

        final StringBuilder message = new StringBuilder()
                .append("Fields updated to car with id ")
                .append(carToUpdate.getId())
                .append(" : ")
                .append("\n");

        final AtomicBoolean displayErrorMessage = new AtomicBoolean(false);
        final StringBuilder errorMessage = new StringBuilder()
                .append("Unknown fields on request to update car with id ")
                .append(carToUpdate.getId())
                .append("\n");

        fieldsToUpdate.forEach((key, value) -> {
            final Field field = ReflectionUtils.findField(Car.class, (String) key);
            if (nonNull(field)) {
                field.setAccessible(true);
                if (((String) key).contentEquals("color")) {
                    value = Color.valueOf(value.toString().toUpperCase());
                }
                ReflectionUtils.setField(field, carToUpdate, value);
                message.append(key).append("=").append(value).append(" ");
            } else {
                displayErrorMessage.set(true);
                errorMessage.append(key).append("=").append(value).append(" ");
            }
        });

        log.info(message.toString());
        if (displayErrorMessage.get()) {
            log.warn(errorMessage.toString());
        }
        return carToUpdate;
    }
}
