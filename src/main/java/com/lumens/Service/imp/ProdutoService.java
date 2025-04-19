package com.lumens.Service.imp;

import com.lumens.Domain.Produto;
import com.lumens.Repository.ProdutoRepository;
import com.lumens.Service.IProdutoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService implements IProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    public Produto criarCafe(Produto produto) {
        return produtoRepository.save(produto);
    }

    @Override
    public List<Produto> pesquisarTodos() {
        return produtoRepository.findAll();
    }

    @Override
    public Produto pesquisarPorNome(String nome) {
        return produtoRepository.findByNome(nome);
    }
}
