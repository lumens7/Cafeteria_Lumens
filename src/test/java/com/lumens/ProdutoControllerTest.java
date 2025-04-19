package com.lumens;

import com.lumens.Domain.Produto;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProdutoControllerTest extends BaseControllerTest {

    @Test
    public void deveRetornarTodosProdutos() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/produto/findAll")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void deveRetornarProdutoPorNome() throws Exception {
        String nomeProduto = "Cappuccino";
        mockMvc.perform(MockMvcRequestBuilders.get("/produto/findByName")
                        .param("nome", nomeProduto)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value(nomeProduto));
    }

    @Test
    public void deveRetornarNotFoundParaProdutoInexistente() throws Exception {
        String nomeProduto = "ProdutoInexistente";
        mockMvc.perform(MockMvcRequestBuilders.get("/produto/findByName")
                        .param("nome", nomeProduto)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
