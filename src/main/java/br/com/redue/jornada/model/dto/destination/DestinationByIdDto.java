package br.com.redue.jornada.model.dto.destination;

import br.com.redue.jornada.model.Destination;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.http.HttpStatusCode;

import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DestinationByIdDto{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String photoUrl;

    private String photoUrl2;

    private String name;
    @Size(min = 10, max = 160)
    private String target;

    private String descText;

    public DestinationByIdDto(Optional<Destination> destination) {
        id = destination.get().getId();
        photoUrl = destination.get().getPhotoUrl();
        photoUrl2 = destination.get().getPhotoUrl2();
        name = destination.get().getName();
        target = destination.get().getTarget();
        descText = destination.get().getDescText();
    }
}
