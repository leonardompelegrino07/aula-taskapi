# TaskAPI - Tarefa Prática 03 (Sessões e Service Layer)

Repositório da aplicação refatorada com arquitetura em camadas, isolamento multi-usuário por sessão HTTP, autenticação segura com hash/salt e camada de serviços compartilhada.

## 🛠️ Tecnologias e Versões
* **Java:** 17+
* **Apache Tomcat:** 10.x / 11.x (Jakarta EE)
* **Banco de Dados:** MySQL (Driver JDBC 8.x)

---

## 👥 Credenciais de Teste (Banco de Dados)
O script de migração (`migracao-03.sql`) cadastra os seguintes usuários de teste:
1. **Usuário A:** `aluno.a@ifsp.edu.br` | Senha: `senha123`
2. **Usuário B:** `aluno.b@ifsp.edu.br` | Senha: `senha123`

---

## 🚀 Como Executar o Importador de Linha de Comando
O importador executa diretamente a `Service Layer` via terminal:
```bash
java -cp "bin:WEB-INF/lib/*" br.edu.ifsp.web.cli.ImportadorConclusoes ids.txt aluno.a@ifsp.edu.br senha123
