package com.lumens.Controller;

import com.lumens.Domain.PedidoCompra;
import com.lumens.Service.imp.PedidoCompraService;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedido")
public class PedidoCompraController {

    private final PedidoCompraService pedidoCompraService;

    public PedidoCompraController(PedidoCompraService pedidoCompraService) {
        this.pedidoCompraService = pedidoCompraService;
    }

    @PostMapping("/criar")
    public PedidoCompra criarPedido(@RequestBody PedidoCompra pedido) {
        return pedidoCompraService.pedir(pedido);
    }

    @Transactional
    @GetMapping("/todos")
    public List<PedidoCompra> listarTodos() {
        return pedidoCompraService.pesquisarTodos();
    }

    @PostMapping("/cancelar/{id}")
    public PedidoCompra cancelarPedido(@PathVariable Long id) {
        return pedidoCompraService.cancelarPedido(id);
    }

//    @PostMapping("/concluir/{id}")
//    public PedidoCompra concluirPedido(@PathVariable Long id) {
//        return pedidoCompraService.concluirPedido(id);
//    }
}
