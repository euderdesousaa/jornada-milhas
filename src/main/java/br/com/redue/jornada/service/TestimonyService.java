package br.com.redue.jornada.service;

import br.com.redue.jornada.mapper.TestimonyMapper;
import br.com.redue.jornada.model.Testimony;
import br.com.redue.jornada.model.dto.testimony.TestimonyDto;
import br.com.redue.jornada.model.dto.testimony.TestimonyUpdateDto;
import br.com.redue.jornada.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TestimonyService {

    private final UserRepository repository;
    private final TestimonyMapper mapper;

    public List<Testimony> findAll() {
        return repository.findAll();
    }

    public Optional<Testimony> findById(UUID id) {
        return Optional.ofNullable(repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum destino foi encontrado.")));
    }


    @Transactional
    public List<Testimony> getRandomTestimony(int amount) {
        List<Testimony> testemonies = repository.findAll();
        List<Testimony> randomTestemonies = new ArrayList<>();

        Random random = new Random();

        while (randomTestemonies.size() < amount && randomTestemonies.size() < testemonies.size()) {
            int randomIndex = random.nextInt(testemonies.size());
            Testimony testimony = testemonies.get(randomIndex);
            if (!randomTestemonies.contains(testimony)) {
                randomTestemonies.add(testimony);
            }
        }
        return randomTestemonies;
    }

    @Transactional
    public TestimonyDto insert(TestimonyDto dto) {
        Testimony save = repository.save(mapper.toEntity(dto));
        return mapper.toDTO(save);
    }

    @Transactional
    public TestimonyUpdateDto update(UUID id, TestimonyUpdateDto dto) {
        Testimony update = repository.findById(id).orElse(null);
        assert update != null;
        update.setTestimony(dto.getTestimony());
        Testimony save = repository.save(update);
        return mapper.toUpdate(save);
    }

    @Transactional
    public ResponseEntity<Object> deleteTestimonyById(UUID id) {
        Optional<Testimony> findById = repository.findById(id);
        if (findById.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        repository.deleteById(findById.get().getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Product deleted successfuly");

    }
}
