package br.com.estoque.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Usuario implements UserDetails{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    
    private Long id;
    private String login;
    private String senha;
    private String nome;
    private UserRole role;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Telefone> telefones = new ArrayList<Telefone>();

    public Usuario(){
        
    }

    public Usuario(String login, String senha, String nome, UserRole role, List<Telefone> telefones){
        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.role = role;
        this.setTelefones(telefones);
    }

    public Usuario(String login, String senha, UserRole role){
        this.login = login;
        this.senha = senha;
        this.role = role;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getLogin() {
        return this.login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getSenha() {
    return this.senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // O Spring Security já tem algumas roles implementadas. Repare que 
        // nesse método o retorno é uma colection, então cada usuário pode ter
        // vários papéis (roles). Por exemplo, um ADMIN é ao mesmo tempo USER
        // norma. Um CHEFE é ao mesmo tempo ADMIN e USER normal, ...
        if (this.role == UserRole.ADMIN){
            return List.of(
                new SimpleGrantedAuthority("ROLE_ADMIN"),   // Admin
                new SimpleGrantedAuthority("ROLE_USER")     // é ao mesmo tempo user normal
                );
        }else{
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public List<Telefone> getTelefones() {
        return telefones;
    }
    
    public void setTelefones(List<Telefone> telefones) {
        this.telefones.clear();
        if (telefones != null) {
            for (Telefone telefone : telefones) {
                telefone.setUsuario(this);
            }
            this.telefones.addAll(telefones);
        }
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.login;
    }
    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }
    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

}
