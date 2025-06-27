package br.com.fiap.core.usecase.cliente;

import br.com.fiap.core.domain.entities.produto.Produto;
import br.com.fiap.core.exception.ProdutoComSkuJaCadastradoException;
import br.com.fiap.core.gataway.ProdutoGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AtualizarProdutoUseCaseTest {

    @Mock
    private ProdutoGateway produtoGateway;

    @InjectMocks
    private AtualizarProdutoUseCase atualizarProdutoUseCase;

    @Test
    void deveRetornarErroAoAtualizarProdutoComSkuJaCadastrado() {

        Long idProdutoAtual = 1L;
        String skuExistente = "SKU123";

        Produto produtoAtual = new Produto(
                idProdutoAtual,
                "Produto 1",
                skuExistente,
                BigDecimal.valueOf(10.0)
        );

        Produto produtoExistente = new Produto(
                2L,
                "Produto 2",
                skuExistente,
                BigDecimal.valueOf(20.0)
        );

        when(produtoGateway.buscarPorId(idProdutoAtual))
                .thenReturn(Optional.of(produtoAtual));
        when(produtoGateway.buscarPorSku(skuExistente))
                .thenReturn(Optional.of(produtoExistente));

        assertThrows(ProdutoComSkuJaCadastradoException.class, () ->
                atualizarProdutoUseCase.executar(idProdutoAtual, produtoAtual)
        );
    }
}