package br.com.estoque.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.estoque.model.Produto;
import br.com.estoque.model.ProdutoDTO;
import br.com.estoque.service.ProdutoService;

@RestController
@RequestMapping("/produto")
@CrossOrigin
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<Produto> cadastrarProduto(@RequestBody ProdutoDTO produtoDTO) {
        Produto novoProduto = produtoService.cadastrarProduto(produtoDTO);
        return ResponseEntity.ok(novoProduto);
    }

    @PutMapping("/{id}/atualiza-produto")
    public ResponseEntity<Produto> atualizarProduto(@PathVariable Long id, @RequestBody ProdutoDTO produto) {
        Produto produtoAtualizado = produtoService.atualizarProduto(id, produto);
        return ResponseEntity.ok(produtoAtualizado);
    }

    @DeleteMapping("/{nome}")
    public ResponseEntity<Void> removerProdutoPorNome(@PathVariable String nome,@RequestBody ProdutoDTO produtoDTO) {
        produtoService.removerProdutoPorNome(nome, produtoDTO);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listarProdutos() {
        List<Produto> produtos = produtoService.listarProdutos();
        return ResponseEntity.ok(produtos);
    }
}
