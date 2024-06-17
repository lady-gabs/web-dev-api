package br.com.estoque.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.estoque.model.Usuario;
import br.com.estoque.repository.UsuarioRepository;

// implements UserDetailsService - Pertencente ao Spring Security
@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    UsuarioRepository usuarioRepository;

    // Aqui nós vamos fazer a consulta dos usuários no BD para passar para o 
    // Spring Security
    public List<Usuario> getAllUsers() {
        Iterable<Usuario> usuariosIterable = usuarioRepository.findAll();
        List<Usuario> usuarios = new ArrayList<>();
        usuariosIterable.forEach(usuarios::add);
        return usuarios;
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByLogin(username);
    }   

    public boolean deleteUserById(Long userId) {
        if (usuarioRepository.existsById(userId)) {
            usuarioRepository.deleteById(userId);
            return true;
        } else {
            return false;
        }
    }

}