package br.com.redue.jornada.model.dto.testimony;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TestimonyDto {

    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID id;

    private String name;

    private String testimony;

    private String imgUser;
}
