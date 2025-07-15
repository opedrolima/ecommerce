package org.example.UseCase; 

import org.example.Cliente;
import org.example.Repository.IRepository;

public class FindClienteByIdUseCase {
    private final IRepository<Cliente> clienteRepository;

    public FindClienteByIdUseCase(IRepository<Cliente> clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente execute(String id) { 
        System.out.println("Buscando Cliente por ID/Nome/Email = " + id);
        return clienteRepository.findByID(id);
    }
}
