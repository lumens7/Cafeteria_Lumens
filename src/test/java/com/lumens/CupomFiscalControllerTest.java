package com.lumens;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CupomFiscalControllerTest extends BaseControllerTest {

    @Test
    public void deveGerarCupomFiscal() throws Exception {
        Long pedidoId = 1L;
        mockMvc.perform(MockMvcRequestBuilders.post("/cupom/emitir/" + pedidoId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.xmlCupom").exists());
    }

    @Test
    public void deveBuscarCupomPorId() throws Exception {
        Long cupomId = 1L;
        mockMvc.perform(MockMvcRequestBuilders.get("/cupom/" + cupomId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cupomId));
    }

    @Test
    public void deveRetornarNotFoundParaCupomInexistente() throws Exception {
        Long cupomId = 999L;
        mockMvc.perform(MockMvcRequestBuilders.get("/cupom/" + cupomId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}