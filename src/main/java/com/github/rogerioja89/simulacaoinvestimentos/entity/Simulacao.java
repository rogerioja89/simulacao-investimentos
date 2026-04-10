package com.github.rogerioja89.simulacaoinvestimentos.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "simulacoes")
public class Simulacao extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long clienteId;

    @Column(nullable = false)
    private String produtoNome;

    @Column(nullable = false)
    private String tipoProduto;

    @Column(nullable = false)
    private Double valorInvestido;

    @Column(nullable = false)
    private Integer prazoMeses;

    @Column(nullable = false)
    private Double rentabilidadeAplicada;

    @Column(nullable = false)
    private Double valorFinal;

    @Column(nullable = false)
    private Instant dataSimulacao;

    // Construtor vazio exigido pelo JPA
    public Simulacao() {}

    // Construtor para facilitar a criação de objetos
    public Simulacao(Long clienteId, String produtoNome, String tipoProduto,
                     Double valorInvestido, Integer prazoMeses,
                     Double rentabilidadeAplicada, Double valorFinal,
                     Instant dataSimulacao) {
        this.clienteId = clienteId;
        this.produtoNome = produtoNome;
        this.tipoProduto = tipoProduto;
        this.valorInvestido = valorInvestido;
        this.prazoMeses = prazoMeses;
        this.rentabilidadeAplicada = rentabilidadeAplicada;
        this.valorFinal = valorFinal;
        this.dataSimulacao = dataSimulacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getProdutoNome() {
        return produtoNome;
    }

    public void setProdutoNome(String produtoNome) {
        this.produtoNome = produtoNome;
    }

    public String getTipoProduto() {
        return tipoProduto;
    }

    public void setTipoProduto(String tipoProduto) {
        this.tipoProduto = tipoProduto;
    }

    public Double getValorInvestido() {
        return valorInvestido;
    }

    public void setValorInvestido(Double valorInvestido) {
        this.valorInvestido = valorInvestido;
    }

    public Integer getPrazoMeses() {
        return prazoMeses;
    }

    public void setPrazoMeses(Integer prazoMeses) {
        this.prazoMeses = prazoMeses;
    }

    public Double getRentabilidadeAplicada() {
        return rentabilidadeAplicada;
    }

    public void setRentabilidadeAplicada(Double rentabilidadeAplicada) {
        this.rentabilidadeAplicada = rentabilidadeAplicada;
    }

    public Double getValorFinal() {
        return valorFinal;
    }

    public void setValorFinal(Double valorFinal) {
        this.valorFinal = valorFinal;
    }

    public Instant getDataSimulacao() {
        return dataSimulacao;
    }

    public void setDataSimulacao(Instant dataSimulacao) {
        this.dataSimulacao = dataSimulacao;
    }
}