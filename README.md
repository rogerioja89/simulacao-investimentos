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

## Referencias

- Quarkus: <https://quarkus.io/>
- Maven Tooling (Quarkus): <https://quarkus.io/guides/maven-tooling>
