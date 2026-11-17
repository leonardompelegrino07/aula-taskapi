package br.edu.ifsp.web.dao;

import br.edu.ifsp.web.model.Tarefa;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TarefaDAO {
	private Connection conexao;

    public TarefaDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public List<Tarefa> listar() throws SQLException {
        List<Tarefa> lista = new ArrayList<>();
        String sql = "SELECT id, titulo, descricao, concluida FROM tarefas";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Tarefa t = new Tarefa(
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("descricao"),
                    rs.getBoolean("concluida")
                );
                lista.add(t);
            }
        }
        return lista;
    }

    public void inserir(Tarefa t) throws SQLException {
        String sql = "INSERT INTO tarefas (titulo, descricao, concluida) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, t.getTitulo());
            stmt.setString(2, t.getDescricao());
            stmt.setBoolean(3, t.isConcluida() != null && t.isConcluida());
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    t.setId(rs.getInt(1));
                }
            }
        }
    }

    public boolean atualizar(int id, Tarefa t) throws SQLException {
        String sql = "UPDATE tarefas SET titulo = ?, descricao = ?, concluida = ? WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, t.getTitulo());
            stmt.setString(2, t.getDescricao());
            stmt.setBoolean(3, t.isConcluida() != null && t.isConcluida());
            stmt.setInt(4, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deletar(int id) throws SQLException {
        String sql = "DELETE FROM tarefas WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
}

