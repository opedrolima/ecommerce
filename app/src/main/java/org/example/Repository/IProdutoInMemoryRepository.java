package org.example.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.example.Produto;

public class IProdutoInMemoryRepository implements IRepository<Produto> {
    private final ArrayList<Produto> produtos = new ArrayList<>();

    @Override
    public Produto save(Produto produto) {
        produtos.add(produto);
        return produto;
    }

    @Override
    public Produto findByID(String id) {
        for (Produto produto : produtos) {
            if (Objects.equals(produto.getNome(), id)) {
                System.out.println("Produto encontrado por ID '" + id + "': " + produto.getNome());
                return produto;
            }
        }
        System.out.println("Produto com ID '" + id + "' não encontrado.");
        return null; 
    }
    @Override
    public void deleteByID(String id) {
    boolean remover = produtos.removeIf(produto -> Objects.equals(produto.getNome(), id));
        if (remover) {
            System.out.println("Produto com ID '" + id + "' deletado com sucesso.");
        } else {
            System.out.println("Produto com ID '" + id + "' não encontrado para deletar.");
        }
    }

    @Override
    public List<Produto> findAll() {
          System.out.println("Listando todos os produtos...");    
        return new ArrayList<>(produtos);
    }
    

    @Override
    public void update(Produto produto) {
         boolean updated = false;
        for (int i = 0; i < produtos.size(); i++) {
            if (Objects.equals(produtos.get(i).getNome(), produto.getNome())) {
                produtos.set(i, produto); 
                updated = true;
                System.out.println("Produto '" + produto.getNome() + "' atualizado com sucesso.");
                break; 
            }
        }
        if (!updated) {
            System.out.println("Produto '" + produto.getNome() + "' não encontrado para atualização.");

        }
    }


}
