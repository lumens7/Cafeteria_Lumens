package com.lumens;

import com.lumens.Service.imp.CriacaoCafes;
import com.lumens.Service.imp.ItensService;
import com.lumens.exception.DuplicidadeException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InicializadorDeDados implements CommandLineRunner {

    private final ItensService itensService;
    private final CriacaoCafes criacaoCafes;

    public InicializadorDeDados(ItensService itensService, CriacaoCafes criacaoCafes) {
        this.itensService = itensService;
        this.criacaoCafes = criacaoCafes;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            itensService.criarItensIniciais();
            criacaoCafes.criarCafe();
        } catch (DuplicidadeException e) {
            System.out.println("Dados já inicializados: " + e.getMessage());
        }
    }
}