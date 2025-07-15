package org.example.Repository; 

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
 
import org.example.Cliente;
public class ClientePostgreSQLRepository implements IRepository<Cliente> {
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/ecommerce_db";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "1234"; 


    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
    }

   

   @Override
public Cliente save(Cliente entity) {
    String sql = "INSERT INTO clientes (nome, email, telefone) VALUES (?, ?, ?) RETURNING id";
    
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) { 
        
        stmt.setString(1, entity.getNome());
        stmt.setString(2, entity.getEmail());
        stmt.setString(3, entity.getTelefone());
        
        try (ResultSet rs = stmt.executeQuery()) { 
            if (rs.next()) {
                entity.setId(rs.getInt("id")); 
            } else {
                System.err.println("Erro: INSERT no PostgreSQL não retornou o ID gerado para cliente.");
            }
        }
        
        System.out.println("Cliente salvo no banco de dados (PostgreSQL): " + entity.getNome() + " com ID: " + entity.getId());
        return entity;

    } catch (SQLException e) {
        System.err.println("Erro ao salvar cliente no PostgreSQL: " + e.getMessage());
        e.printStackTrace();
        throw new RuntimeException("Erro ao acessar o PostgreSQL para salvar cliente", e);
    }
}

    @Override
    public Cliente findByID(String id) {
       
        String sql = "SELECT id, nome, email, telefone FROM clientes WHERE nome = ? OR email = ?"; 
        Cliente cliente = null;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id); 
            stmt.setString(2, id); 

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) { 
                    cliente = new Cliente(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("telefone")
                    );
                    System.out.println("Cliente encontrado por ID/Nome/Email '" + id + "': " + cliente.getNome() + " (DB ID: " + cliente.getId() + ")");
                } else {
                    System.out.println("Cliente por ID/Nome/Email '" + id + "' não encontrado.");
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar cliente por ID no banco de dados: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao acessar o banco de dados para buscar cliente", e);
        }
        return cliente;
    }

    @Override
    public void deleteByID(String id) {
        String sql = "DELETE FROM clientes WHERE nome = ? OR email = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id); 
            stmt.setString(2, id);

            int affectedRows = stmt.executeUpdate(); 
            if (affectedRows > 0) {
                System.out.println("Cliente com ID/Nome/Email '" + id + "' deletado com sucesso do banco de dados.");
            } else {
                System.out.println("Cliente com ID/Nome/Email '" + id + "' não encontrado para deletar.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao deletar cliente por ID no banco de dados: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao acessar o banco de dados para deletar cliente", e);
        }
    }

    @Override
    public List<Cliente> findAll() {
        String sql = "SELECT id, nome, email, telefone FROM clientes";
        List<Cliente> clientesList = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) { 
                Cliente cliente = new Cliente(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("email"),
                    rs.getString("telefone")
                );
                clientesList.add(cliente);
            }
            System.out.println("Total de clientes listados do banco de dados: " + clientesList.size());

        } catch (SQLException e) {
            System.err.println("Erro ao listar todos os clientes do banco de dados: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao acessar o banco de dados para listar clientes", e);
        }
        return clientesList;
    }

    @Override
    public void update(Cliente entity) {
    
        String sql = "UPDATE clientes SET nome = ?, email = ?, telefone = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, entity.getNome());
            stmt.setString(2, entity.getEmail());
            stmt.setString(3, entity.getTelefone());
            stmt.setInt(4, entity.getId()); 

            int affectedRows = stmt.executeUpdate(); 

            if (affectedRows > 0) {
                System.out.println("Cliente com ID " + entity.getId() + " atualizado com sucesso no banco de dados.");
            } else {
                System.out.println("Cliente com ID " + entity.getId() + " não encontrado para atualização.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar cliente no banco de dados: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao acessar o banco de dados para atualizar cliente", e);
        }
    }
}