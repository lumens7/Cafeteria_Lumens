package com.lumens.exception;

public class PedidoInexistenteException extends RuntimeException {
    public PedidoInexistenteException(String mensagem) {
        super(mensagem);
    }
}
