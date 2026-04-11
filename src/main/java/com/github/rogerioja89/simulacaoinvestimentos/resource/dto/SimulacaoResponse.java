package com.github.rogerioja89.simulacaoinvestimentos.resource.dto;

import java.time.Instant;

public class SimulacaoResponse {
    public ProdutoValidadoResponse produtoValidado;
    public ResultadoSimulacaoResponse resultadoSimulacao;
    public Instant dataSimulacao;

    public SimulacaoResponse(ProdutoValidadoResponse produtoValidado,
                             ResultadoSimulacaoResponse resultadoSimulacao,
                             Instant dataSimulacao) {
        this.produtoValidado = produtoValidado;
        this.resultadoSimulacao = resultadoSimulacao;
        this.dataSimulacao = dataSimulacao;
    }
}
