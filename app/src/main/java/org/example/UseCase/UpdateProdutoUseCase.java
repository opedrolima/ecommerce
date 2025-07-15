package org.example.UseCase; // Ajuste o pacote se necessário

import org.example.Produto;
import org.example.Repository.IRepository;

public class UpdateProdutoUseCase {
    private final IRepository<Produto> produtoRepository;

    public UpdateProdutoUseCase(IRepository<Produto> produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public void execute(int id, String novoNome, double novoPreco, String novaCategoria) {
        System.out.println("Executando caso de uso: Atualizar Produto ID = " + id);
        Produto produtoAtualizado = new Produto(id, novoNome, novoPreco, novaCategoria);

        produtoRepository.update(produtoAtualizado);
    }
}