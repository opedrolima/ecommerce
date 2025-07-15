package org.example.UseCase;

import org.example.Cliente;
import org.example.Repository.IRepository;

public class DeleteClienteByIdUseCase {
    private final IRepository<Cliente> clienteRepository;

    public DeleteClienteByIdUseCase(IRepository<Cliente> clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public void execute(String id) { 
        System.out.println("Deletando Cliente por ID/Nome/Email = " + id);
        clienteRepository.deleteByID(id);
    }
}