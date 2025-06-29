package br.com.fiap.adapter.persistence.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "produtos", uniqueConstraints = {
        @UniqueConstraint(name = "uk_produto_sku", columnNames = {"sku"})
})
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String sku;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    public ProdutoEntity(String nome, String sku, BigDecimal preco) {
        this.nome = nome;
        this.sku = sku;
        this.preco = preco;
    }
}

