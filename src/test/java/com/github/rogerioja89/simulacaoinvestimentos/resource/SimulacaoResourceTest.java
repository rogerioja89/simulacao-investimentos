package com.github.rogerioja89.simulacaoinvestimentos.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class SimulacaoResourceTest {

    @Test
    void testCriarSimulacaoEndpoint() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"clienteId\":1,\"valor\":10000.00,\"prazoMeses\":12,\"tipoProduto\":\"CDB\"}")
                .when()
                .post("/simulacoes")
                .then()
                .statusCode(200)
                .body("produtoValidado.tipo", equalTo("CDB"))
                .body("produtoValidado.nome", equalTo("CDB Caixa 2026"))
                .body("resultadoSimulacao.valorFinal", equalTo(11268.25f))
                .body("resultadoSimulacao.prazoMeses", equalTo(12))
                .body("dataSimulacao", notNullValue());
    }

    @Test
    void testCriarSimulacaoInvalidaEndpoint() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"clienteId\":1,\"valor\":10,\"prazoMeses\":2,\"tipoProduto\":\"CDB\"}")
                .when()
                .post("/simulacoes")
                .then()
                .statusCode(422)
                .body("codigo", equalTo("PRODUTO_NAO_ELEGIVEL"))
                .body("mensagem", equalTo("Os dados informados não atendem às regras do produto."))
                .body("detalhes", containsString("Faixas aceitas"));
    }

    @Test
    void testCriarSimulacaoPayloadAusenteEndpoint() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .post("/simulacoes")
                .then()
                .statusCode(422)
                .body("codigo", equalTo("PAYLOAD_AUSENTE"))
                .body("mensagem", equalTo("Payload ausente para criação da simulação."));
    }

    @Test
    void testCriarSimulacaoPayloadInvalidoEndpoint() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"clienteId\":123,\"valor\":10000,,\"prazoMeses\":12,\"tipoProduto\":\"CDB\"}")
                .when()
                .post("/simulacoes")
                .then()
                .statusCode(422)
                .body("codigo", equalTo("PAYLOAD_INVALIDO"))
                .body("mensagem", equalTo("Payload inválido para criação da simulação."));
    }

    @Test
    void testListarSimulacoesRetornaValorFinalComDuasCasas() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"clienteId\":987654,\"valor\":2000,\"prazoMeses\":12,\"tipoProduto\":\"CDB\"}")
                .when()
                .post("/simulacoes")
                .then()
                .statusCode(200);

        given()
                .queryParam("clienteId", 987654)
                .when()
                .get("/simulacoes")
                .then()
                .statusCode(200)
                .body("find { it.clienteId == 987654 }.valorInvestido", equalTo(2000.00f))
                .body("find { it.clienteId == 987654 }.valorFinal", equalTo(2253.65f));
    }
}