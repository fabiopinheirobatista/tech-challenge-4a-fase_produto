package br.com.fiap.adapter.controller;

import br.com.fiap.TechChallenge4aFaseProdutoApplication;
import br.com.fiap.adapter.controller.request.ProdutoDtoRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TechChallenge4aFaseProdutoApplication.class)
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
@DisplayName("Testes de integração para busca de produtos por nome via API")
class ProdutoApiControllerBuscaPorNomeITTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    @DisplayName("Configura o ambiente de teste cadastrando produtos com diferentes tipos de bebidas")
    void setup() throws Exception {
        cadastrarProduto("Refrigerante Cola", 8.90, "SKU001");
        cadastrarProduto("Refrigerante Laranja",7.90, "SKU002");
        cadastrarProduto("Suco de Uva", 12.90, "SKU003");
    }

    private void cadastrarProduto(String nome, double preco, String sku) throws Exception {
        ProdutoDtoRequest produto = new ProdutoDtoRequest(nome, sku, new BigDecimal(preco));
        mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produto)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Deve buscar produtos que contenham parte do nome especificado")
    void deveBuscarProdutosPorNomeComSucesso() throws Exception {
        mockMvc.perform(get("/produtos/nome/{nome}", "Refrigerante"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nome").value("Refrigerante Cola"))
                .andExpect(jsonPath("$[1].nome").value("Refrigerante Laranja"));
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia quando não encontrar produtos com o nome especificado")
    void deveRetornarListaVaziaQuandoNaoEncontrarProdutos() throws Exception {
        mockMvc.perform(get("/produtos/nome/{nome}", "Produto Inexistente"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    @DisplayName("Deve buscar produto pelo nome exato e validar seus dados")
    void deveBuscarProdutoComNomeExato() throws Exception {
        mockMvc.perform(get("/produtos/nome/{nome}", "Suco de Uva"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].nome").value("Suco de Uva"))
                .andExpect(jsonPath("$[0].sku").value("SKU003"));
    }
}