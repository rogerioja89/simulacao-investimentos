# simulacao-investimentos

API de simulacao de investimentos com Quarkus.

## Pre-requisitos

- Java 21
- Windows PowerShell

## Executar a aplicacao (modo dev)

Na raiz do projeto, rode:

```powershell
Set-Location "D:\Documentos\CAIXA\Quarkus\simulacao-investimentos"
.\mvnw.cmd quarkus:dev
```

Endpoints uteis durante o desenvolvimento:

- API: `http://localhost:8080/simulacoes`
- Dev UI: `http://localhost:8080/q/dev/`

Para encerrar, use `Ctrl + C` no terminal.

## Executar testes

Todos os testes:

```powershell
Set-Location "D:\Documentos\CAIXA\Quarkus\simulacao-investimentos"
.\mvnw.cmd test
```

Executar uma classe de teste especifica:

```powershell
Set-Location "D:\Documentos\CAIXA\Quarkus\simulacao-investimentos"
.\mvnw.cmd -Dtest=SimulacaoServiceTest test
```

Executar multiplas classes de teste:

```powershell
Set-Location "D:\Documentos\CAIXA\Quarkus\simulacao-investimentos"
.\mvnw.cmd -Dtest=SimulacaoServiceTest,SimulacaoResourceTest test
```

## Gerar pacote da aplicacao

Build padrao:

```powershell
Set-Location "D:\Documentos\CAIXA\Quarkus\simulacao-investimentos"
.\mvnw.cmd clean package
```

Executar o jar gerado:

```powershell
java -jar target\quarkus-app\quarkus-run.jar
```

## Exemplos de requisicoes (POST e GET)

### POST `/simulacoes`

Body (JSON):

```json
{
  "clienteId": 123,
  "valor": 10000.00,
  "prazoMeses": 12,
  "tipoProduto": "CDB"
}
```

Resposta esperada (`200 OK`):

```json
{
  "produtoValidado": {
	"id": 1,
	"nome": "CDB Caixa 2026",
	"tipo": "CDB",
	"rentabilidade": 0.12,
	"risco": "Baixo"
  },
  "resultadoSimulacao": {
	"valorFinal": 11268.25,
	"prazoMeses": 12
  },
  "dataSimulacao": "2026-04-08T12:00:00Z"
}
```

Exemplo no PowerShell:

```powershell
$body = @{
  clienteId = 123
  valor = 10000.00
  prazoMeses = 12
  tipoProduto = "CDB"
} | ConvertTo-Json

Invoke-RestMethod -Method Post -Uri "http://localhost:8080/simulacoes" -ContentType "application/json" -Body $body
```

### GET `/simulacoes?clienteId=123`

Resposta esperada (`200 OK`):

```json
[
  {
	"id": 1,
	"clienteId": 123,
	"produtoNome": "CDB Caixa 2026",
	"tipoProduto": "CDB",
	"valorInvestido": 10000.00,
	"prazoMeses": 12,
	"rentabilidadeAplicada": 0.12,
	"valorFinal": 11268.25,
	"dataSimulacao": "2026-04-08T12:00:00Z"
  }
]
```

Exemplo no PowerShell:

```powershell
Invoke-RestMethod -Method Get -Uri "http://localhost:8080/simulacoes?clienteId=123"
```

Se o `clienteId` nao for informado no GET, a API retorna `400 Bad Request`.

## Referencias

- Quarkus: <https://quarkus.io/>
- Maven Tooling (Quarkus): <https://quarkus.io/guides/maven-tooling>
