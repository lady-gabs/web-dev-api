package br.com.estoque.model;
import java.util.List;

public record RegisterDTO(String login, String nome, String password, UserRole role, String cpf, String cnpj, List<String> telefones) {
    public UserRole getRole() {
        return role;
    }
}