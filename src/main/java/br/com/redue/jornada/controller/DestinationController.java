package br.com.redue.jornada.controller;

import br.com.redue.jornada.model.Destination;
import br.com.redue.jornada.model.dto.destination.DestinationByIdDto;
import br.com.redue.jornada.model.dto.destination.DestinationDto;
import br.com.redue.jornada.model.dto.destination.DestinationUpdateDTO;
import br.com.redue.jornada.service.DestinationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/destinos")
@RequiredArgsConstructor
public class DestinationController {

    private final DestinationService service;

    @GetMapping(params = "name")
    public ResponseEntity<Object> getDestinationByName(@RequestParam String name) {
        return service.getDestinationByParam(name);
    }

    @GetMapping
    public ResponseEntity<List<Destination>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DestinationByIdDto> getDestinationById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getDestinationById(id));
    }

    @PostMapping
    public ResponseEntity<DestinationDto> createADestination(@Valid @RequestBody DestinationDto destination) {
        DestinationDto newDestination = service.createADestination(destination);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(newDestination).toUri();
        return ResponseEntity.created(uri).body(newDestination);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<DestinationUpdateDTO> changeAnyThing(@PathVariable Long id,
                                                               @RequestBody @Valid DestinationUpdateDTO destinationUpdateDTO) {
        DestinationUpdateDTO update = service.updateADestination(id, destinationUpdateDTO);
        return ResponseEntity.ok().body(update);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return service.deleteADestinationById(id);
    }

}
