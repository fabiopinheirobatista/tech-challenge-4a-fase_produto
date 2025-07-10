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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TechChallenge4aFaseProdutoApplication.class)
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
@DisplayName("Testes de integração para deleção de produtos via API")
class ProdutoApiControllerDeletarITTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveDeletarProdutoComSucesso() throws Exception {
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

        Long produtoId = objectMapper.readTree(response).get("id").asLong();

        mockMvc.perform(delete("/produtos/{id}", produtoId))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/produtos/{id}", produtoId))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveRetornarNotFoundAoDeletarProdutoInexistente() throws Exception {
        Long idInexistente = 99999L;

        mockMvc.perform(delete("/produtos/{id}", idInexistente))
                .andExpect(status().isNotFound())
                .andExpect(result -> {
                    assert result.getResolvedException() instanceof ProdutoNaoEncontradoException;
                });
    }

    @Test
    void deveRetornarBadRequestAoDeletarComIdInvalido() throws Exception {
        mockMvc.perform(delete("/produtos/abc"))
                .andExpect(status().isBadRequest());
    }
}