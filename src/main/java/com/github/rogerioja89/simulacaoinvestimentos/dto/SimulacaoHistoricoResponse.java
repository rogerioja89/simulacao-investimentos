package com.github.rogerioja89.simulacaoinvestimentos.dto;

import java.math.BigDecimal;
import java.time.Instant;

public class SimulacaoHistoricoResponse {
    public Long id;
    public Long clienteId;
    public String produtoNome;
    public String tipoProduto;
    public BigDecimal valorInvestido;
    public Integer prazoMeses;
    public Double rentabilidadeAplicada;
    public BigDecimal valorFinal;
    public Instant dataSimulacao;

    public SimulacaoHistoricoResponse(Long id, Long clienteId, String produtoNome, String tipoProduto,
                                      BigDecimal valorInvestido, Integer prazoMeses, Double rentabilidadeAplicada,
                                      BigDecimal valorFinal, Instant dataSimulacao) {
        this.id = id;
        this.clienteId = clienteId;
        this.produtoNome = produtoNome;
        this.tipoProduto = tipoProduto;
        this.valorInvestido = valorInvestido;
        this.prazoMeses = prazoMeses;
        this.rentabilidadeAplicada = rentabilidadeAplicada;
        this.valorFinal = valorFinal;
        this.dataSimulacao = dataSimulacao;
    }
}
