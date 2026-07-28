package com.example.api.controller;

import com.example.api.dto.ProdutoStatusRequest;
import com.example.api.model.Produto;
import com.example.api.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<Produto> cadastrar(
           @Valid @RequestBody Produto produto
    ) {
        Produto produtoSalvo = produtoService.cadastrar(produto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(produtoSalvo);
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listarTodos() {
        List<Produto> produtos = produtoService.listarTodos();

        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(
            @PathVariable Long id
    ) {
        Optional<Produto> produtoEncontrado =
                produtoService.buscarPorId(id);

        if (produtoEncontrado.isPresent()) {
            return ResponseEntity.ok(produtoEncontrado.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody Produto produto
    ) {
        Optional<Produto> produtoAtualizado =
                produtoService.atualizar(id, produto);

        if (produtoAtualizado.isPresent()) {
            return ResponseEntity.ok(
                    produtoAtualizado.get()
            );
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id
    ) {
        boolean produtoExcluido =
                produtoService.excluir(id);

        if (!produtoExcluido) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Produto> alterarStatus(
            @PathVariable Long id,
            @Valid @RequestBody ProdutoStatusRequest statusRequest
    ) {
        Optional<Produto> produtoAtualizado =
                produtoService.alterarStatus(
                        id,
                        statusRequest.getAtivo()
                );

        if (produtoAtualizado.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(
                produtoAtualizado.get()
        );
    }
}
