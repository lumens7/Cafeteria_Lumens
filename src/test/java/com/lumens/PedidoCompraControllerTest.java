package com.lumens;

import com.lumens.Domain.PedidoCompra;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PedidoCompraControllerTest extends BaseControllerTest {

    @Test
    public void deveCriarNovoPedido() throws Exception {
        PedidoCompra pedido = new PedidoCompra();
        // Configure seu pedido aqui

        mockMvc.perform(MockMvcRequestBuilders.post("/pedido")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedido)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    public void deveRetornarTodosPedidos() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/pedido")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void deveCancelarPedido() throws Exception {
        Long pedidoId = 1L;
        mockMvc.perform(MockMvcRequestBuilders.put("/pedido/cancelar/" + pedidoId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusPedido").value("CANCELADO"));
    }
}