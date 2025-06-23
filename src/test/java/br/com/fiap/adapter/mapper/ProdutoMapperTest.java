package br.com.fiap.adapter.mapper;

import br.com.fiap.adapter.controller.request.ProdutoDtoRequest;
import br.com.fiap.adapter.controller.response.ProdutoDtoResponse;
import br.com.fiap.adapter.persistence.entity.ProdutoEntity;
import br.com.fiap.core.domain.entities.produto.Produto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoMapperTest {

    private ProdutoMapper mapper;
    private static final Long ID = 1L;
    private static final String NOME = "Produto Teste";
    private static final String SKU = "SKU123";
    private static final BigDecimal PRECO = BigDecimal.valueOf(99.90);

    @BeforeEach
    void setUp() {
        mapper = new ProdutoMapper();
    }

    @Test
    void deveConverterProdutoDtoRequestParaProduto() {
        ProdutoDtoRequest dto = new ProdutoDtoRequest(NOME, SKU, PRECO);
        Produto produto = mapper.toProduto(dto);
        assertNotNull(produto);
        assertNull(produto.getId());
        assertEquals(NOME, produto.getNome());
        assertEquals(SKU, produto.getSku());
        assertEquals(PRECO, produto.getPreco());
    }

    @Test
    void deveRetornarNullQuandoProdutoDtoRequestForNulo() {
        assertNull(mapper.toProduto((ProdutoDtoRequest) null));
    }

    @Test
    void deveConverterProdutoDtoResponseParaProduto() {
        ProdutoDtoResponse dto = new ProdutoDtoResponse(ID, NOME, SKU, PRECO);

        Produto produto = mapper.toProduto(dto);

        assertNotNull(produto);
        assertEquals(ID, produto.getId());
        assertEquals(NOME, produto.getNome());
        assertEquals(SKU, produto.getSku());
        assertEquals(PRECO, produto.getPreco());
    }

    @Test
    void deveRetornarNullQuandoProdutoDtoResponseForNulo() {
        assertNull(mapper.toProduto((ProdutoDtoResponse) null));
    }

    @Test
    void deveConverterProdutoParaProdutoDtoRequest() {
        Produto produto = new Produto(ID, NOME, SKU, PRECO);

        ProdutoDtoRequest dto = mapper.toProdutoDtoRequest(produto);

        assertNotNull(dto);
        assertEquals(NOME, dto.nome());
        assertEquals(SKU, dto.sku());
        assertEquals(PRECO, dto.preco());
    }

    @Test
    void deveRetornarNullQuandoProdutoForNuloAoConverterParaProdutoDtoRequest() {
        assertNull(mapper.toProdutoDtoRequest(null));
    }

    @Test
    void deveConverterProdutoParaProdutoDtoResponse() {
        Produto produto = new Produto(ID, NOME, SKU, PRECO);

        ProdutoDtoResponse dto = mapper.toProdutoDtoResponse(produto);

        assertNotNull(dto);
        assertEquals(ID, dto.id());
        assertEquals(NOME, dto.nome());
        assertEquals(SKU, dto.sku());
        assertEquals(PRECO, dto.preco());
    }

    @Test
    void deveRetornarNullQuandoProdutoForNuloAoConverterParaProdutoDtoResponse() {
        assertNull(mapper.toProdutoDtoResponse(null));
    }

    @Test
    void deveConverterProdutoEntityParaProduto() {
        ProdutoEntity entity = new ProdutoEntity(ID, NOME, SKU, PRECO);

        Produto produto = mapper.toProduto(entity);

        assertNotNull(produto);
        assertEquals(ID, produto.getId());
        assertEquals(NOME, produto.getNome());
        assertEquals(SKU, produto.getSku());
        assertEquals(PRECO, produto.getPreco());
    }

    @Test
    void deveRetornarNullQuandoProdutoEntityForNulo() {
        assertNull(mapper.toProduto((ProdutoEntity) null));
    }

    @Test
    void deveConverterProdutoParaProdutoEntity() {
        Produto produto = new Produto(ID, NOME, SKU, PRECO);

        ProdutoEntity entity = mapper.toProdutoEntity(produto);

        assertNotNull(entity);
        assertEquals(ID, entity.getId());
        assertEquals(NOME, entity.getNome());
        assertEquals(SKU, entity.getSku());
        assertEquals(PRECO, entity.getPreco());
    }

    @Test
    void deveRetornarNullQuandoProdutoForNuloAoConverterParaProdutoEntity() {
        assertNull(mapper.toProdutoEntity(null));
    }
}