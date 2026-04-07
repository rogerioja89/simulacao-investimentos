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
                .body("{\"clienteId\":1,\"valor\":2000,\"prazoMeses\":12,\"tipoProduto\":\"CDB\"}")
                .when()
                .post("/simulacoes")
                .then()
                .statusCode(200)
                .body("tipoProduto", equalTo("CDB"))
                .body("valorFinal", greaterThan(2000.0f));
    }

    @Test
    void testCriarSimulacaoInvalidaEndpoint() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"clienteId\":1,\"valor\":10,\"prazoMeses\":2,\"tipoProduto\":\"CDB\"}")
                .when()
                .post("/simulacoes")
                .then()
                .statusCode(422);
    }
}