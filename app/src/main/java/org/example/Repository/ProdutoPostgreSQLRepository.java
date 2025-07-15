package org.example.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
 
import org.example.Produto;

public class ProdutoPostgreSQLRepository implements IRepository<Produto> {

    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/ecommerce_db";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "1234"; 

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
    }

    @Override
    public Produto save(Produto entity) {
        String sql = "INSERT INTO produtos (nome, preco, categoria) VALUES (?, ?, ?) RETURNING id";
        
       try (Connection conn = getConnection();
         
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        
        stmt.setString(1, entity.getNome());
        stmt.setDouble(2, entity.getPreco());
        stmt.setString(3, entity.getCategoria());
        
        
        try (ResultSet rs = stmt.executeQuery()) { 
            if (rs.next()) { 
                entity.setId(rs.getInt("id")); 
            } else {
                
                System.err.println("Erro: INSERT no PostgreSQL não retornou o ID gerado.");
            }
        }
        
        
        System.out.println("Produto salvo no banco de dados (PostgreSQL): " + entity.getNome() + " com ID: " + entity.getId());
        return entity;

    } catch (SQLException e) {
        System.err.println("Erro ao salvar produto no PostgreSQL: " + e.getMessage());
        e.printStackTrace();
        throw new RuntimeException("Erro ao acessar o PostgreSQL para salvar produto", e);
    }
}

    @Override
    public Produto findByID(String id) {
       String sql = "SELECT id, nome, preco, categoria FROM produtos WHERE nome = ?";
        Produto produto = null;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    produto = new Produto(
                        rs.getInt("id"), 
                        rs.getString("nome"),
                        rs.getDouble("preco"),
                        rs.getString("categoria")
                    );
                    System.out.println("Produto encontrado por nome '" + id + "': " + produto.getNome() + " (DB ID: " + produto.getId() + ")");
                } else {
                    System.out.println("Produto por nome '" + id + "' não encontrado.");
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar produto por ID no banco de dados: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao acessar o banco de dados para buscar produto", e);
        }
        return produto;
    }

    @Override
    public void deleteByID(String id) {
        String sql = "DELETE FROM produtos WHERE nome = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, id);
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                System.out.println("Produto com nome '" + id + "' deletado com sucesso do banco de dados.");
            } else {
                System.out.println("Produto com nome '" + id + "' não encontrado para deletar.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao deletar produto por ID no banco de dados: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao acessar o banco de dados para deletar produto", e);
        }
    }

    @Override
    public List<Produto> findAll() {
        String sql = "SELECT id, nome, preco, categoria FROM produtos";
        List<Produto> produtosList = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Produto produto = new Produto(
                    rs.getInt("id"), 
                    rs.getString("nome"),
                    rs.getDouble("preco"),
                    rs.getString("categoria")
                );
                produtosList.add(produto);
            }
            System.out.println("Total de produtos listados do banco de dados: " + produtosList.size());

        } catch (SQLException e) {
            System.err.println("Erro ao listar todos os produtos do banco de dados: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao acessar o banco de dados para listar produtos", e);
        }
        return produtosList;
    }

   @Override
public void update(Produto entity) {
    
    String sql = "UPDATE produtos SET nome = ?, preco = ?, categoria = ? WHERE id = ?";

    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, entity.getNome());
        stmt.setDouble(2, entity.getPreco());
        stmt.setString(3, entity.getCategoria());
        stmt.setInt(4, entity.getId()); 

        int affectedRows = stmt.executeUpdate();

        if (affectedRows > 0) {
            System.out.println("Produto com ID " + entity.getId() + " atualizado com sucesso no banco de dados.");
        } else {
            System.out.println("Produto com ID " + entity.getId() + " não encontrado para atualização.");
        }

    } catch (SQLException e) {
        System.err.println("Erro ao atualizar produto no banco de dados: " + e.getMessage());
        e.printStackTrace();
        throw new RuntimeException("Erro ao acessar o banco de dados para atualizar produto", e);
    }
}
}