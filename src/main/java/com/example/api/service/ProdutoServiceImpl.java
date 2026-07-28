package com.example.api.service;

import com.example.api.model.Produto;
import com.example.api.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository produtoRepository;

    @Override
    public Produto cadastrar(Produto produto) {
        return produtoRepository.save(produto);
    }

    @Override
    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    @Override
    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }

    @Override
    public Optional<Produto> atualizar(
            Long id,
            Produto produtoAtualizado
    ) {
        Optional<Produto> produtoExistenteOptional =
                produtoRepository.findById(id);

        if (produtoExistenteOptional.isEmpty()) {
            return Optional.empty();
        }

        Produto produtoExistente =
                produtoExistenteOptional.get();

        produtoExistente.setNome(
                produtoAtualizado.getNome()
        );

        produtoExistente.setDescricao(
                produtoAtualizado.getDescricao()
        );

        produtoExistente.setPreco(
                produtoAtualizado.getPreco()
        );

        produtoExistente.setQuantidadeEstoque(
                produtoAtualizado.getQuantidadeEstoque()
        );

        produtoExistente.setCategoria(
                produtoAtualizado.getCategoria()
        );

        produtoExistente.setAtivo(
                produtoAtualizado.getAtivo()
        );

        Produto produtoSalvo =
                produtoRepository.save(produtoExistente);

        return Optional.of(produtoSalvo);
    }

    @Override
    public boolean excluir(Long id) {
        if (!produtoRepository.existsById(id)) {
            return false;
        }
        produtoRepository.deleteById(id);
        return true;
    }

    @Override
    public Optional<Produto> alterarStatus(
            Long id,
            Boolean ativo
    ) {
        Optional<Produto> produtoOptional =
                produtoRepository.findById(id);

        if (produtoOptional.isEmpty()) {
            return Optional.empty();
        }

        Produto produto = produtoOptional.get();

        produto.setAtivo(ativo);

        Produto produtoSalvo =
                produtoRepository.save(produto);

        return Optional.of(produtoSalvo);
    }
}
