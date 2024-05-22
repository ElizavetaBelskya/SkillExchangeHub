package ru.kpfu.itis.belskaya.models.forms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Elizaveta Belskaya
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OrderForm {

    @NotBlank(message = "It can't be empty")
    private String skill;

    private String description;

    @NotBlank(message = "It can't be empty")
    private String gender;

    private Boolean rating;


}
