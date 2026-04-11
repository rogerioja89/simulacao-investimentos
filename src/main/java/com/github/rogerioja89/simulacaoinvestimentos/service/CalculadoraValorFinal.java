package com.github.rogerioja89.simulacaoinvestimentos.service;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CalculadoraValorFinal {

    public double calcular(double valor, double rentabilidadeAnual, int prazoMeses) {
        return valor * Math.pow(1 + rentabilidadeAnual / 12.0, prazoMeses);
    }
}
