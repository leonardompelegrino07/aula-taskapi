package br.edu.ifsp.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/tarefas", "/tarefas/*"})
public class FiltroHeaderJson implements Filter { // <-- ADICIONAR "implements Filter"

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpReq = (HttpServletRequest) req;
        HttpServletResponse httpResp = (HttpServletResponse) resp;
        String metodo = httpReq.getMethod();

        // Se for POST ou PUT, valida se veio em JSON
        if ("POST".equalsIgnoreCase(metodo) || "PUT".equalsIgnoreCase(metodo)) {
            String contentType = httpReq.getContentType();
            if (contentType == null || !contentType.contains("application/json")) {
                httpResp.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE); // HTTP 415
                httpResp.setContentType("application/json; charset=UTF-8");
                httpResp.getWriter().write("{\"erro\": \"É necessário enviar o header Content-Type: application/json\"}");
                return; // Interrompe a requisição
            }
        }

        chain.doFilter(req, resp);
    }
}