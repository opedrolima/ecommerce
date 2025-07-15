package org.example.UseCase;

import org.example.Produto;
import org.example.Repository.IRepository;

public class SaveProdutoUseCase {
    private final IRepository<Produto> produtoRepository;


    public SaveProdutoUseCase(IRepository<Produto> produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Produto execute(String nome, double preco, String categoria) {
        System.out.println("Salvando... \n Produto - Nome: " + nome);
       
        Produto novoProduto = new Produto(nome, preco, categoria);
        return produtoRepository.save(novoProduto);
    }
}