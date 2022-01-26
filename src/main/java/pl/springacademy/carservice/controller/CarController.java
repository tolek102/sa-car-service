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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String getAllCars(@RequestParam(value = "message", required = false) final String message, final Model model) {
        final List<Car> allCars = carService.getAllCars();

        if (CollectionUtils.isEmpty(allCars)) {
            final String errorMessage = "There are no cars in database";
            log.error(errorMessage);
            model.addAttribute("message", errorMessage);
            return "cars";
        }
        model.addAttribute("cars", allCars);
        model.addAttribute("message", message);
        return "cars";
    }

    @GetMapping("/cars/{id}")
    public String getCarById(@PathVariable final int id, final Model model, final RedirectAttributes attributes) {
        final Optional<Car> carById = carService.getCarById(id);


        if (carById.isPresent()) {
            model.addAttribute("cars", Collections.singletonList(carById.get()));
        } else {
            final String errorMessage = String.format("Car with id %d not found", id);
            attributes.addAttribute("message", errorMessage);
            return "redirect:/cars";
        }
        return "cars";
    }

    @PostMapping("cars/add")
    public String addCar(@Valid @ModelAttribute final Car car, final RedirectAttributes attributes) {
        final Optional<Car> addedCar = carService.addCar(car);

        if (addedCar.isEmpty()) {
            final String errorMessage = String.format("Car with id %d already exist", car.getId());
            attributes.addAttribute("message", errorMessage);
            return "redirect:/cars/add-car";
        }
        attributes.addAttribute("message", "Car with Id : '" + car.getId() + "' was added successfully!");
        return "redirect:/cars/add-car";
    }

    @GetMapping("cars/add-car")
    public String addCar(@RequestParam(value = "message", required = false) final String message, final Model model) {
        final List<Car> allCars = carService.getAllCars();

        model.addAttribute("message", message);
        model.addAttribute("cars", allCars);
        model.addAttribute("newCar", new Car());
        return "addCar";
    }

    @PostMapping("cars/{id}/edit")
    public String editCar(@PathVariable final int id, @Valid @ModelAttribute("updatedCar") final Car updatedCar,
            final RedirectAttributes attributes) {
        carService.updateCarById(id, updatedCar);

        attributes.addAttribute("message", "Car with Id : '" + id + "' updated successfully!");

        return "redirect:/cars";
    }

    @GetMapping("cars/{id}/edit-car")
    public String editCar(@PathVariable final int id, final Model model, final RedirectAttributes attributes) {
        final Optional<Car> car = carService.getCarById(id);

        if (car.isEmpty()) {
            final String errorMessage = String.format("Car with id %d not found", id);
            attributes.addAttribute("message", errorMessage);
            return "redirect:/cars";
        }

        model.addAttribute("carToEdit", car.get());
        model.addAttribute("newCar", new Car());
        return "editCar";
    }

    @GetMapping("/cars/{id}/delete")
    public String deleteCarById(@PathVariable final int id, final RedirectAttributes attributes) {
        final Optional<Car> car = carService.deleteCarById(id);

        if (car.isEmpty()) {
            final String errorMessage = String.format("Car with id %d not found", id);
            attributes.addAttribute("message", errorMessage);
            return "redirect:/cars";
        }
        attributes.addAttribute("message", "Car with Id : '" + id + "' is removed successfully!");
        return "redirect:/cars";
    }
}
