package br.edu.ifsp.web.cli;

import br.edu.ifsp.web.dao.ConnectionFactory;
import br.edu.ifsp.web.model.Usuario;
import br.edu.ifsp.web.service.AuthService;
import br.edu.ifsp.web.service.TarefaService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class ImportadorConclusoes {

    public static void main(String[] args) {
        if (args.length < 3) {
            System.err.println("Uso: java br.edu.ifsp.web.cli.ImportadorConclusoes <caminho_arquivo> <email> <senha>");
            System.exit(1);
        }

        String caminhoArquivo = args[0];
        String email = args[1];
        String senha = args[2];

        try (Connection conn = ConnectionFactory.getConnection()) {
            AuthService authService = new AuthService(conn);
            Usuario usuario = authService.autenticar(email, senha);

            if (usuario == null) {
                System.err.println("Erro: Credenciais inválidas.");
                System.exit(2);
            }

            TarefaService tarefaService = new TarefaService(conn);

            try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
                String linha;
                while ((linha = br.readLine()) != null) {
                    linha = linha.trim();
                    if (linha.isEmpty()) continue;

                    try {
                        int tarefaId = Integer.parseInt(linha);
                        tarefaService.concluirTarefa(tarefaId, usuario);
                        System.out.println("Tarefa " + tarefaId + ": concluída");
                    } catch (NumberFormatException e) {
                        System.out.println("Linha inválida (ID não numérico): " + linha);
                    } catch (RuntimeException e) {
                        // Trata exceções de negócio (já concluída, não encontrada, etc.)
                        System.out.println("Tarefa " + linha + ": " + e.getMessage());
                    }
                }
            } catch (IOException e) {
                System.err.println("Erro ao ler o arquivo: " + e.getMessage());
                System.exit(3);
            }

        } catch (SQLException e) {
            System.err.println("Erro de conexão com o banco de dados: " + e.getMessage());
            System.exit(4);
        }
    }
}