package com.github.rogerioja89.simulacaoinvestimentos.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "simulacoes")
public class Simulacao extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    public Long clienteId;

    @Column(nullable = false)
    public String produtoNome;

    @Column(nullable = false)
    public String tipoProduto;

    @Column(nullable = false)
    public Double valorInvestido;

    @Column(nullable = false)
    public Integer prazoMeses;

    @Column(nullable = false)
    public Double rentabilidadeAplicada;

    @Column(nullable = false)
    public Double valorFinal;

    @Column(nullable = false)
    public LocalDateTime dataSimulacao;

    // Construtor vazio exigido pelo JPA
    public Simulacao() {}

    // Construtor para facilitar a criação de objetos
    public Simulacao(Long clienteId, String produtoNome, String tipoProduto,
                     Double valorInvestido, Integer prazoMeses,
                     Double rentabilidadeAplicada, Double valorFinal,
                     LocalDateTime dataSimulacao) {
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