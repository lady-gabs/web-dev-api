package br.com.estoque.service;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.estoque.model.Estoque;
import br.com.estoque.model.Produto;
import br.com.estoque.repository.EstoqueRepository;
import br.com.estoque.repository.ProdutoRepository;

@Service
public class EstoqueService {

    @Autowired
    private EstoqueRepository estoqueRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public Estoque cadastrarEstoque(Estoque estoque) {
        return estoqueRepository.save(estoque);
    }

    public void deleteEstoqueById(Long id) {
        Estoque estoque = estoqueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado"));

        estoqueRepository.delete(estoque);
    }

     public void ajustarQuantidade(Long estoqueId, Long produtoId, int quantidade) {
        Optional<Estoque> estoqueOpt = estoqueRepository.findById(estoqueId);
        if (estoqueOpt.isPresent()) {
            Estoque estoque = estoqueOpt.get();
            Optional<Produto> produtoOpt = produtoRepository.findById(produtoId);
            if (produtoOpt.isPresent() && produtoOpt.get().getEstoque().getId().equals(estoqueId)) {
                estoque.ajustarQuantidade(quantidade);
                estoqueRepository.save(estoque);
            } else {
                throw new RuntimeException("Produto não encontrado ou não pertence ao estoque especificado");
            }
        } else {
            throw new RuntimeException("Estoque não encontrado");
        }
    }

    public List<Estoque> listarEstoques() {
        Iterable<Estoque> estoquesIterable = estoqueRepository.findAll();
        List<Estoque> estoques = new ArrayList<>();
        estoquesIterable.forEach(estoques::add);
        return estoques;
    }
}


    
