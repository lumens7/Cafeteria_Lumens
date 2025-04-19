package com.lumens.Domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Entity(name = "tb_produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    @ManyToMany(fetch = FetchType.EAGER) // Alterado para EAGER
    @JoinTable(
            name = "tb_produto_descricao_itens_prod",
            joinColumns = @JoinColumn(name = "tb_produto_id"),
            inverseJoinColumns = @JoinColumn(name = "descricao_itens_prod_id")
    )
    private List<ItensProdução> descricaoItensProd;


    private Double valor;


}
