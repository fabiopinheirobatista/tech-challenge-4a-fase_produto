package br.com.fiap.core.usecase.cliente;

import br.com.fiap.core.domain.entities.produto.Produto;
import br.com.fiap.core.gataway.ProdutoGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarTodosProdutosUseCase {

    private final ProdutoGateway produtoGateway;

    public List<Produto> executar() {
        return produtoGateway.listarTodos();
    }

}