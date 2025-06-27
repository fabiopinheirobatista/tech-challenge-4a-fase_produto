package br.com.fiap.core.usecase.cliente;

import br.com.fiap.core.exception.ProdutoNaoEncontradoException;
import br.com.fiap.core.gataway.ProdutoGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeletarProdutoUseCase {

    private final ProdutoGateway produtoGateway;

    public void executar(Long id) {
        produtoGateway
                .buscarPorId(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto não encontrado com o id: " + id));
        produtoGateway.deletar(id);
    }

}