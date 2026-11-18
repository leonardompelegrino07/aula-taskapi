package br.edu.ifsp.web.dao;

import br.edu.ifsp.web.model.Tarefa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TarefaDAO {
    
    private Connection conexao;

    // Construtor que recebe a conexão vinda da requisição/filtro
    public TarefaDAO(Connection conexao) {
        this.conexao = conexao;
    }

    // 1. Listar apenas as tarefas do usuário logado usando a conexão injetada
    public List<Tarefa> listarPorUsuario(int usuarioId) {
        List<Tarefa> tarefas = new ArrayList<>();
        String sql = "SELECT id, titulo, descricao, concluida, usuario_id FROM tarefas WHERE usuario_id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Tarefa t = new Tarefa();
                    t.setId(rs.getInt("id"));
                    t.setTitulo(rs.getString("titulo"));
                    t.setDescricao(rs.getString("descricao"));
                    t.setConcluida(rs.getBoolean("concluida"));
                    t.setUsuarioId(rs.getInt("usuario_id"));
                    tarefas.add(t);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tarefas;
    }

    // 2. Inserir nova tarefa vinculada ao usuário logado
    public void inserir(Tarefa tarefa) {
        String sql = "INSERT INTO tarefas (titulo, descricao, concluida, usuario_id) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, tarefa.getTitulo());
            stmt.setString(2, tarefa.getDescricao());
            stmt.setBoolean(3, tarefa.isConcluida());
            stmt.setInt(4, tarefa.getUsuarioId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}