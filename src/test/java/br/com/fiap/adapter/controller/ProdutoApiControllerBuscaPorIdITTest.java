package br.com.fiap.adapter.controller;

import br.com.fiap.TechChallenge4aFaseProdutoApplication;
import br.com.fiap.adapter.controller.request.ProdutoDtoRequest;
import br.com.fiap.core.exception.ProdutoNaoEncontradoException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@DisplayName("Testes de integração para busca de produtos por ID via API")
class ProdutoApiControllerBuscaPorIdITTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve criar um produto e depois buscar por ID com sucesso, validando todos os campos retornados")
    void deveBuscarProdutoPorIdComSucesso() throws Exception {
        ProdutoDtoRequest produto = new ProdutoDtoRequest(
                "Produto Teste",
                "SKU123",
                new BigDecimal(100.90)
        );

        String produtoJson = objectMapper.writeValueAsString(produto);

        String response = mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(produtoJson))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long produtoId = objectMapper.readTree(response).get("id").asLong();

        mockMvc.perform(get("/produtos/{id}", produtoId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Produto Teste"))
                .andExpect(jsonPath("$.preco").value(new BigDecimal(100.90)))
                .andExpect(jsonPath("$.sku").value("SKU123"));
    }

    @Test
    @DisplayName("Deve retornar status 404 (Not Found) ao tentar buscar um produto com ID inexistente")
    void deveRetornarNotFoundAoBuscarProdutoInexistente() throws Exception {
        Long idInexistente = 99999L;

        mockMvc.perform(get("/produtos/{id}", idInexistente))
                .andExpect(status().isNotFound())
                .andExpect(result -> {
                    Exception resolvedException = result.getResolvedException();
                    assert resolvedException != null;
                    assert resolvedException instanceof ProdutoNaoEncontradoException;
                });
    }



    @Test
    @DisplayName("Deve retornar status 400 (Bad Request) ao tentar buscar um produto com ID em formato inválido")
    void deveRetornarBadRequestAoBuscarComIdInvalido() throws Exception {
        mockMvc.perform(get("/produtos/abc"))
                .andExpect(status().isBadRequest());
    }
}