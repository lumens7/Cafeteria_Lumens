package com.lumens.Repository;

import com.lumens.Domain.Produto;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    @EntityGraph(attributePaths = {"descricaoItensProd"})
    List<Produto> findAll();
    Produto findByNome(String nomes);

}
