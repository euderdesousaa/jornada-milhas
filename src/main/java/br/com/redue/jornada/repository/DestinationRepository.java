package br.com.redue.jornada.repository;

import br.com.redue.jornada.model.Destination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long> {

    @Query(value = "SELECT t FROM Destination t WHERE t.name=?1")
    List<Destination> findByName(String name);
}
