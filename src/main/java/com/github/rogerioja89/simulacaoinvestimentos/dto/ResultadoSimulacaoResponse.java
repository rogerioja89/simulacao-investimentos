package com.github.rogerioja89.simulacaoinvestimentos.dto;

import java.math.BigDecimal;

public class ResultadoSimulacaoResponse {
    public BigDecimal valorFinal;
    public Integer prazoMeses;

    public ResultadoSimulacaoResponse(BigDecimal valorFinal, Integer prazoMeses) {
        this.valorFinal = valorFinal;
        this.prazoMeses = prazoMeses;
    }
}
