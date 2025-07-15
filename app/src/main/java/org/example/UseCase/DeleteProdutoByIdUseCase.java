package org.example.UseCase;

import org.example.Produto;
import org.example.Repository.IRepository;

public class DeleteProdutoByIdUseCase {
    private final IRepository<Produto> produtoRepository;

    public DeleteProdutoByIdUseCase(IRepository<Produto> produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public void execute(String id) {
        System.out.println("Executando caso de uso: Deletar Produto por ID = " + id);
        produtoRepository.deleteByID(id);
    }
}