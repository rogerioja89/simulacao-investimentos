package com.github.rogerioja89.simulacaoinvestimentos.repository;

import com.github.rogerioja89.simulacaoinvestimentos.entity.Simulacao;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class SimulacaoRepository implements PanacheRepository<Simulacao> {

    // Buscar todas as simulações de um cliente específico
    public List<Simulacao> findByClienteId(Long clienteId) {
        return list("clienteId", clienteId);
    }
}