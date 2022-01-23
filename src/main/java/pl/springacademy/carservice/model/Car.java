package pl.springacademy.carservice.model;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Car extends RepresentationModel<Car> {

    @NonNull
    private int id;
    @NotBlank
    private String mark;
    @NotBlank
    private String model;
    @NonNull
    private Color color;
}
