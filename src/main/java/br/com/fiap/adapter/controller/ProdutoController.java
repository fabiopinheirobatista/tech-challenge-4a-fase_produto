package br.com.fiap.adapter.controller;

import br.com.fiap.adapter.controller.request.ProdutoDtoRequest;
import br.com.fiap.adapter.controller.response.ProdutoDtoResponse;

import java.util.List;

public interface ProdutoController {

    ProdutoDtoResponse salvar(ProdutoDtoRequest cliente);
    ProdutoDtoResponse buscarPorId(Long id);
    ProdutoDtoResponse buscarPorSku(String sku);
    List<ProdutoDtoResponse> buscarPorNome(String nome);
    void deletar(Long id);
    ProdutoDtoResponse atualizar(Long id, ProdutoDtoRequest cliente);
    List<ProdutoDtoResponse> listarTodos();
}