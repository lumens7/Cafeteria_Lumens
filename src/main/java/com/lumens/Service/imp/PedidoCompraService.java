package com.lumens.Service.imp;

import com.lumens.Domain.PedidoCompra;
import com.lumens.Domain.Produto;
import com.lumens.Repository.PedidoCompraRepository;
import com.lumens.Service.IPedidoCompraService;
import com.lumens.exception.PedidoInexistenteException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoCompraService implements IPedidoCompraService {

    private final PedidoCompraRepository pedidoCompraRepository;

    public PedidoCompraService(PedidoCompraRepository pedidoCompraRepository) {
        this.pedidoCompraRepository = pedidoCompraRepository;
    }

    @Override
    public PedidoCompra pedir(PedidoCompra pedidoCompra) {
        // Define status inicial e calcula valor total
        pedidoCompra.setStatusPedido(PedidoCompra.Status.ATIVO);

        double valorTotal = pedidoCompra.getProdutos().stream()
                .mapToDouble(Produto::getValor) // Usa mapToDouble em vez de mapToLong
                .sum();

        pedidoCompra.setValorTotal(valorTotal);
        return pedidoCompraRepository.save(pedidoCompra);
    }

    @Override
    public List<PedidoCompra> pesquisarTodos() {
        return pedidoCompraRepository.findAll();
    }

    @Override
    public List<Optional> pesquisarPorID(Long id) {
        return pedidoCompraRepository.findAllById(id); // Ou usa Optional se preferir apenas um
    }

    public PedidoCompra cancelarPedido(Long id) {
        PedidoCompra pedido = pedidoCompraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        pedido.setStatusPedido(PedidoCompra.Status.CANCELADO);
        return pedidoCompraRepository.save(pedido);
    }

    @Override
    public PedidoCompra concluirPedido(Long id) {
        PedidoCompra pedido = pedidoCompraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        pedido.setStatusPedido(PedidoCompra.Status.CONCLUIDO);
        return pedidoCompraRepository.save(pedido);
    }

    @Override
    public Boolean findByIdAndStatus(Long id) {
        PedidoCompra pedido = pedidoCompraRepository.findByIdAndStatusPedido(id, PedidoCompra.Status.ATIVO)
                .orElseThrow(() -> new PedidoInexistenteException("Pedido não encontrado"));
        return true;
    }
}
