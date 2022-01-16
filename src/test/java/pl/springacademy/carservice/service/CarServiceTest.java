package pl.springacademy.carservice.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import pl.springacademy.carservice.model.Car;
import pl.springacademy.carservice.model.Color;
import pl.springacademy.carservice.repository.CarRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {

    @Mock
    private CarRepository carRepository;
    @InjectMocks
    private CarService carService;

    @BeforeEach
    void setUp() {
        when(carRepository.getAllCars()).thenReturn(getAllCars());
    }

    @Test
    void should_return_all_cars_from_repository() {
        final List<Car> allCars = carService.getAllCars();

        verify(carRepository).getAllCars();
        assertThat(allCars)
                .isNotEmpty()
                .hasSize(3);
    }

    @Test
    void should_find_car_by_id() {
        final Optional<Car> carById = carService.getCarById(1);

        assertThat(carById)
                .isNotEmpty()
                .isEqualTo(Optional.of(Car.builder().id(1).mark("Ford").model("Mustang").color(Color.BLACK).build()));
    }

    @Test
    void should_return_optional_empty_when_car_was_not_found_by_id() {
        final Optional<Car> carById = carService.getCarById(4);

        assertThat(carById)
                .isEmpty();
    }

    @Test
    void should_find_car_by_color() {
        final List<Car> carById = carService.getCarsByColor(Color.RED);

        assertThat(carById)
                .isNotEmpty()
                .hasSize(1)
                .containsExactly(Car.builder().id(3).mark("Mazda").model("MX3").color(Color.RED).build());
    }

    @Test
    void should_return_empty_list_when_car_was_not_found_by_color() {
        final List<Car> carById = carService.getCarsByColor(Color.YELLOW);

        assertThat(carById)
                .isEmpty();
    }

    @Test
    void should_add_car_to_database_when_car_with_same_id_not_existing_in_database() {
        final Car carToAdd = Car.builder().id(4).mark("Kia").model("Ceed").color(Color.GREEN).build();

        final Optional<Car> car = carService.addCar(carToAdd);

        verify(carRepository).addCar(carToAdd);
        assertThat(car).isNotEmpty();
        assertThat(car.get()).isEqualTo(carToAdd);
    }

    @Test
    void should_return_optional_empty_when_car_with_same_id_existing_in_database() {
        final Car carToAdd = Car.builder().id(1).mark("Kia").model("Ceed").color(Color.GREEN).build();

        final Optional<Car> car = carService.addCar(carToAdd);

        verify(carRepository).getAllCars();
        verifyNoMoreInteractions(carRepository);
        assertThat(car).isEmpty();
    }

    @Test
    void should_call_add_car_method_when_car_with_same_id_not_existing_in_database() {
        final Car carToAdd = Car.builder().id(4).mark("Kia").model("Ceed").color(Color.GREEN).build();

        final Car car = carService.addOrUpdateCar(4, carToAdd);

        verify(carRepository).addCar(carToAdd);
    }

    @Test
    void should_call_update_full_car_car_method_when_car_with_same_id_existing_in_database() {
        final Car carToUpdate = Car.builder().id(1).mark("Ford").model("Mustang").color(Color.BLACK).build();
        final Car carToAdd = Car.builder().id(1).mark("Kia").model("Ceed").color(Color.GREEN).build();

        carService.addOrUpdateCar(1, carToAdd);

        verify(carRepository).updateFullCar(carToUpdate, carToAdd);
    }

    @Test
    void should_delete_car_by_id_when_found_in_database() {
        carService.deleteCarById(1);

        verify(carRepository).deleteCar(Car.builder().id(1).mark("Ford").model("Mustang").color(Color.BLACK).build());
    }

    @Test
    void should_return_optional_empty_when_car_not_found_in_database() {
        final Optional<Car> car = carService.deleteCarById(4);

        verify(carRepository).getAllCars();
        verifyNoMoreInteractions(carRepository);
        assertThat(car).isEmpty();
    }

    private List<Car> getAllCars() {
        return Arrays.asList(
                Car.builder().id(1).mark("Ford").model("Mustang").color(Color.BLACK).build(),
                Car.builder().id(2).mark("Opel").model("Astra").color(Color.WHITE).build(),
                Car.builder().id(3).mark("Mazda").model("MX3").color(Color.RED).build()
        );

    }
}