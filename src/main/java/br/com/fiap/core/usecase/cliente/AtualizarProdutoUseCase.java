package br.com.fiap.core.usecase.cliente;

import br.com.fiap.core.domain.entities.produto.Produto;
import br.com.fiap.core.exception.ProdutoComSkuJaCadastradoException;
import br.com.fiap.core.exception.ProdutoNaoEncontradoException;
import br.com.fiap.core.gataway.ProdutoGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AtualizarProdutoUseCase {

    private final ProdutoGateway produtoGateway;

    public Produto executar(Long id, Produto produto) {
        produtoGateway.buscarPorId(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto não encontrado com o id: " + id));

        Optional<Produto> produtoPorSku = produtoGateway.buscarPorSku(produto.getSku());

       if (produtoPorSku.isPresent() && !produtoPorSku.get().getId().equals(id)) {
           throw new ProdutoComSkuJaCadastradoException("Já existe um produto cadastrado com o SKU: " + produto.getSku());
       }
        produto.setId(id);
       return produtoGateway.atualizar(produto);
    }

}