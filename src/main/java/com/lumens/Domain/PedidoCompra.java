package com.lumens.Domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_pedido_compra")
public class PedidoCompra {
    public enum Status{
        ATIVO, CANCELADO, CONCLUIDO;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Produto> produtos;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status statusPedido;
    private Double valorTotal;

}
