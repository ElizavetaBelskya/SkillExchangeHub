package ru.kpfu.itis.belskaya.models.forms;

import lombok.*;
import ru.kpfu.itis.belskaya.models.User;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RateDto {
    private Integer rating;

    private String comment;

    private User author;

    private Long recipientId;

}
