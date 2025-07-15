package org.example.UseCase;

import org.example.Produto;
import org.example.Repository.IRepository;

public class FindProdutoByIdUseCase {
    private final IRepository<Produto> produtoRepository;

    public FindProdutoByIdUseCase(IRepository<Produto> produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Produto execute(String id) {
        System.out.println("Buscando Produto por ID: " + id);
        return produtoRepository.findByID(id);
    }
}