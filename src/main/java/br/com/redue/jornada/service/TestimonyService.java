package br.com.redue.jornada.service;

import br.com.redue.jornada.mapper.TestimonyMapper;
import br.com.redue.jornada.model.Testimony;
import br.com.redue.jornada.model.dto.TestimonyDto;
import br.com.redue.jornada.repository.UserRepository;
import br.com.redue.jornada.service.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TestimonyService {

    private final UserRepository repository;
    private final TestimonyMapper mapper;

    public List<Testimony> findAll(){
        return repository.findAll();
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
    public TestimonyDto insert(TestimonyDto dto){
        Testimony save = repository.save(mapper.toEntity(dto));
        return mapper.toDTO(save);
    }

    @Transactional
    public TestimonyDto update(UUID id){
        Testimony update = repository.findById(id).orElse(null);
        Testimony save = repository.save(mapper.toUpdate(update));
        if(update == null)
        {
            throw new ResourceNotFoundException("Destination not found");
        }
        return mapper.toDTO(save);
    }

    @Transactional
    public void deleteTestimonyById(UUID id){
        repository.deleteById(id);
    }
}
