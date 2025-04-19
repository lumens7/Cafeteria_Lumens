package com.lumens.Service;

import com.lumens.Domain.CupomFiscal;

public interface ICupomFiscalService {
    CupomFiscal gerarCupom(Long idPedido);
    CupomFiscal buscarCupomId(Long id);
}
