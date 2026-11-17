CREATE DATABASE IF NOT EXISTS taskdb;
USE taskdb;

CREATE TABLE IF NOT EXISTS tarefas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(120) NOT NULL,
    descricao VARCHAR(500),
    concluida BOOLEAN DEFAULT FALSE
);

INSERT INTO tarefas (titulo, descricao, concluida) VALUES
('Estudar filtros', 'Ler o material da aula', FALSE),
('Configurar o banco', 'MySQL + driver', TRUE);