package br.com.redue.jornada.service;

import br.com.redue.jornada.mapper.DestinationMapper;
import br.com.redue.jornada.model.Destination;
import br.com.redue.jornada.model.dto.destination.DestinationByIdDto;
import br.com.redue.jornada.model.dto.destination.DestinationDto;
import br.com.redue.jornada.model.dto.destination.DestinationUpdateDTO;
import br.com.redue.jornada.repository.DestinationRepository;
import com.pkslow.ai.GoogleBardClient;
import com.pkslow.ai.domain.Answer;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DestinationService {

    private final DestinationRepository repository;

    private final DestinationMapper mapper;

    private final GoogleBardClient bardClient;


    @Transactional
    public DestinationDto createADestination(DestinationDto dto) {
        Destination save = repository.save(mapper.toEntity(dto));
        if (dto.getDescText().isEmpty()) {
            Answer answer = bardClient.ask("Faça um resumo sobre " + dto.getName() + " enfatizando o porque este lugar é " +
                    "incrível. Utilize uma linguagem informal e até 100 caracteres no máximo em cada parágrafo. Crie 2 parágrafos neste resumo.");
            save.setDescText(answer.getChosenAnswer());
        }
        return mapper.toDTO(save);
    }

    public ResponseEntity<Object> getDestinationByParam(String name) {
        List<Destination> findByName = repository.findByName(name);
        if (findByName.isEmpty()) {
            return ResponseEntity.status( HttpStatus.NOT_FOUND).body("Name not found "+ name);
        }
        return ResponseEntity.status(HttpStatus.OK).body(findByName);
    }

    public List<Destination> findAll() {
        return repository.findAll();
    }

    public DestinationByIdDto getDestinationById(Long id) {
        Optional<Destination> destination = Optional.ofNullable(repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum destino foi encontrado.")));
        return new DestinationByIdDto(destination);
    }

    @Transactional
    public DestinationUpdateDTO updateADestination(Long id, DestinationUpdateDTO dto) {
        Destination destination = repository.findById(id).orElse(null);
        assert destination != null;
        destination.setTarget(dto.getTarget());
        destination.setDescText(dto.getDescText());
        Destination save = repository.save(destination);
        return mapper.toUpdate(save);
    }

    @Transactional
    public ResponseEntity<Object> deleteADestinationById(Long id) {
        Optional<Destination> findById = repository.findById(id);
        if (findById.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        repository.deleteById(findById.get().getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Product deleted successfuly");

    }
}
