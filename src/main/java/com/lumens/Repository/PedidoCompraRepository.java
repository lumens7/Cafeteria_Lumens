package com.lumens.Repository;

import com.lumens.Domain.PedidoCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoCompraRepository extends JpaRepository<PedidoCompra, Long> {
    List<Optional> findAllById(Long id);
    Optional<PedidoCompra> findByIdAndStatusPedido(Long id, PedidoCompra.Status statusPedido);
}
