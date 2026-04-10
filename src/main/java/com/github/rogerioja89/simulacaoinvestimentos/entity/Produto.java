package com.github.rogerioja89.simulacaoinvestimentos.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
@Table(name = "produtos")
public class Produto extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String tipoProduto;

    @Column(nullable = false)
    private Double rentabilidadeAnual;

    @Column(nullable = false)
    private String risco;

    @Column(nullable = false)
    private Integer prazoMinMeses;

    @Column(nullable = false)
    private Integer prazoMaxMeses;

    @Column(nullable = false)
    private Double valorMin;

    @Column(nullable = false)
    private Double valorMax;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipoProduto() {
        return tipoProduto;
    }

    public void setTipoProduto(String tipoProduto) {
        this.tipoProduto = tipoProduto;
    }

    public Double getRentabilidadeAnual() {
        return rentabilidadeAnual;
    }

    public void setRentabilidadeAnual(Double rentabilidadeAnual) {
        this.rentabilidadeAnual = rentabilidadeAnual;
    }

    public String getRisco() {
        return risco;
    }

    public void setRisco(String risco) {
        this.risco = risco;
    }

    public Integer getPrazoMinMeses() {
        return prazoMinMeses;
    }

    public void setPrazoMinMeses(Integer prazoMinMeses) {
        this.prazoMinMeses = prazoMinMeses;
    }

    public Integer getPrazoMaxMeses() {
        return prazoMaxMeses;
    }

    public void setPrazoMaxMeses(Integer prazoMaxMeses) {
        this.prazoMaxMeses = prazoMaxMeses;
    }

    public Double getValorMin() {
        return valorMin;
    }

    public void setValorMin(Double valorMin) {
        this.valorMin = valorMin;
    }

    public Double getValorMax() {
        return valorMax;
    }

    public void setValorMax(Double valorMax) {
        this.valorMax = valorMax;
    }
}