package org.example.UseCase; 

import org.example.Cliente; 
import org.example.Repository.IRepository; 
public class SaveClienteUseCase {
    private final IRepository<Cliente> clienteRepository;

    public SaveClienteUseCase(IRepository<Cliente> clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente execute(String nome, String email, String telefone) {
        System.out.println("Cliente Salvo - Nome: " + nome);
        Cliente novoCliente = new Cliente(nome, email, telefone);
        return clienteRepository.save(novoCliente);
    }
}
