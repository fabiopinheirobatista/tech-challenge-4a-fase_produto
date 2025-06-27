package br.com.fiap.adapter.persistence.repository;

import br.com.fiap.adapter.persistence.entity.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Long>    {
    ProdutoEntity findBySku(String sku);
    List<ProdutoEntity> findByNomeContainingIgnoreCase(String nome);
}
