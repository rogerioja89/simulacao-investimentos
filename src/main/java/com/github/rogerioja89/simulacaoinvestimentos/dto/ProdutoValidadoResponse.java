package com.github.rogerioja89.simulacaoinvestimentos.dto;

public class ProdutoValidadoResponse {
    public Long id;
    public String nome;
    public String tipo;
    public Double rentabilidade;
    public String risco;

    public ProdutoValidadoResponse(Long id, String nome, String tipo, Double rentabilidade, String risco) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.rentabilidade = rentabilidade;
        this.risco = risco;
    }
}