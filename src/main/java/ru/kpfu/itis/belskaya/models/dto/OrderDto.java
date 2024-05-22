package ru.kpfu.itis.belskaya.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Order")
public class OrderDto {

    @Schema(description = "Order id", example = "1")
    private Long id;

    @Schema(description = "Skill of order", example = "Math")
    @NotBlank(message = "It can't be empty")
    private String skill;

    @Schema(description = "author id", example = "1")
    private Long authorId;

    @Schema(description = "Comment to order", example = "I need the professional school teacher")
    private String description;

    @Schema(description = "Gender of potential recipient", example = "MALE")
    @NotBlank(message = "It can't be empty")
    private String recipientGender;

    @Schema(description = "Minimal rating for potential recipient", example = "4.0")
    @NotNull(message = "It can't be empty")
    private float minRating;

    @Schema(description = "Date of order creation", example = "")
    private LocalDateTime creationDate;

    @Schema(description = "Author's skills")
    private List<String> authorSkills;

    @Schema(description = "recipient id", example = "1")
    private Long recipientId;

    @Schema(description = "List of candidates id", example = "[1, 2, 3]")
    private List<Long> candidates;

}
