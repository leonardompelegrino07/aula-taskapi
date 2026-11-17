USE taskdb;

CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(120) NOT NULL,
    email VARCHAR(160) NOT NULL UNIQUE,
    credencial VARCHAR(255) NOT NULL
);

INSERT INTO usuarios (nome, email, credencial) VALUES
('Ana Silva', 'ana@ifsp.edu.br', 'MDEyMzQ1Njc4OWFiY2RlZg==:y8OCDupECIyV47w6O3pB8MNiPE63e1AkBbrjei4+JCQ='),
('Bruno Costa', 'bruno@ifsp.edu.br', 'ZmVkY2JhOTg3NjU0MzIxMA==:mRM1kFY+qCBHC6lLA4R/9os5+gEYxqLVWGeujEzGyno=');

ALTER TABLE tarefas ADD COLUMN usuario_id INT NULL;

UPDATE tarefas SET usuario_id = (SELECT id FROM usuarios WHERE email = 'ana@ifsp.edu.br') WHERE usuario_id IS NULL;

ALTER TABLE tarefas MODIFY COLUMN usuario_id INT NOT NULL;
ALTER TABLE tarefas ADD CONSTRAINT fk_tarefas_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE;

INSERT INTO tarefas (titulo, descricao, concluida, usuario_id) VALUES
('Revisar Service Layer', 'Garantir isolamento HTTP', FALSE, (SELECT id FROM usuarios WHERE email = 'ana@ifsp.edu.br')),
('Implementar CLI', 'Criar ImportadorConclusoes', FALSE, (SELECT id FROM usuarios WHERE email = 'bruno@ifsp.edu.br')),
('Testar Sessao HTTP', 'Validar troca de ID no login', TRUE, (SELECT id FROM usuarios WHERE email = 'bruno@ifsp.edu.br')),
('Escrever README', 'Documentar etapas de verificacao', FALSE, (SELECT id FROM usuarios WHERE email = 'bruno@ifsp.edu.br'));
```[cite: 49]

Pronto! Agora o arquivo `migracao-03.sql` existe fisicamente na pasta do seu projeto. 

Vá no Git Bash e rode o comando para registrar a etapa 01[cite: 16]:
```bash
git add schema.sql migracao-03.sql
git commit -m "01 - Adiciona schema.sql e migracao-03.sql"
git push origin main