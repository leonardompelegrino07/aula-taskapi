package br.edu.ifsp.web.controller;

import br.edu.ifsp.web.command.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// Mapeamento duplo para evitar o erro 404 em /tarefas e /tarefas/
@WebServlet(urlPatterns = {"/tarefas", "/tarefas/*"})
public class TaskApiServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Map<String, Command> commands = new HashMap<>();

    @Override
    public void init() throws ServletException {
        commands.put("GET", new ListarTarefasCommand());
        commands.put("POST", new CriarTarefaCommand());
        commands.put("PUT", new AtualizarTarefaCommand());
        commands.put("DELETE", new DeletarTarefaCommand());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String metodo = req.getMethod();
        Command command = commands.get(metodo);

        if (command != null) {
            try {
                command.execute(req, resp);
            } catch (Exception e) {
                e.printStackTrace();
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro interno no servidor");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Método HTTP não suportado");
        }
    }
}