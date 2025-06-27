package br.com.fiap.adapter.controller;

import br.com.fiap.adapter.controller.request.ProdutoDtoRequest;
import br.com.fiap.adapter.controller.response.ProdutoDtoResponse;
import br.com.fiap.adapter.mapper.ProdutoMapper;
import br.com.fiap.core.domain.entities.produto.Produto;
import br.com.fiap.core.usecase.cliente.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoApiController implements ProdutoController{

    private final CadastrarProdutoUseCase cadastrarProdutoUseCase;
    private final BuscarProdutoPorIdUseCase buscarProdutoPorIdUseCase;
    private final BuscarProdutoPorSkuUseCase buscarProdutoPorSkuUseCase;
    private final BuscarProdutoPorNomeUseCase buscarProdutoPorNomeUseCase;
    private final DeletarProdutoUseCase deletarProdutoUseCase;
    private final AtualizarProdutoUseCase atualizarProdutoUseCase;
    private final ListarTodosProdutosUseCase listarTodosProdutosUseCase;
    private ProdutoMapper mapper;

    public ProdutoApiController(
        CadastrarProdutoUseCase cadastrarProdutoUseCase,
        BuscarProdutoPorIdUseCase buscarProdutoPorIdUseCase,
        BuscarProdutoPorSkuUseCase buscarProdutoPorSkuUseCase,
        BuscarProdutoPorNomeUseCase buscarProdutoPorNomeUseCase,
        DeletarProdutoUseCase deletarProdutoUseCase,
        AtualizarProdutoUseCase atualizarProdutoUseCase,
        ListarTodosProdutosUseCase listarTodosProdutosUseCase
    ) {
        this.cadastrarProdutoUseCase = cadastrarProdutoUseCase;
        this.buscarProdutoPorIdUseCase = buscarProdutoPorIdUseCase;
        this.buscarProdutoPorSkuUseCase = buscarProdutoPorSkuUseCase;
        this.buscarProdutoPorNomeUseCase = buscarProdutoPorNomeUseCase;
        this.deletarProdutoUseCase = deletarProdutoUseCase;
        this.atualizarProdutoUseCase = atualizarProdutoUseCase;
        this.listarTodosProdutosUseCase = listarTodosProdutosUseCase;
        this.mapper = new ProdutoMapper();
    }
    


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public ProdutoDtoResponse salvar(@RequestBody ProdutoDtoRequest produtoDtoRequest) {
        Produto produto = mapper.toProduto(produtoDtoRequest);
        Produto produtoSalvo = cadastrarProdutoUseCase.executar(produto);
        return mapper.toProdutoDtoResponse(produtoSalvo);
    }

    @GetMapping("/{id}")
    @Override
    public ProdutoDtoResponse buscarPorId(@PathVariable Long id) {
        return mapper.toProdutoDtoResponse(buscarProdutoPorIdUseCase.executar(id));
    }

    @GetMapping("/sku/{sku}")
    @Override
    public ProdutoDtoResponse buscarPorSku(@PathVariable String sku) {
        return mapper.toProdutoDtoResponse(buscarProdutoPorSkuUseCase.executar(sku));
    }

    @GetMapping("/nome/{nome}")
    @Override
    public List<ProdutoDtoResponse> buscarPorNome(@PathVariable String nome) {
        List<Produto> produtos = buscarProdutoPorNomeUseCase.executar(nome);
        return produtos.stream()
                .map(mapper::toProdutoDtoResponse)
                .toList();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void deletar(@PathVariable Long id) {
        deletarProdutoUseCase.executar(id);
    }

    @PutMapping("/{id}")
    @Override
    public ProdutoDtoResponse atualizar(@PathVariable Long id, @RequestBody ProdutoDtoRequest produtoDtoRequest) {
        Produto produto = mapper.toProduto(produtoDtoRequest);
        Produto produtoAtualizado = atualizarProdutoUseCase.executar(id, produto);
        return mapper.toProdutoDtoResponse(produtoAtualizado);
    }

    @GetMapping
    @Override
    public List<ProdutoDtoResponse> listarTodos() {
        return listarTodosProdutosUseCase.executar().stream()
            .map(mapper::toProdutoDtoResponse)
            .toList();
    }
}
