package br.edu.ifsp.web.command;

import br.edu.ifsp.web.dao.TarefaDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;

public class DeletarTarefaCommand implements Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Connection conexao = (Connection) req.getAttribute("conexao");
        TarefaDAO dao = new TarefaDAO(conexao);
        
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID não informado");
            return;
        }
        
        int id = Integer.parseInt(pathInfo.substring(1));
        boolean deletado = dao.deletar(id);
        
        if (deletado) {
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT); // HTTP 204
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Tarefa não encontrada");
        }
    }
}