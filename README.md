# Orçamento AI — API Inteligente com Spring AI

Projeto desenvolvido para o desafio da DIO sobre **Spring Boot + Spring AI**.

A aplicação funciona como um assistente financeiro capaz de registrar e consultar transações. Além dos endpoints REST tradicionais, o projeto utiliza **Tool Calling** para permitir que a IA execute funções reais da aplicação.

## Funcionalidades

- Registrar receitas e despesas;
- Listar todas as transações;
- Consultar transações por categoria;
- Consultar o saldo atual;
- Consultar o total gasto em uma categoria;
- Conversar com o assistente por texto;
- Enviar um comando de voz, transcrever o áudio, executar a ação necessária e receber uma resposta em MP3;
- Validar dados antes de salvar;
- Tratar erros de validação de forma centralizada.

## Comando implementado:

A principal evolução em relação ao fluxo básico foi a ferramenta **consultar total gasto por categoria**.
Com ela, a pessoa pode fazer perguntas como:

```text
Quanto eu gastei com Alimentacao?
```

O Spring AI identifica a intenção e pode chamar a ferramenta `consultar-total-gasto-por-categoria`, que consulta as transações reais armazenadas no banco.

## Tecnologias:

- Java 17;
- Spring Boot;
- Spring Web;
- Spring Data JPA;
- Spring AI;
- OpenAI;
- H2 Database;
- Bean Validation;
- Maven;
- JUnit 5 e Mockito.

## Estrutura:

```text
src/main/java/com/luizeduardo/orcamentoai
- controller
│   - AssistenteController.java
│   - TransacaoController.java
├── domain
│   - TipoTransacao.java
│   - Transacao.java
├── dto
│   - TransacaoRequest.java
│   - TransacaoResponse.java
├── exception
│   - GlobalExceptionHandler.java
├── repository
│   - TransacaoRepository.java
├── service
│   - TransacaoService.java
├── tools
│   - FinanceiroTools.java
└── OrcamentoAiApplication.java
```

## Como executar:

É necessário ter Java 17+ e Maven instalados.

Defina sua chave da OpenAI como variável de ambiente.

### Windows PowerShell

```powershell
$env:OPENAI_API_KEY="sua-chave-aqui"
```

### Linux/macOS

```bash
export OPENAI_API_KEY="sua-chave-aqui"
```

Depois execute:

```bash
mvn spring-boot:run
```

A aplicação ficará disponível em:

```text
http://localhost:8080
```

O console do H2 pode ser acessado em:

```text
http://localhost:8080/h2-console
```

JDBC URL:

```text
jdbc:h2:mem:orcamento
```

Usuário: `sa`  
Senha: deixe em branco.

## Como testar

### Registrar uma despesa

```bash
curl -X POST http://localhost:8080/api/transacoes \
  -H "Content-Type: application/json" \
  -d '{"descricao":"Mercado","valor":150.00,"tipo":"DESPESA","categoria":"Alimentacao"}'
```

### Consultar todas as transações

```bash
curl http://localhost:8080/api/transacoes
```

### Consultar o saldo

```bash
curl http://localhost:8080/api/transacoes/saldo
```

### Consultar o total gasto por categoria

```bash
curl http://localhost:8080/api/transacoes/total-gasto/Alimentacao
```

### Conversar com a IA por texto

```bash
curl -X POST http://localhost:8080/api/assistente/texto \
  -H "Content-Type: text/plain" \
  --data "Quanto eu gastei com Alimentacao?"
```

### Usar comando de voz

Envie um arquivo de áudio em `multipart/form-data`:

```bash
curl -X POST http://localhost:8080/api/assistente/voz \
  -F "file=@comando.mp3" \
  --output resposta.mp3
```

## Testes automatizados

```bash
mvn test
```

Os testes verificam o cálculo do total gasto por categoria e a rejeição de valores inválidos.

## O que aprendi

O projeto mostrou como integrar Inteligência Artificial a uma aplicação Java sem deixar a IA responsável diretamente pela regra de negócio. O `ChatClient` interpreta o pedido da pessoa e utiliza ferramentas expostas com `@Tool`, enquanto as operações financeiras continuam sendo executadas pelo serviço da aplicação. Também foi possível praticar persistência com JPA, criação de endpoints REST, validação, tratamento de erros, testes automatizados e integração de texto e áudio com Spring AI.
