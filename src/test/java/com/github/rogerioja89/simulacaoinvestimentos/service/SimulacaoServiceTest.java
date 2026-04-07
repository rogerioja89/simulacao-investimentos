package com.github.rogerioja89.simulacaoinvestimentos.service;

import com.github.rogerioja89.simulacaoinvestimentos.entity.Produto;
import com.github.rogerioja89.simulacaoinvestimentos.repository.ProdutoRepository;
import com.github.rogerioja89.simulacaoinvestimentos.repository.SimulacaoRepository;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.Optional;

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

        Optional<?> simulacaoOpt = simulacaoService.criarSimulacao(1L, 2000.0, 12, "CDB");

        assertTrue(simulacaoOpt.isPresent());
        var simulacao = simulacaoOpt.get();
        assertEquals("CDB", ((com.github.rogerioja89.simulacaoinvestimentos.entity.Simulacao) simulacao).tipoProduto);
    }

    @Test
    void testCriarSimulacaoInvalida() {
        Optional<?> simulacaoOpt = simulacaoService.criarSimulacao(1L, 10.0, 2, "CDB");
        assertTrue(simulacaoOpt.isEmpty());
    }
}