<<<<<<< HEAD
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
=======
# TaskAPI - Tarefa Prática 03

Repositório da aplicação refatorada para a Tarefa Prática 03 de Desenvolvimento Web (IFSP - Campus Araraquara), contendo a implementação de sessões HTTP, isolamento multi-usuário, Service Layer e importador de linha de comando.

## Tecnologias Utilizadas
- Java 17+
- Apache Tomcat (Jakarta EE)
- MySQL / JDBC

## Credenciais de Teste
O script de migração cadastra os seguintes usuários no banco de dados:
- **Usuário A:** aluno.a@ifsp.edu.br | Senha: senha123
- **Usuário B:** aluno.b@ifsp.edu.br | Senha: senha123

## Como Executar o Importador CLI
Para executar o importador de conclusões via linha de comando (fora do Tomcat):
java -cp "bin:WEB-INF/lib/*" br.edu.ifsp.web.cli.ImportadorConclusoes ids.txt aluno.a@ifsp.edu.br senha123

## Roteiro de Verificação (Item 09)
1. GET /tarefas sem login: 401 com corpo JSON
2. POST /auth/login com senha errada: 401
3. POST /auth/login como usuário A: 200, ID de sessão renovado, sem credencial no corpo
4. GET /tarefas: 200 apenas com as tarefas de A
5. GET, PUT, PATCH, DELETE em tarefa de B: 404 em todas; dados de B intactos
6. POST /tarefas sem título: 400
7. POST /tarefas válido: 201 com ID gerado
8. PATCH na tarefa criada (duas vezes): 204 na primeira, 409 na segunda
9. GET /auth/me: 200 com usuário e sessões ativas
10. POST /auth/logout e GET /tarefas: 204 e 401
11. Importador CLI: conclui os IDs válidos de A, reporta os erros individualmente e finaliza

## Respostas do Relatório
1. **Caminhos e Ponto de Encontro:** O PATCH passa por filtros, Front Controller e Commands, acionando a Service Layer. O importador CLI chama diretamente a Service Layer. Os dois caminhos se encontram exatamente na Service Layer.
2. **Conexão e Transação:** A conexão é gerenciada via ConnectionFactory e injetada na camada de serviço. O antigo FiltroConexao foi descontinuado para evitar abertura desnecessária de conexões em rotas sem persistência ou via CLI.
3. **Parâmetro de Usuário:** A Service Layer é agnóstica ao protocolo HTTP. Se ela lesse a sessão diretamente, o importador de linha de comando não conseguiria utilizá-la.
4. **Segurança de Sessão:** O ID da sessão muda no login para evitar ataques de fixação de sessão (Session Fixation). A tarefa de outro usuário retorna 404 em vez de 403 para garantir isolamento estrito, ocultando a existência do recurso.
5. **Justificativa da Service Layer:** A camada de serviço tornou-se obrigatória nesta etapa devido à multiplicidade de clientes (API Web e importador CLI) e à necessidade de centralizar regras de transação e domínio atômicas.
>>>>>>> bc9f9fe (Adiciona README.md)
