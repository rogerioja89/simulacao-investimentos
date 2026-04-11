package com.github.rogerioja89.simulacaoinvestimentos.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculadoraValorFinalTest {

    @Test
    void deveCalcularValorFinalCorretamente() {
        CalculadoraValorFinal calculadora = new CalculadoraValorFinal();

        double resultado = calculadora.calcular(2000.0, 0.12, 12);

        assertEquals(2253.65, resultado, 0.5);
    }

    @Test
    void deveCalcularValorFinalParaOutroPrazo() {
        CalculadoraValorFinal calculadora = new CalculadoraValorFinal();

        double resultado = calculadora.calcular(10000.0, 0.10, 6);

        assertEquals(10510.32, resultado, 0.5);
    }
}
