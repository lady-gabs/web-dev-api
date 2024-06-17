package br.com.estoque.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.estoque.model.Estoque;
import br.com.estoque.service.EstoqueService;

@RestController
@RequestMapping("/estoque")
@CrossOrigin
public class EstoqueController {

    @Autowired
    private EstoqueService estoqueService;

    @PostMapping("/novo-estoque")
    public ResponseEntity<Estoque> cadastrarEstoque(@RequestBody Estoque estoque) {
        Estoque novoEstoque = estoqueService.cadastrarEstoque(estoque);
        return ResponseEntity.ok(novoEstoque);
    }

    @DeleteMapping("/remover-estoque/{id}")
    public ResponseEntity<String> deleteEstoque(@PathVariable Long id) {
        try {
            estoqueService.deleteEstoqueById(id);
            return ResponseEntity.ok("Estoque deletado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao deletar estoque: " + e.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<Estoque>> getAllEstoques() {
        List<Estoque> estoques = estoqueService.listarEstoques();
        return ResponseEntity.ok(estoques);
    }
}