package pl.springacademy.carservice.configuration;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import pl.springacademy.carservice.model.Car;
import pl.springacademy.carservice.model.Color;
import pl.springacademy.carservice.repository.CarRepository;

@Component
@RequiredArgsConstructor
public class DbDataInitializer {

    private final CarRepository carRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void dbInit() {
        carRepository.save(Car.builder()
                .mark("Honda")
                .model("Accord")
                .productionYear(2019)
                .color(Color.RED)
                .build());
        carRepository.save(Car.builder()
                .mark("Fiat")
                .model("126p")
                .productionYear(1989)
                .color(Color.YELLOW)
                .build());
        carRepository.save(Car.builder()
                .mark("Skoda")
                .model("Fabia")
                .productionYear(2000)
                .color(Color.GREEN)
                .build());
    }
}
