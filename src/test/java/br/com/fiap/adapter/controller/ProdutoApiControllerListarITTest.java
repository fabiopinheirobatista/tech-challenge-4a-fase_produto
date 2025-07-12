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
@DisplayName("Testes de integração para listagem de produtos via API")
class ProdutoApiControllerListarITTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    @DisplayName("Configura o ambiente de teste cadastrando 3 produtos para os testes")
    void setup() throws Exception {
        cadastrarProduto("Produto 1", 10.0, "SKU001");
        cadastrarProduto("Produto 2", 20.0, "SKU002");
        cadastrarProduto("Produto 3", 30.0, "SKU003");
    }

    private void cadastrarProduto(String nome, double preco, String sku) throws Exception {
        ProdutoDtoRequest produto = new ProdutoDtoRequest(nome, sku, new BigDecimal(preco));
        mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produto)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Deve retornar uma lista com todos os produtos cadastrados, verificando nome e SKU de cada um")
    void deveListarTodosProdutosComSucesso() throws Exception {
        mockMvc.perform(get("/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].nome").value("Produto 1"))
                .andExpect(jsonPath("$[0].sku").value("SKU001"))
                .andExpect(jsonPath("$[1].nome").value("Produto 2"))
                .andExpect(jsonPath("$[1].sku").value("SKU002"))
                .andExpect(jsonPath("$[2].nome").value("Produto 3"))
                .andExpect(jsonPath("$[2].sku").value("SKU003"));
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia quando não houver produtos cadastrados")
    void deveRetornarListaVaziaQuandoNaoHouverProdutos() throws Exception {
        mockMvc.perform(get("/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3));
    }
}