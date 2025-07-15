package org.example;

import java.util.List;
 
import org.example.Repository.ClientePostgreSQLRepository;
import org.example.Repository.IRepository;
import org.example.Repository.ProdutoPostgreSQLRepository;
import org.example.UseCase.DeleteClienteByIdUseCase;
import org.example.UseCase.DeleteProdutoByIdUseCase;
import org.example.UseCase.FindAllClientesUseCase;
import org.example.UseCase.FindAllProdutoUseCase;
import org.example.UseCase.FindClienteByIdUseCase;
import org.example.UseCase.FindProdutoByIdUseCase;
import org.example.UseCase.SaveClienteUseCase;
import org.example.UseCase.SaveProdutoUseCase;
import org.example.UseCase.UpdateClienteUseCase;
import org.example.UseCase.UpdateProdutoUseCase;



public class App {
    public static void main(String[] args) {
        System.out.println("Iniciando a aplicacao...");

        System.out.println("\n--- Testando Operacoses de Produto ---");
        IRepository<Produto> produtoRepository = new ProdutoPostgreSQLRepository();

        SaveProdutoUseCase saveProduto = new SaveProdutoUseCase(produtoRepository);
        FindProdutoByIdUseCase findProduto = new FindProdutoByIdUseCase(produtoRepository);
        FindAllProdutoUseCase findAllProdutos = new FindAllProdutoUseCase(produtoRepository);
        UpdateProdutoUseCase updateProduto = new UpdateProdutoUseCase(produtoRepository);
        DeleteProdutoByIdUseCase deleteProduto = new DeleteProdutoByIdUseCase(produtoRepository);

        
        Produto p1 = saveProduto.execute("Laptop Gamer", 7500.00, "Eletrônicos");
        Produto p2 = saveProduto.execute("Mouse Sem Fio", 150.00, "Acessorios");
        Produto p3 = saveProduto.execute("Teclado Mecânico", 400.00, "Acessorios");

        
        Produto foundP1 = findProduto.execute("Laptop Gamer");
        if (foundP1 != null) {
            System.out.println("Produto encontrado: " + foundP1);
        }

    
  
        System.out.println("\n--- Atualizando Produto ---");

        if (p1 != null) { 
   
        updateProduto.execute(p1.getId(), "Laptop Gamer Elite", 8500.00, "Hardware Premium");

        Produto updatedP1 = findProduto.execute("Laptop Gamer Elite"); 
        if (updatedP1 != null) {
        System.out.println("Produto atualizado: " + updatedP1);
        } else {
            System.out.println("Produto 'Laptop Gamer Elite' não encontrado após atualização (verificar lógica do find/update).");
        }
        } else {
        System.out.println("Não foi possível atualizar, o produto 'p1' era nulo ou não foi salvo nesta execução.");
        }

        
        List<Produto> produtos = findAllProdutos.execute();
        System.out.println("\nTodos os Produtos:");
        produtos.forEach(System.out::println);

        
        deleteProduto.execute("Mouse Sem Fio");
        System.out.println("\nVerificando produtos apos deletar 'Mouse Sem Fio':");
        findAllProdutos.execute().forEach(System.out::println);


        
        System.out.println("\n--- Testando Operacoes de Cliente ---");
        IRepository<Cliente> clienteRepository = new ClientePostgreSQLRepository();

        SaveClienteUseCase saveCliente = new SaveClienteUseCase(clienteRepository);
        FindClienteByIdUseCase findCliente = new FindClienteByIdUseCase(clienteRepository);
        FindAllClientesUseCase findAllClientes = new FindAllClientesUseCase(clienteRepository);
        UpdateClienteUseCase updateCliente = new UpdateClienteUseCase(clienteRepository);
        DeleteClienteByIdUseCase deleteCliente = new DeleteClienteByIdUseCase(clienteRepository);

  
        Cliente c1 = saveCliente.execute("Joao Silva", "joao.silva@email.com", "11987654321");
        Cliente c2 = saveCliente.execute("Maria Souza", "maria.souza@email.com", "21998765432");
        Cliente c3 = saveCliente.execute("Pedro Santos", "pedro.santos@email.com", "31987651234");

        Cliente foundC1 = findCliente.execute("joao.silva@email.com"); 
        if (foundC1 != null) {
            System.out.println("Cliente encontrado: " + foundC1);
        }

 
        if (foundC1 != null) {
            updateCliente.execute(foundC1.getId(), "Joao Silva Neto", "joao.silva.neto@email.com", "11987650000");
            Cliente updatedC1 = findCliente.execute("joao.silva.neto@email.com"); 
            System.out.println("Cliente atualizado: " + updatedC1);
        }

        
        List<Cliente> clientes = findAllClientes.execute();
        System.out.println("\nTodos os Clientes:");
        clientes.forEach(System.out::println);

        
        deleteCliente.execute("Maria Souza"); 
        System.out.println("\n Verificando clientes apos deletar 'Maria Souza':");
        findAllClientes.execute().forEach(System.out::println);

        System.out.println("\nAplicacao finalizada.");
    }
}