package br.com.estoque.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.estoque.model.Usuario;
import br.com.estoque.repository.UsuarioRepository;

@Controller("IndexController")
@RequestMapping("/usuario")
public class IndexController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Manipulando parâmetros
    @GetMapping(value="/", produces="application/json")
    public ResponseEntity init (){
        return new ResponseEntity<>("Olá Usuário! ", HttpStatus.OK);
    }

    // Retornando objetos
    @GetMapping(value="/endpoint2", produces="application/json")
    public ResponseEntity init2 (){

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setLogin("user@gmail.com");
        usuario.setNome("Usuario");
        usuario.setSenha("123");

        Usuario usuario2 = new Usuario();
        usuario2.setId(2L);
        usuario2.setLogin("user2@gmail.com");
        usuario2.setNome("Usuario 2");
        usuario2.setSenha("123");        

        List<Usuario> list = new ArrayList<Usuario>();
        list.add(usuario);
        list.add(usuario2);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // Retornando registros do BD
    @GetMapping(value="/endpoint3/{id}", produces="application/json")
    public ResponseEntity<Usuario> init3 (@PathVariable(value="id") Long id){

        try{
            Optional<Usuario> usuario = usuarioRepository.findById(id);
            return new ResponseEntity<>(usuario.get(), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }        
        
    }    

    // Cadastra no usuario
    @PostMapping(value="/", produces="application/json")
    public ResponseEntity<Usuario> cadastrar (@RequestBody Usuario usuario){    
        
        // for (int pos = 0; pos < usuario.getTelefones().size(); pos++){
        //     usuario.getTelefones().get(pos).setUsuario(usuario);
        // }

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return new ResponseEntity<>(usuarioSalvo, HttpStatus.OK);
    }

}


