package pl.springacademy.carservice.controller;

import java.time.LocalDate;
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

import static java.util.Objects.nonNull;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping("/cars")
    public String getAllCars(@RequestParam(value = "message", required = false) final String message,
            @RequestParam(value = "withFilter", required = false) final Boolean withFilter, final Model model) {
        final List<Car> allCars = carService.getAllCars();

        if (CollectionUtils.isEmpty(allCars)) {
            final String errorMessage = "There are no cars in database";
            log.error(errorMessage);
            model.addAttribute("message", errorMessage);
            return "cars";
        }
        model.addAttribute("cars", allCars);
        model.addAttribute("message", message);
        model.addAttribute("withFilter", withFilter);
        return "cars";
    }

    @GetMapping("/cars/{id}")
    public String getCarById(@PathVariable final Long id, final Model model, final RedirectAttributes attributes) {
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

    @GetMapping("/cars/productionYear")
    public String getCarByProductionYearRange(@RequestParam final Integer beginProductionYear,
            @RequestParam(required = false) final Integer endProductionYear, final Model model, final RedirectAttributes redirectAttribute) {

        try {
            final Integer endYear = nonNull(endProductionYear) ? endProductionYear : LocalDate.now().getYear();
            final List<Car> carByProductionYear = carService.getCarByProductionYearRange(beginProductionYear, endYear);
            model.addAttribute("cars", carByProductionYear);
            model.addAttribute("withFilter", true);
            model.addAttribute("message", "Cars produced from " + beginProductionYear
                    + " till " + endYear);
            return "cars";
        } catch (final IllegalStateException e) {
            redirectAttribute.addAttribute("message", e.getMessage());
        } catch (final Exception e) {
            redirectAttribute.addAttribute("message", "ERROR OCCURRED during search!");
        }
        return "redirect:/cars";
    }

    @PostMapping("cars/add")
    public String addCar(@Valid @ModelAttribute final Car car, final RedirectAttributes attributes) {
        final Optional<Car> addedCar = carService.addCar(car);

        if (addedCar.isEmpty()) {
            final String errorMessage = String.format("Car with id %d already exist", car.getId());
            attributes.addAttribute("message", errorMessage);
            return "redirect:/cars/add-car";
        }
        attributes.addAttribute("message", "Car was added successfully!");
        return "redirect:/cars/add-car";
    }

    @GetMapping("cars/add-car")
    public String addCar(@RequestParam(value = "message", required = false) final String message, final Model model) {

        model.addAttribute("message", message);
        model.addAttribute("newCar", new Car());
        return "addCar";
    }

    @PostMapping("cars/{id}/edit")
    public String editCar(@PathVariable final Long id, @Valid @ModelAttribute("updatedCar") final Car updatedCar,
            final RedirectAttributes attributes) {

        try {
            carService.updateCarById(id, updatedCar);
        } catch (final IllegalStateException e) {
            attributes.addAttribute("message", e.getMessage());
        } catch (final Exception e) {
            attributes.addAttribute("message", "ERROR OCCURRED! Caw with id " + id + " was not updated!!");
        } finally {
            attributes.addAttribute("message", "Car with Id : '" + id + "' updated successfully!");
        }

        return "redirect:/cars";
    }

    @GetMapping("cars/{id}/edit-car")
    public String editCar(@PathVariable final Long id, final Model model, final RedirectAttributes attributes) {
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
    public String deleteCarById(@PathVariable final Long id, final RedirectAttributes attributes) {

        try {
            carService.deleteCarById(id);
        } catch (final IllegalStateException e) {
            attributes.addAttribute("message", e.getMessage());
        } catch (final Exception e) {
            attributes.addAttribute("message", "ERROR OCCURRED! Caw with id " + id + " was not removed!!");
        } finally {
            attributes.addAttribute("message", "Car with Id : '" + id + "' is removed successfully!");
        }

        return "redirect:/cars";
    }
}
