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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TechChallenge4aFaseProdutoApplication.class)
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
@DisplayName("Testes de integração para atualização de produtos via API")
class ProdutoApiControllerAtualizarITTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Long produtoId;

    @BeforeEach
    @DisplayName("Configura o ambiente de teste cadastrando um produto para ser atualizado")
    void setup() throws Exception {
        ProdutoDtoRequest produto = new ProdutoDtoRequest(
                "Produto Teste",
                "SKU123",
                new BigDecimal(100.90));
        String response = mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produto)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        produtoId = objectMapper.readTree(response).get("id").asLong();
    }

    @Test
    @DisplayName("Deve atualizar um produto existente com sucesso, validando os novos valores")
    void deveAtualizarProdutoComSucesso() throws Exception {


        ProdutoDtoRequest produto = new ProdutoDtoRequest(
                "Produto Teste",
                "SKU123",
                new BigDecimal(149.90));

        mockMvc.perform(put("/produtos/{id}", produtoId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Produto Teste"))
                .andExpect(jsonPath("$.preco").value(new BigDecimal(149.90)))
                .andExpect(jsonPath("$.sku").value("SKU123"));
    }

    @Test
    @DisplayName("Deve retornar erro 405 (Method Not Allowed) ao tentar atualizar um produto inexistente")
    void deveRetornarNotFoundAoAtualizarProdutoInexistente() throws Exception {
        ProdutoDtoRequest produto = new ProdutoDtoRequest(
                "Produto Teste",
                "SKU123",
                new BigDecimal(149.90));

        mockMvc.perform(put("/produtos/sku/{sku}", "88888")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produto)))
                .andExpect(status().isMethodNotAllowed());
    }
}