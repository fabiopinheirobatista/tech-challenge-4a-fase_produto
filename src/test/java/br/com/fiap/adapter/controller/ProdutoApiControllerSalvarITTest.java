package br.com.fiap.adapter.controller;

import br.com.fiap.TechChallenge4aFaseProdutoApplication;
import br.com.fiap.core.usecase.cliente.CadastrarProdutoUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TechChallenge4aFaseProdutoApplication.class)
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
@DisplayName("Testes de integração para cadastro de produtos via API")
class ProdutoApiControllerSalvarITTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CadastrarProdutoUseCase cadastrarProdutoUseCase;

    @Test
    @DisplayName("Deve salvar um produto com sucesso")
    void deveSalvarProdutoComSucesso() throws Exception {
        String produtoJson = """
            {
                "nome": "Produto Teste",
                "descricao": "Descrição do Produto Teste",
                "preco": 99.90,
                "sku": "SKU123",
                "quantidade": 10
            }
            """;

        mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(produtoJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Produto Teste"))
                .andExpect(jsonPath("$.sku").value("SKU123"))
                .andExpect(jsonPath("$.preco").value(99.90));
    }

    @Test
    @DisplayName("Deve retornar erro ao tentar salvar produto sem dados obrigatórios")
    void deveRetornarErroAoSalvarProdutoSemDados() throws Exception {
        String produtoJson = "{}";

        mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(produtoJson))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("Deve retornar erro ao tentar salvar produto com SKU duplicado")
    void deveRetornarErroAoSalvarProdutoComSkuDuplicado() throws Exception {
        String produtoJson = """
        {
            "nome": "Produto Teste",
            "sku": "SKU123",
            "preco": 99.90
        }
        """;
        mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(produtoJson))
                .andExpect(status().isCreated());

        String segundoProdutoJson = """
        {
            "nome": "Outro Produto",
            "sku": "SKU123",
            "preco": 150.00
        }
        """;

        mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(segundoProdutoJson))
                .andExpect(status().isConflict());
    }

}