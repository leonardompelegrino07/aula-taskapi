package br.edu.ifsp.web.service;

import dao.UsuarioDAO;
import model.Usuario;
import java.util.Base64;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AuthService {
    
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    // Método para autenticar o usuário validando a senha informada com o hash salvo no banco
    public Usuario autenticar(String email, String senhaDigitada) {
        Usuario usuario = usuarioDAO.buscarPorEmail(email);
        
        if (usuario != null && verificarSenha(senhaDigitada, usuario.getCredencial())) {
            return usuario; // Login bem-sucedido
        }
        return null; // Credenciais inválidas
    }

    private boolean verificarSenha(String senhaDigitada, String credencialArmazenada) {
        try {
            // O formato padrão da credencial armazena o "salt:hash"
            String[] partes = credencialArmazenada.split(":");
            if (partes.length != 2) return false;
            
            String salt = partes.get(0); // na prática pegamos a string do salt
            String hashSalvo = partes[1];
            
            // Recalcula o hash usando SHA-256 com o salt
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(Base64.getDecoder().decode(partes[0]));
            byte[] encodedHash = digest.digest(senhaDigitada.getBytes());
            String hashCalculado = Base64.getEncoder().encodeToString(encodedHash);
            
            return hashCalculado.equals(hashSalvo);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
    }
}