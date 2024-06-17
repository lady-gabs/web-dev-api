package br.com.estoque.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

import br.com.estoque.model.Produto;

public interface ProdutoRepository extends CrudRepository<Produto, Long>{
    Produto findByNome(String nome);
    Optional<Produto> findById(Long id);
}
