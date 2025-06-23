package br.com.fiap.adapter.gateway;

import br.com.fiap.adapter.mapper.ProdutoMapper;
import br.com.fiap.adapter.persistence.entity.ProdutoEntity;
import br.com.fiap.adapter.persistence.repository.ProdutoRepository;
import br.com.fiap.core.domain.entities.produto.Produto;
import br.com.fiap.core.gataway.ProdutoGateway;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RepositorioDeProdutoJPAGatewayImpl implements ProdutoGateway {

    private final ProdutoRepository produtoRepository;
    private ProdutoMapper mapper = new ProdutoMapper();
    
    public RepositorioDeProdutoJPAGatewayImpl(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    public Produto salvar(Produto produto) {
        ProdutoEntity produtoEntity = mapper.toProdutoEntity(produto);
        produtoEntity = produtoRepository.save(produtoEntity);
        return mapper.toProduto(produtoEntity);
        
    }

    @Override
    public Optional<Produto> buscarPorId(Long id) {
        Optional<ProdutoEntity> produtoEntity = produtoRepository.findById(id);
        return produtoEntity.map(mapper::toProduto);
    }

    @Override
    public Optional<Produto> buscarPorSku(String sku) {
        ProdutoEntity produtoEntity = produtoRepository.findBySku(sku);
        return Optional.ofNullable(produtoEntity).map(mapper::toProduto);
    }

    @Override
    public List<Produto> buscarPorNome(String nome) {
        List<ProdutoEntity> produtos = produtoRepository.findByNomeContainingIgnoreCase(nome);
        return produtos.stream().map(mapper::toProduto).toList();
    }

    @Override
    public void deletar(Long id) {
        produtoRepository.deleteById(id);
    }

    @Override
    public Produto atualizar(Produto produto  ) {
        ProdutoEntity produtoEntity = produtoRepository.save(mapper.toProdutoEntity(produto));
        return mapper.toProduto(produtoEntity);
    }

    @Override
    public List<Produto> listarTodos() {
        List<ProdutoEntity> produtos = produtoRepository.findAll();
        return produtos.stream().map(mapper::toProduto).toList();
    }
    
}
