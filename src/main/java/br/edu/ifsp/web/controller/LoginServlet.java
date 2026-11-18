package br.edu.ifsp.web.controller;


import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Usuario;
import service.AuthService;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private AuthService authService = new AuthService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        // Autentica utilizando a camada de serviço e validação de hash
        Usuario usuario = authService.autenticar(email, senha);

        if (usuario != null) {
            // Cria a sessão HTTP para o usuário autenticado
            HttpSession session = request.getSession();
            session.setAttribute("usuarioLogado", usuario);
            
            // Redireciona para a área protegida de tarefas
            response.sendRedirect(request.getContextPath() + "/app/tarefas");
        } else {
            // Se falhar, redireciona de volta para o login com erro
            response.sendRedirect(request.getContextPath() + "/login.jsp?erro=1");
        }
    }
}
