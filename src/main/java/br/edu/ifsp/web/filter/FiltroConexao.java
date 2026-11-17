package br.edu.ifsp.web.filter;
import br.edu.ifsp.web.dao.ConnectionFactory;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebFilter(urlPatterns = {"/tarefas", "/tarefas/*"}) // Intercepta todas as requisições da API
public class FiltroConexao implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        
        ServletContext ctx = req.getServletContext();
        ConnectionFactory factory = (ConnectionFactory) ctx.getAttribute("connectionFactory");
        
        Connection conexao = null;
        
        try {
            // IDA: Abre a conexão e insere no atributo da requisição
            conexao = factory.getConnection();
            req.setAttribute("conexao", conexao);
            
            // Repassa a requisição para o próximo filtro ou Servlet/Command
            chain.doFilter(req, resp);
            
        } finally {
            // VOLTA: Sempre fecha a conexão no bloco finally para evitar leaks
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar a conexão JDBC: " + e.getMessage());
                }
            }
        }
    }
}
