package pl.springacademy.carservice.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
@NonNull
public class Car {

    private int id;
    private String mark;
    private String model;
    private Color color;
}
