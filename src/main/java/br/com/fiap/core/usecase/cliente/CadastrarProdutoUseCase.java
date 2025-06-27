package br.com.fiap.core.usecase.cliente;

import br.com.fiap.core.domain.entities.produto.Produto;
import br.com.fiap.core.exception.ProdutoComSkuJaCadastradoException;
import br.com.fiap.core.gataway.ProdutoGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CadastrarProdutoUseCase {

    private final ProdutoGateway produtoGateway;

    public Produto executar(Produto produto) {
        produtoGateway.buscarPorSku(produto.getSku())
                .ifPresent(p -> {
                    throw new ProdutoComSkuJaCadastradoException(produto.getSku());
                });
        return produtoGateway.salvar(produto);
    }

}