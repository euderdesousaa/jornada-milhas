package br.com.redue.jornada.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_user")
public class Testimony {

    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID id;

    private String name;

    private String testimony;

    private String imgUser;

}
