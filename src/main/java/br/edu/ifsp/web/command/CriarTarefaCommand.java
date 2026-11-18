package br.edu.ifsp.web.command;

import br.edu.ifsp.web.dao.TarefaDAO;
import br.edu.ifsp.web.model.Tarefa;
import br.edu.ifsp.web.model.Usuario;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Connection;

public class CriarTarefaCommand implements Command {
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
        
        Gson gson = new Gson();
        Tarefa novaTarefa = gson.fromJson(req.getReader(), Tarefa.class);
        
        // Associa a tarefa ao ID do usuário logado na sessão
        novaTarefa.setUsuarioId(usuario.getId());
        
        dao.inserir(novaTarefa);
        
        resp.setStatus(HttpServletResponse.SC_CREATED); // HTTP 201
        resp.setContentType("application/json; charset=UTF-8");
        resp.getWriter().write(gson.toJson(novaTarefa));
    }
}