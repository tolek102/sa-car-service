package pl.springacademy.carservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Data;

// for disabling @NotNull validation and allowing to send only fields that You want to update instead of full object
@Data
@Builder
@JsonInclude(Include.NON_NULL)
public class UpdateCarAllowedFields {

    private Integer id;
    private String mark;
    private String model;
    private Color color;
}
