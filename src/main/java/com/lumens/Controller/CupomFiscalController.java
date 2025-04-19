package com.lumens.Controller;

import com.lumens.Domain.CupomFiscal;
import com.lumens.Service.imp.CupomFiscalService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cupom")
public class CupomFiscalController {

    private final CupomFiscalService cupomFiscalService;

    public CupomFiscalController(CupomFiscalService cupomFiscalService) {
        this.cupomFiscalService = cupomFiscalService;
    }

    @PostMapping("/emitir/{idPedido}")
    public CupomFiscal emitir(@PathVariable Long idPedido) {
        return cupomFiscalService.gerarCupom(idPedido);
    }

    @GetMapping("/{id}")
    public CupomFiscal buscarPorId(@PathVariable Long id) {
        return cupomFiscalService.buscarCupomId(id);
    }
}
