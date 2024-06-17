package br.com.estoque.model;

import java.util.List;

import jakarta.persistence.Entity;

@Entity
public class Mercado extends Usuario {
    private String cnpj;
    
    public Mercado() {
    }

    public Mercado(String login, String nome, String senha, UserRole role, String cnpj, List<Telefone> telefones) {
        super(login, nome, senha, role, telefones);
        setLogin(login);
        setNome(nome);
        setSenha(senha); 
        setRole(role);
        setTelefones(telefones);
        this.cnpj = cnpj;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}

