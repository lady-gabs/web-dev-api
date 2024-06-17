package br.com.estoque.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.estoque.model.Usuario;

@Repository
public interface UsuarioRepository  extends CrudRepository<Usuario, Long>{
	
	@Query("select u from Usuario u where u.login = ?1")
	Usuario findByLogin(String login);

}
