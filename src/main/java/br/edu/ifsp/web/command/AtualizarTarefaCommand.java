package br.edu.ifsp.web.command;

import br.edu.ifsp.web.dao.TarefaDAO;
import br.edu.ifsp.web.model.Tarefa;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;

public class AtualizarTarefaCommand implements Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Connection conexao = (Connection) req.getAttribute("conexao");
        TarefaDAO dao = new TarefaDAO(conexao);
        
        String pathInfo = req.getPathInfo(); // Obtém "/{id}"
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID não informado");
            return;
        }
        
        int id = Integer.parseInt(pathInfo.substring(1));
        Gson gson = new Gson();
        Tarefa tarefa = gson.fromJson(req.getReader(), Tarefa.class);
        
        boolean atualizado = dao.atualizar(id, tarefa);
        if (atualizado) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("{\"mensagem\": \"Tarefa atualizada com sucesso\"}");
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Tarefa não encontrada");
        }
    }
}