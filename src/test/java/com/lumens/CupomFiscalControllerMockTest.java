package com.lumens;

import com.lumens.Controller.CupomFiscalController;
import com.lumens.Domain.CupomFiscal;
import com.lumens.Service.imp.CupomFiscalService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(CupomFiscalController.class)
public class CupomFiscalControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CupomFiscalService cupomFiscalService;

    @Test
    public void quandoGerarCupom_entaoRetornarCupom() throws Exception {
        // Configuração do mock
        given(cupomFiscalService.gerarCupom(anyLong())).willReturn(new CupomFiscal());

        // Teste
        mockMvc.perform(post("/cupom/emitir/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}