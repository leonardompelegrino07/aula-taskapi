package br.edu.ifsp.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = {"/tarefas", "/tarefas/*"})
public class FiltroTempoDeExecucao implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        
        // IDA: Registra o tempo inicial
        long inicio = System.currentTimeMillis();
        
        // Continua a execução
        chain.doFilter(req, resp);
        
        // VOLTA: Calcula o tempo total e pega a URI/Método
        long tempoTotal = System.currentTimeMillis() - inicio;
        
        HttpServletRequest httpReq = (HttpServletRequest) req;
        String metodo = httpReq.getMethod();
        String uri = httpReq.getRequestURI();
        
        System.out.println("[TEMPO EXECUÇÃO] " + metodo + " " + uri + " levou (ms): " + tempoTotal);
    }
}