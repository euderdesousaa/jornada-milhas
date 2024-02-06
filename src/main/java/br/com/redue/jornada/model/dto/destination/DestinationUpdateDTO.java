package br.com.redue.jornada.model.dto.destination;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DestinationUpdateDTO{

    @Size(min = 10, max = 160)
    private String target;

    private String descText;
}
