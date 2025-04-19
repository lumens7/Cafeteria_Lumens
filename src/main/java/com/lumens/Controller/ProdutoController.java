package com.lumens.Controller;

import com.lumens.Domain.Produto;
import com.lumens.Service.imp.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/produto")
@Tag(name = "Produto", description = "API para gerenciamento de produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping("/findAll")
    @Operation(summary = "Listar todos os produtos", description = "Retorna uma lista com todos os produtos cadastrados")
    public ResponseEntity<List<Produto>> pesquisarTodos() {
        return ResponseEntity.ok(produtoService.pesquisarTodos());
    }

    @GetMapping("/findByName")
    @Operation(summary = "Buscar produto por nome", description = "Retorna um produto específico pelo nome")
    public ResponseEntity<Produto> pesquisarPorNome(@RequestParam String nome) {
        Produto produto = produtoService.pesquisarPorNome(nome);
        return produto != null ? ResponseEntity.ok(produto) : ResponseEntity.notFound().build();
    }
}