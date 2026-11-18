package br.edu.ifsp.web.model;

public class Tarefa {
	private Integer id;
    private String titulo;
    private String descricao;
    private Boolean concluida;

    public Tarefa() {}

    public Tarefa(Integer id, String titulo, String descricao, Boolean concluida) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.concluida = concluida;
    }

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Boolean isConcluida() { return concluida; }
    public void setConcluida(Boolean concluida) { this.concluida = concluida; }
    

    private int usuarioId;

   
    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }
}


