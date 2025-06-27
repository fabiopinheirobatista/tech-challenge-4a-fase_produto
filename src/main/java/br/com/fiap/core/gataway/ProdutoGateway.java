package br.com.fiap.core.gataway;

import br.com.fiap.core.domain.entities.produto.Produto;

import java.util.List;
import java.util.Optional;

public interface ProdutoGateway {
    Produto salvar(Produto cliente);
    Optional<Produto> buscarPorId(Long id);
    Optional<Produto> buscarPorSku(String sku);
    List<Produto> buscarPorNome(String nome);
    void deletar(Long id);
    Produto atualizar(Produto cliente);
    List<Produto> listarTodos();
}
