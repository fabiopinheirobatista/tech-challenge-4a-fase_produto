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
@DisplayName("Testes de integração para busca de produtos por SKU via API")
class ProdutoApiControllerBuscaPorSkuITTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() throws Exception {
        ProdutoDtoRequest produto = new ProdutoDtoRequest(
                "Produto Teste",
                "SKU123",
                new BigDecimal(100.90));

        mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produto)))
                .andExpect(status().isCreated());
    }

    @Test
    void deveBuscarProdutoPorSkuComSucesso() throws Exception {
        mockMvc.perform(get("/produtos/sku/{sku}", "SKU123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Produto Teste"))
                .andExpect(jsonPath("$.preco").value(new BigDecimal(100.90)))
                .andExpect(jsonPath("$.sku").value("SKU123"));
    }

    @Test
    void deveRetornarNotFoundAoBuscarSkuInexistente() throws Exception {

        mockMvc.perform(get("/produtos/{{sku}}", "SKU_INEXISTENTE"))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    Exception resolvedException = result.getResolvedException();
                    assert resolvedException != null;
                });
    }

    @Test
    void deveRetornarBadRequestAoBuscarComSkuVazio() throws Exception {
        mockMvc.perform(get("/produtos/sku/ "))
                .andExpect(status().isNotFound());
    }
}