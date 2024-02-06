package br.com.redue.jornada.controller;

import br.com.redue.jornada.model.Testimony;
import br.com.redue.jornada.model.dto.TestimonyDto;
import br.com.redue.jornada.service.TestimonyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
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

    @PostMapping
    public ResponseEntity<TestimonyDto> createATestimony(@Valid @RequestBody TestimonyDto testimony) {
        TestimonyDto newTestimony = service.insert(testimony);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(newTestimony).toUri();
        return ResponseEntity.created(uri).body(newTestimony);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<TestimonyDto> changeAnyThing(@PathVariable UUID id,
                                                 @RequestBody TestimonyDto testimonyDto) {
        TestimonyDto update = service.update(id);
        return ResponseEntity.ok().body(update);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id){
        service.deleteTestimonyById(id);
    }

    @GetMapping(value = "/depoimentos-home")
    public ResponseEntity<List<Testimony>> getDepoimentosHome() {
        List<Testimony> testimonies = service.getRandomTestimony(3);
        return ResponseEntity.ok(testimonies);
    }
}
