package br.com.redue.jornada.repository;

import br.com.redue.jornada.model.Testimony;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<Testimony, UUID> {
}
