package com.lumens.Service.imp;

import com.lumens.Domain.ItensProdução;
import com.lumens.Domain.Produto;
import com.lumens.Repository.ProdutoRepository;
import com.lumens.exception.DuplicidadeException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class CriacaoCafes {

    private final ProdutoService produtoService;
    private final ProdutoRepository produtoRepository;
    private final ItensService itensService;

    public CriacaoCafes(ProdutoService produtoService, ItensService itensService, ProdutoRepository produtoRepository) {
        this.produtoService = produtoService;
        this.itensService = itensService;
        this.produtoRepository = produtoRepository;
    }

    public boolean criarCafe() {
        criarCafePersonalizado("Cappuccino", List.of("cafe soluvel", "leite em pó", "achocolatado", "canela"), 6.50d);
        criarCafePersonalizado("Cafe expresso", List.of("cafe soluvel", "agua", "açucar", "leite"), 4.25d);
        criarCafePersonalizado("Cafe Lumens", List.of("cafe soluvel", "agua", "achocolatado", "canela", "cravo", "leite em pó"), 7.00d);
        return true;
    }

    private void criarCafePersonalizado(String nome, List<String> nomesItens, double valor) {
        if(produtoRepository.findByNome(nome) == null) {
            List<ItensProdução> itens = itensService.pesquisarPorNome(nomesItens);

            Produto produto = new Produto();
            produto.setNome(nome);
            produto.setValor(valor);
            produto.setDescricaoItensProd(new ArrayList<>(itens));

            produtoService.criarCafe(produto);
        }
        else{
            throw new DuplicidadeException("O item '" + nome + "' já está cadastrado.");
        }
    }
}
