package com.github.rogerioja89.simulacaoinvestimentos.service;

import com.github.rogerioja89.simulacaoinvestimentos.entity.Produto;
import com.github.rogerioja89.simulacaoinvestimentos.repository.ProdutoRepository;
import com.github.rogerioja89.simulacaoinvestimentos.repository.SimulacaoRepository;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class SimulacaoServiceTest {

    @Inject
    SimulacaoService simulacaoService;

    @Inject
    ProdutoRepository produtoRepository;

    @Test
    void testCriarSimulacaoValida() {
        // garante que existe um produto CDB
        Produto produto = produtoRepository.find("tipoProduto", "CDB").firstResult();
        assertNotNull(produto);

        SimulacaoService.ResultadoCriacaoSimulacao resultado = simulacaoService.criarSimulacao(1L, 2000.0, 12, "CDB");

        assertTrue(resultado.comSucesso());
        var simulacao = resultado.simulacaoProcessada().simulacao();
        assertEquals("CDB", simulacao.tipoProduto);
        assertEquals(2253.650469149836, simulacao.valorFinal, 0.000001);
    }

    @Test
    void testCriarSimulacaoInvalida() {
        SimulacaoService.ResultadoCriacaoSimulacao resultado = simulacaoService.criarSimulacao(1L, 10.0, 2, "CDB");
        assertFalse(resultado.comSucesso());
        assertEquals("PRODUTO_NAO_ELEGIVEL", resultado.erro().codigo());
    }
}