package com.lumens.Service;

import com.lumens.Domain.PedidoCompra;

import java.util.List;
import java.util.Optional;

public interface IPedidoCompraService {
    PedidoCompra pedir(PedidoCompra pedidoCompra);
    List<PedidoCompra> pesquisarTodos();
    List<Optional> pesquisarPorID(Long id);
    PedidoCompra cancelarPedido(Long id);
    PedidoCompra concluirPedido(Long id);
    Boolean findByIdAndStatus(Long id);
}
