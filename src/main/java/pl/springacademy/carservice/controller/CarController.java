package pl.springacademy.carservice.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.springacademy.carservice.model.Car;
import pl.springacademy.carservice.service.CarService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping("/cars")
    public String getAllCars(Model model) {
        final List<Car> allCars = carService.getAllCars();

        if (CollectionUtils.isEmpty(allCars)) {
            final String errorMessage = "There are no cars in database";
            log.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);
            return "error";
        }
//        model.addAttribute("name", "Paweł");
        model.addAttribute("cars", allCars);
        return "cars";
    }

    @GetMapping("/cars/{id}")
    public String getCarById(@PathVariable final int id, Model model) {
        final Optional<Car> carById = carService.getCarById(id);


        if (carById.isPresent()) {
            model.addAttribute("cars", Collections.singletonList(carById.get()));
        } else {
            final String errorMessage = String.format("Car with id %d not found", id);
            model.addAttribute("errorMessage", errorMessage);
            return "error";
        }
        return "cars";
    }

    @PostMapping("cars/add")
    public String addCar(@Valid @ModelAttribute final Car car, Model model) {
        final Optional<Car> addedCar = carService.addCar(car);

        if (addedCar.isEmpty()) {
            final String errorMessage = String.format("Car with id %d already exist", car.getId());
            model.addAttribute("errorMessage", errorMessage);
            return "error";
        }
        return "redirect:/cars/add-car";
    }

    @GetMapping("cars/add-car")
    public String addCar(Model model) {
        final List<Car> allCars = carService.getAllCars();

        model.addAttribute("cars", allCars);
        model.addAttribute("newCar", new Car());
        return "addCar";
    }

    @PostMapping("cars/{id}/edit")
    public String editCar(@PathVariable final int id, @Valid @ModelAttribute("updatedCar") final Car updatedCar, Model model) {
        carService.updateCarById(id, updatedCar);

        return "redirect:/cars";
    }

    @GetMapping("cars/{id}/edit-car")
    public String editCar(@PathVariable final int id, Model model) {
        final Optional<Car> car = carService.getCarById(id);

        if (car.isEmpty()) {
            final String errorMessage = String.format("Car with id %d not existing", id);
            model.addAttribute("errorMessage", errorMessage);
            return "error";
        }

        model.addAttribute("carToEdit", car.get());
        model.addAttribute("newCar", new Car());
        return "editCar";
    }

    @GetMapping("/cars/{id}/delete")
    public String deleteCarById(@PathVariable final int id, Model model) {
        final Optional<Car> car = carService.deleteCarById(id);

        if (car.isEmpty()) {
            final String errorMessage = String.format("Car with id %d not found", id);
            model.addAttribute("errorMessage", errorMessage);
            return "error";
        }
        return "redirect:/cars";
    }
}
