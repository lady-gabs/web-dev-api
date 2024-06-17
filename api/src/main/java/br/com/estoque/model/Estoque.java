package br.com.estoque.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Estoque {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;
    private int qtd;

    @OneToMany(mappedBy = "estoque", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Produto> produtos = new ArrayList<>();

    public Estoque() {
    }

    public Estoque(String nome) {
        this.nome = nome;
    }

    public Estoque(String nome, int qtd, List<Produto> produtos) {
        this.nome = nome;
        this.qtd = qtd;
        this.produtos = produtos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQtd() {
        return qtd;
    }

    public void setQtd(int qtd) {
        this.qtd = qtd;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public void ajustarQuantidade(int quantidade) {
        this.qtd += quantidade;
    }

    public void deletaQuantidade(int quantidade) {
        this.qtd -= quantidade;
    }
}