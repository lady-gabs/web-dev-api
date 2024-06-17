package br.com.estoque.model;

import java.util.List;

import jakarta.persistence.Entity;

@Entity
public class Cliente extends Usuario {
    private String cpf;
    
    public Cliente() {
    }
//muda o construtor com novos parametros
    public Cliente(String login, String nome, String senha, UserRole role, String cpf, List<Telefone> telefones) {
        super(login, nome, senha, role, telefones);
        setLogin(login);
        setNome(nome);
        setSenha(senha); 
        setRole(role); 
        setTelefones(telefones);
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
