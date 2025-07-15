package org.example;

import org.example.Repository.IProdutoInMemoryRepository;
import org.example.UseCase.SaveProdutoUseCase;

public class Ecomerce {
    public static void main(String[] args) {
              final var Repository = new IProdutoInMemoryRepository();
            final var useCase = new SaveProdutoUseCase(Repository);
            final var resultado = useCase.execute("Produto 1", 100.0, "Categoria A");
            System.out.println(resultado);  
        
        
    }
}
