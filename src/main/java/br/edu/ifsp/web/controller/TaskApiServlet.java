package br.edu.ifsp.web.controller;

import br.edu.ifsp.web.command.*;
import br.edu.ifsp.web.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// Mapeado para /app/tarefas para ser interceptado pelo Filtro de Autenticação
@WebServlet(urlPatterns = {"/app/tarefas", "/app/tarefas/*"})
public class TaskApiServlet extends HttpServlet {

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
        // Validação adicional de segurança para garantir que há um usuário na sessão
        HttpSession session = req.getSession(false);
        Usuario usuario = (session != null) ? (Usuario) session.getAttribute("usuarioLogado") : null;

        if (usuario == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

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