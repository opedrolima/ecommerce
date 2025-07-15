package org.example.UseCase;

import java.util.List;

import org.example.Produto;
import org.example.Repository.IRepository;

public class FindAllProdutoUseCase {
    private final IRepository<Produto> produtoRepository;

    public FindAllProdutoUseCase(IRepository<Produto> produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<Produto> execute() {
        System.out.println("Executando caso de uso: Listar todos os Produtos.");
        return produtoRepository.findAll();
    }
}