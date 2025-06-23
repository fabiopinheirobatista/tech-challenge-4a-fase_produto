package br.com.fiap.adapter.controller.request;

import java.math.BigDecimal;

public record ProdutoDtoRequest(      
    String nome,
    String sku,
    BigDecimal preco
) {
}
