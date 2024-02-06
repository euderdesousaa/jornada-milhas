package br.com.redue.jornada.service;

import br.com.redue.jornada.mapper.DestinationMapper;
import br.com.redue.jornada.model.Destination;
import br.com.redue.jornada.model.dto.destination.DestinationByIdDto;
import br.com.redue.jornada.model.dto.destination.DestinationDto;
import br.com.redue.jornada.model.dto.destination.DestinationUpdateDTO;
import br.com.redue.jornada.repository.DestinationRepository;
import com.pkslow.ai.GoogleBardClient;
import com.pkslow.ai.domain.Answer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

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
        if(dto.getDescText().isEmpty()){
            Answer answer = bardClient.ask("Faça um resumo sobre " + dto.getName() + " enfatizando o porque este lugar é " +
                    "incrível. Utilize uma linguagem informal e até 100 caracteres no máximo em cada parágrafo. Crie 2 parágrafos neste resumo.");
            save.setDescText(answer.getChosenAnswer());
        }
        return mapper.toDTO(save);
    }

    public List<Destination> getDestinationByParam(@RequestParam("name") String name) {
        return repository.findByName(name);
    }

    public List<Destination> findAll() {
        return repository.findAll();
    }

    public DestinationByIdDto getDestinationById(Long id) {
        Optional<Destination> destination = repository.findById(id);
        return new DestinationByIdDto(destination);
    }

    public DestinationUpdateDTO updateADestination(Long id, DestinationUpdateDTO dto) {
        Destination destination = repository.findById(id).orElse(null);
        destination.setTarget(dto.getTarget());
        destination.setDescText(dto.getDescText());
        Destination save = repository.save(destination);
        return mapper.toUpdate(save);
    }

    @Transactional
    public void deleteADestinationById(Long id) {
        repository.deleteById(id);
    }
}
