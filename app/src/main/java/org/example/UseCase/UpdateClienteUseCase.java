package org.example.UseCase;

import org.example.Cliente;
import org.example.Repository.IRepository;

public class UpdateClienteUseCase {
    private final IRepository<Cliente> clienteRepository;

    public UpdateClienteUseCase(IRepository<Cliente> clienteRepository) {
        this.clienteRepository = clienteRepository;
    }
    public void execute(int id, String novoNome, String novoEmail, String novoTelefone) {
        System.out.println("Atualizando Cliente de ID: " + id);
        Cliente clienteAtualizado = new Cliente(id, novoNome, novoEmail, novoTelefone);
        clienteRepository.update(clienteAtualizado);
    }
}