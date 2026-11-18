package br.edu.ifsp.web.model;

public class Usuario {
	private int id;
    private String nome;
    private String email;
    private String credencial; // Armazena o hash da senha

    public Usuario() {}

    public Usuario(int id, String nome, String email, String credencial) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.credencial = credencial;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCredencial() { return credencial; }
    public void setCredencial(String credencial) { this.credencial = credencial; }
}


