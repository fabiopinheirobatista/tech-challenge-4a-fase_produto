package br.com.fiap.core.usecase.cliente;

import br.com.fiap.core.domain.entities.produto.Produto;
import br.com.fiap.core.exception.ProdutoNaoEncontradoException;
import br.com.fiap.core.gataway.ProdutoGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BuscarProdutoPorIdUseCase {

    private final ProdutoGateway produtoGateway;

    public Produto executar(Long id) {
       return  produtoGateway
               .buscarPorId(id)
               .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto não encontrado com o id: " + id));
    }

}