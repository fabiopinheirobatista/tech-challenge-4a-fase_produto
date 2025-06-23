package br.com.fiap.adapter.controller.response;

import java.math.BigDecimal;


public record ProdutoDtoResponse(
    Long id,
    String nome,
    String sku,
    BigDecimal preco
) {
}
