package br.edu.ifsp.web.command;

import br.edu.ifsp.web.dao.TarefaDAO;
import br.edu.ifsp.web.model.Tarefa;
import br.edu.ifsp.web.model.Usuario;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Connection;
import java.util.List;

public class ListarTarefasCommand implements Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        // Recupera o usuário logado na sessão
        HttpSession session = req.getSession(false);
        Usuario usuario = (session != null) ? (Usuario) session.getAttribute("usuarioLogado") : null;
        
        if (usuario == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Usuário não autenticado");
            return;
        }

        Connection conexao = (Connection) req.getAttribute("conexao");
        TarefaDAO dao = new TarefaDAO(conexao);
        
        // Lista apenas as tarefas pertencentes a este usuário
        List<Tarefa> tarefas = dao.listarPorUsuario(usuario.getId());
        
        String json = new Gson().toJson(tarefas);
        resp.setContentType("application/json; charset=UTF-8");
        resp.getWriter().write(json);
    }
}