package com.github.rogerioja89.simulacaoinvestimentos.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
@Table(name = "produtos")
public class Produto extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    public String nome;

    @Column(nullable = false)
    public String tipoProduto;

    @Column(nullable = false)
    public Double rentabilidadeAnual;

    @Column(nullable = false)
    public String risco;

    @Column(nullable = false)
    public Integer prazoMinMeses;

    @Column(nullable = false)
    public Integer prazoMaxMeses;

    @Column(nullable = false)
    public Double valorMin;

    @Column(nullable = false)
    public Double valorMax;

    // Construtor vazio exigido pelo JPA
    public Produto() {}

    // Construtor para facilitar a criação de objetos
    public Produto(String nome, String tipoProduto, Double rentabilidadeAnual, String risco,
                   Integer prazoMinMeses, Integer prazoMaxMeses,
                   Double valorMin, Double valorMax) {
        this.nome = nome;
        this.tipoProduto = tipoProduto;
        this.rentabilidadeAnual = rentabilidadeAnual;
        this.risco = risco;
        this.prazoMinMeses = prazoMinMeses;
        this.prazoMaxMeses = prazoMaxMeses;
        this.valorMin = valorMin;
        this.valorMax = valorMax;
    }
}