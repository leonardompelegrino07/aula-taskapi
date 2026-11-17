package br.edu.ifsp.web.listener;

import br.edu.ifsp.web.dao.ConnectionFactory;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
@WebListener
public class IniciaAplicacaoListener implements ServletContextListener{
	@Override
    public void contextInitialized(ServletContextEvent e) {
        ServletContext ctx = e.getServletContext();
        
        // 1. Instancia a ConnectionFactory e publica no ServletContext
        ConnectionFactory factory = new ConnectionFactory();
        ctx.setAttribute("connectionFactory", factory);
        
        // 2. Log de inicialização no console
        System.out.println("[LISTENERS] Aplicação TaskAPI iniciada e ConnectionFactory registrada no ServletContext.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent e) {
        // 3. Log de encerramento no console
        System.out.println("[LISTENERS] Aplicação TaskAPI encerrada.");
    }
}


