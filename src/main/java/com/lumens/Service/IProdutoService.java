package com.lumens.Service;

import com.lumens.Domain.Produto;

import java.util.List;

public interface IProdutoService {

    Produto criarCafe(Produto produto);
    List<Produto> pesquisarTodos();
    Produto pesquisarPorNome(String nome);
}
