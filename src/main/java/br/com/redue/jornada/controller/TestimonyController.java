package br.com.redue.jornada.controller;

import br.com.redue.jornada.model.Testimony;
import br.com.redue.jornada.model.dto.testimony.TestimonyDto;
import br.com.redue.jornada.model.dto.testimony.TestimonyUpdateDto;
import br.com.redue.jornada.service.TestimonyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/depoimentos")
@RequiredArgsConstructor
public class TestimonyController {

    private final TestimonyService service;

    @GetMapping
    public List<Testimony> getAllTestimony() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Testimony> findById(@PathVariable UUID id){
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<TestimonyDto> createATestimony(@Valid @RequestBody TestimonyDto testimony) {
        TestimonyDto newTestimony = service.insert(testimony);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(newTestimony).toUri();
        return ResponseEntity.created(uri).body(newTestimony);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<TestimonyUpdateDto> changeAnyThing(@PathVariable UUID id,
                                                 @RequestBody TestimonyUpdateDto dto) {
        TestimonyUpdateDto update = service.update(id, dto);
        return ResponseEntity.ok().body(update);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable UUID id){
        return service.deleteTestimonyById(id);
    }

    @GetMapping(value = "/depoimentos-home")
    public ResponseEntity<List<Testimony>> getTestimonyRandom() {
        List<Testimony> testimonies = service.getRandomTestimony(3);
        return ResponseEntity.ok(testimonies);
    }
}
