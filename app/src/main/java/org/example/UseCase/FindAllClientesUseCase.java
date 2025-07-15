package org.example.UseCase; 
import java.util.List;

import org.example.Cliente;
import org.example.Repository.IRepository;

public class FindAllClientesUseCase {
    private final IRepository<Cliente> clienteRepository;

    public FindAllClientesUseCase(IRepository<Cliente> clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> execute() {
        System.out.println("Listando todos os Clientes.");
        return clienteRepository.findAll();
    }
}