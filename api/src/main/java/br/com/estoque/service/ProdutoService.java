package br.com.estoque.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.estoque.model.Estoque;
import br.com.estoque.model.Produto;
import br.com.estoque.model.ProdutoDTO;
import br.com.estoque.repository.EstoqueRepository;
import br.com.estoque.repository.ProdutoRepository;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private EstoqueRepository estoqueRepository;

    public Produto cadastrarProduto(ProdutoDTO produtoDTO) {
        Optional<Estoque> estoqueOpt = estoqueRepository.findById(produtoDTO.getEstoqueId());
        if (estoqueOpt.isPresent()) {
            Estoque estoque = estoqueOpt.get();
            Produto produto = new Produto(produtoDTO.getNome(), produtoDTO.getPreco(), produtoDTO.getQuantidade());
            produto.setEstoque(estoque);
            estoque.ajustarQuantidade(produtoDTO.getQuantidade());
            estoque.getProdutos().add(produto);
            estoqueRepository.save(estoque); // Persist both Produto and Estoque with the new relationship
            return produto;
        } else {
            throw new RuntimeException("Estoque não encontrado");
        }
    }

    public Produto atualizarProduto(Long id, ProdutoDTO produto) {
        Optional<Produto> produtoOpt = produtoRepository.findById(id);
        Optional<Estoque> estoqueOpt = estoqueRepository.findById(produto.getEstoqueId());
        if (produtoOpt.isPresent()) {
            Estoque estoque = estoqueOpt.get();
            Produto produtoExistente = produtoOpt.get();
            estoque.deletaQuantidade(produtoExistente.getQuantidade());
            produtoExistente.setNome(produto.getNome());
            produtoExistente.setPreco(produto.getPreco());
            produtoExistente.setQuantidade(produto.getQuantidade());
            estoque.ajustarQuantidade(produtoExistente.getQuantidade());
            return produtoRepository.save(produtoExistente);
        } else {
            throw new RuntimeException("Produto não encontrado");
        }
    }

    public void removerProdutoPorNome(String nome, ProdutoDTO produtoDTO) {
        Produto produto = produtoRepository.findByNome(nome);
        Optional<Estoque> estoqueOpt = estoqueRepository.findById(produtoDTO.getEstoqueId());
        if (produto != null) {
            Estoque estoque = estoqueOpt.get();
            System.out.println(produto.getQuantidade());
            estoque.deletaQuantidade(produto.getQuantidade());
            produtoRepository.delete(produto);
        }
    }

    public List<Produto> listarProdutos() {
        Iterable<Produto> produtosIterable = produtoRepository.findAll();
        List<Produto> produtos = new ArrayList<>();
        produtosIterable.forEach(produtos::add);
        return produtos;
    }
}



