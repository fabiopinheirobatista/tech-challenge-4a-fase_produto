package br.com.fiap.core.exception;

public class ProdutoComSkuJaCadastradoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ProdutoComSkuJaCadastradoException(String message) {
        super(message);
    }

}
