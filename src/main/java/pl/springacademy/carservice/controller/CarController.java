package pl.springacademy.carservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pl.springacademy.carservice.model.Car;
import pl.springacademy.carservice.model.Color;
import pl.springacademy.carservice.model.UpdateCarAllowedFields;
import pl.springacademy.carservice.service.CarService;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping
    public ResponseEntity<List<Car>> getAllCars() {
        return carService.getAllCars();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable final int id) {
        return carService.getCarById(id);
    }

    @GetMapping("/color")
    public ResponseEntity<List<Car>> getCarsByColor(@RequestParam final Color color) {
        return carService.getCarsByColor(color);
    }

    @PostMapping
    public ResponseEntity<Car> addCar(@Validated @RequestBody final Car car) {
        return carService.addCar(car);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Car> addOrUpdateCar(@PathVariable final int id, @Validated @RequestBody final Car car) {
        return carService.addOrUpdateCar(id, car);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Car> updateCarById(@PathVariable final int id, @RequestBody final UpdateCarAllowedFields car) {
        return carService.updateCarById(id, car);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Car> deleteCarById(@PathVariable final int id) {
        return carService.deleteCarById(id);
    }
}
