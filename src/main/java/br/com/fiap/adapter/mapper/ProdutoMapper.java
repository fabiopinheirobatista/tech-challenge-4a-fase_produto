package br.com.fiap.adapter.mapper;

import br.com.fiap.adapter.controller.request.ProdutoDtoRequest;
import br.com.fiap.adapter.controller.response.ProdutoDtoResponse;
import br.com.fiap.adapter.persistence.entity.ProdutoEntity;
import br.com.fiap.core.domain.entities.produto.Produto;

public class ProdutoMapper {

    // ProdutoDtoRequest -> Produto
    public  Produto toProduto(ProdutoDtoRequest dto) {
        if (dto == null) return null;
        return new Produto(
            null, 
            dto.nome(),
            dto.sku(),
            dto.preco()
        );
    }

    // Produto -> ProdutoDtoRequest
    public  ProdutoDtoRequest toProdutoDtoRequest(Produto produto) {
        if (produto == null) return null;
        return new ProdutoDtoRequest(
            produto.getNome(),
            produto.getSku(),
            produto.getPreco()
        );
    }

    // ProdutoDtoResponse -> Produto
    public  Produto toProduto(ProdutoDtoResponse dto) {
        if (dto == null) return null;
        return new Produto(
            dto.id(),
            dto.nome(),
            dto.sku(),
            dto.preco()
        );
    }

    // Produto -> ProdutoDtoResponse
    public  ProdutoDtoResponse toProdutoDtoResponse(Produto produto) {
        if (produto == null) return null;
        return new ProdutoDtoResponse(
            produto.getId(),
            produto.getNome(),
            produto.getSku(),
            produto.getPreco()
        );
    }

    public  Produto toProduto(ProdutoEntity entity) {
        if (entity == null) return null;
        return new Produto(
            entity.getId(),
            entity.getNome(),
            entity.getSku(),
            entity.getPreco()
        );
    }

    public  ProdutoEntity toProdutoEntity(Produto produto) {
        if (produto == null) return null;
        return new ProdutoEntity(
            produto.getId(),
            produto.getNome(),
            produto.getSku(),
            produto.getPreco()
        );
    }


    
}