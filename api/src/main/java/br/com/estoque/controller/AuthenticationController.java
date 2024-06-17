package br.com.estoque.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.estoque.model.AuthenticationDTO;
import br.com.estoque.model.Cliente;
import br.com.estoque.model.Mercado;
import br.com.estoque.model.LoginResponseDTO;
import br.com.estoque.model.RegisterDTO;
import br.com.estoque.model.Telefone;
import br.com.estoque.model.UserRole;
import br.com.estoque.model.Usuario;
import br.com.estoque.repository.UsuarioRepository;
import br.com.estoque.security.TokenService;
import br.com.estoque.service.AuthorizationService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
@CrossOrigin
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private AuthorizationService authorizationService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
    
        // É uma boa prática armazenarmos as senhas do usuário como HASH no banco de dados. 
        // Dessa maneira, caso haja um vazamento do BD, as senhas estarão criptografadas
        // e não poderão ser diretamente acessadas. 
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        System.out.println(usernamePassword);
        try{
            var auth = this.authenticationManager.authenticate(usernamePassword);
            System.out.println(auth);
            var token = tokenService.generateToken((Usuario)auth.getPrincipal());
            System.out.println(token);

            Usuario user = usuarioRepository.findByLogin(data.login());
            System.out.println("Role: " + user.getRole());
            var role = user.getRole();

            LoginResponseDTO responseDTO = new LoginResponseDTO(token, role);
            return ResponseEntity.ok(responseDTO);

        }catch(Exception e){
            System.out.println("Erro:  ");
            System.out.println(e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterDTO data){
        // Primeiro verifica se já não existe outro usuário cadastrado com o mesmo login
        if(this.usuarioRepository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();

        // Caso não exista, vamos encriptar a senha para salvar no BD. A senha bruta do usuário 
        // NÃO DEVE SER INSERIDA NO BD POR MEDIDAS DE SEGURANÇA.

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        System.out.println(data.login());
        System.out.println(encryptedPassword);
        System.out.println(data.role());

        List<Telefone> telefones = data.telefones().stream()
                .map(numero -> {
                    Telefone telefone = new Telefone();
                    telefone.setNumero(numero);
                    return telefone;
                })
                .collect(Collectors.toList());

        if (data.cpf() != null) {
            Cliente newCliente = new Cliente(data.login(), data.nome(), encryptedPassword, UserRole.USER, data.cpf(), telefones);
            this.usuarioRepository.save(newCliente);

            for (Telefone telefone : telefones) {
                telefone.setUsuario(newCliente);
            }
            
        } else if (data.cnpj() != null) {
            Mercado newMercado = new Mercado(data.login(), data.nome(),encryptedPassword, UserRole.ADMIN, data.cnpj(), telefones);
            this.usuarioRepository.save(newMercado);

            for (Telefone telefone : telefones) {
                telefone.setUsuario(newMercado);
            }
        }

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        boolean isDeleted = authorizationService.deleteUserById(userId);

        if (isDeleted) {
            return ResponseEntity.ok("User deleted successfully.");
        } else {
            return ResponseEntity.status(404).body("User not found.");
        }
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> getAllUsers() {
        List<Usuario> usuarios = authorizationService.getAllUsers();
        return ResponseEntity.ok(usuarios);
    }
}
