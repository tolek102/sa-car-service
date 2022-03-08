package pl.springacademy.carservice.model;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Car {

    private Long id;
    @NotBlank
    private String mark;
    @NotBlank
    private String model;
    @NonNull
    private Integer productionYear;
    @NonNull
    private Color color;
}
