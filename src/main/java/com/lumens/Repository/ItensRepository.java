package com.lumens.Repository;

import com.lumens.Domain.ItensProdução;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItensRepository extends JpaRepository<ItensProdução, Long> {
    List<ItensProdução> findByNomeIn(List<String> nomes);

    ItensProdução findByNome(String nome);
}
