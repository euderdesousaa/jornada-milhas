package br.com.redue.jornada.controller;

import br.com.redue.jornada.model.dto.destination.DestinationDto;
import br.com.redue.jornada.model.dto.destination.DestinationUpdateDTO;
import br.com.redue.jornada.service.DestinationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = DestinationController.class)
class DestinationControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private DestinationService service;

    @Test
    @DisplayName("Should throw http code 400 when the infomations are invalid")
    void create_a_destination_invalid() throws Exception {
        var response = mvc.perform(post("/destinos")).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Should throw http code 400 when the infomations is valid")
    void create_A_Destination_Valid() throws Exception {
        // Dados de teste
        DestinationDto destinationDto = new DestinationDto("Cancun", 200.0, "https://img.com",
                "https://img2.com", "Getting to know beachers", "BloodCanvas");

        // Mock do comportamento do serviço
        given(service.createADestination(any(DestinationDto.class))).willReturn(destinationDto);

        // Execução da requisição
        mvc.perform(post("/destinos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(destinationDto)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Should return 200 http code and all destinations")
    void findAllDestination() throws Exception {
        var response = mvc.perform(get("/destinos")).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Should return 200 http code and destinations")
    void get_Destination_ByParam_Valid() throws Exception {
        var response = mvc.perform(get("/destinos?name=Cancun")).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }


    @Test
    @DisplayName("Should throw 404 http code when  Deveria devolver codigo http 404 ao informar nome invalido")
    void get_Destination_ByParam_Invalid() throws Exception {
        // Mock do comportamento do serviço
        when(service.getDestinationByParam("1")).thenReturn(ResponseEntity.notFound().build());

        // Execução da requisição
        mvc.perform(get("/destinos?name=1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return 200 http code and destination list")
    void findByIdValid() throws Exception {
        var response = mvc.perform(get("/destinos/16")).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Should return 400 http code when the information is invalid")
    void changeInformation_Invalid() throws Exception {
        var response = mvc.perform(post("/destinos")).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Should return http 200 code and update destinations")
    void changeInfomation_Valid() throws Exception {
        // Dados de teste
        DestinationUpdateDTO updateDTO = new DestinationUpdateDTO("Conhecer o mar, e outras coisas coisas.", "Exemplo de local que eu queria visitar.");

        // Mock do comportamento do serviço
        given(service.updateADestination(eq(1L), any(DestinationUpdateDTO.class))).willReturn(updateDTO);

        // Execução da requisição
        mvc.perform(put("/destinos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should throw 404 http code when ID is invalid")
    void delete_A_Destination_Invalid() throws Exception {
        // Mock do comportamento do serviço
        given(service.deleteADestinationById(anyLong())).willReturn(ResponseEntity.notFound().build());

        // Execução da requisição
        mvc.perform(delete("/destinos/1"))
                .andExpect(status().isNotFound());
    }


}
