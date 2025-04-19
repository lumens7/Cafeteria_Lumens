package com.lumens.Service.imp;

import com.lumens.Domain.ItensProdução;
import com.lumens.Repository.ItensRepository;
import com.lumens.exception.DuplicidadeException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItensService {

    private final ItensRepository itensRepository;

    public ItensService(ItensRepository itensRepository) {
        this.itensRepository = itensRepository;
    }

    public void criarItensIniciais() {
        List<String> nomesDosItens = List.of(
                "cafe soluvel",
                "canela",
                "agua",
                "leite",
                "açucar",
                "leite em pó",
                "achocolatado",
                "cravo"
        );

        for (String nome : nomesDosItens) {
            if (nome != null && !nome.isBlank()) {
                salvar(nome);
            }
        }
    }

    public void salvar(String nome) {
        if(itensRepository.findByNome(nome) == null){
            ItensProdução item = new ItensProdução();
            item.setNome(nome);
            itensRepository.save(item);
        }
        else {
            throw new DuplicidadeException("O item '" + nome + "' já está cadastrado.");
        }
    }
    public List<ItensProdução> pesquisarPorNome(List<String> nome){
        return itensRepository.findByNomeIn(nome);
    }
}
