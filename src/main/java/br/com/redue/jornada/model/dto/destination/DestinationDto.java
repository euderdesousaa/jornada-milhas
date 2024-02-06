package br.com.redue.jornada.model.dto.destination;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DestinationDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Say destination name, please!")
    private String name;

    private Double price;

    @NotBlank(message = "See for us a image :)")
    @URL
    private String photoUrl;

    @URL
    private String photoUrl2;

    @Size(min = 10, max = 160)
    private String target;

    private String descText;
}
