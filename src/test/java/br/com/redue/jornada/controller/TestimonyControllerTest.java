package br.com.redue.jornada.controller;

import br.com.redue.jornada.model.dto.testimony.TestimonyDto;
import br.com.redue.jornada.model.dto.testimony.TestimonyUpdateDto;
import br.com.redue.jornada.service.TestimonyService;
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

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TestimonyController.class)
class TestimonyControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TestimonyService service;

    private final UUID id = UUID.randomUUID();

    @Test
    @DisplayName("Should throw http code 400 when the infomations are invalid")
    void create_a_destination_invalid() throws Exception {
        var response = mvc.perform(post("/depoimentos")).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Should throw http code 400 when the infomations is valid")
    void create_A_Destination_Valid() throws Exception {
        TestimonyDto destinationDto = new TestimonyDto();

        given(service.insert(any(TestimonyDto.class))).willReturn(destinationDto);

        mvc.perform(post("/depoimentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(destinationDto)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Should return 200 http code and all destinations")
    void findAllDestination() throws Exception {
        var response = mvc.perform(get("/depoimentos")).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Should return 200 http code and destination list")
    void findByIdValid() throws Exception {
        var response = mvc.perform(get("/depoimentos/" + id)).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Should return 400 http code when the information is invalid")
    void changeInformation_Invalid() throws Exception {
        var response = mvc.perform(post("/depoimentos")).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Should return http 200 code and update destinations")
    void changeInfomation_Valid() throws Exception {
        TestimonyUpdateDto updateDTO = new TestimonyUpdateDto("Já visitei diversos paises mas Cancun em questão foi o melhor");

        given(service.update(eq(id), any(TestimonyUpdateDto.class))).willReturn(updateDTO);

        mvc.perform(put("/depoimentos/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should throw 404 http code when ID is invalid")
    void delete_A_Destination_Invalid() throws Exception {
        given(service.deleteTestimonyById(id)).willReturn(ResponseEntity.notFound().build());

        mvc.perform(delete("/depoimentos/" + id))
                .andExpect(status().isNotFound());
    }
}
