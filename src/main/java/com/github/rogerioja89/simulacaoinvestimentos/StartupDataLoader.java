package com.github.rogerioja89.simulacaoinvestimentos;

import com.github.rogerioja89.simulacaoinvestimentos.entity.Produto;
import com.github.rogerioja89.simulacaoinvestimentos.repository.ProdutoRepository;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class StartupDataLoader {

    @Inject
    ProdutoRepository produtoRepository;

    @Transactional
    void init(@Observes StartupEvent ev) {
        if (produtoRepository.findByTipoIgnoreCase("CDB") == null) {
            produtoRepository.persist(new Produto("CDB Caixa 2026", "CDB", 0.12, "Baixo", 6, 36, 1000.0, 50000.0));
        }
        if (produtoRepository.findByTipoIgnoreCase("LCI") == null) {
            produtoRepository.persist(new Produto("LCI Caixa 2027", "LCI", 0.10, "Médio", 12, 48, 5000.0, 100000.0));
        }
        if (produtoRepository.findByTipoIgnoreCase("LCA") == null) {
            produtoRepository.persist(new Produto("LCA Caixa 2028", "LCA", 0.14, "Alto", 24, 60, 10000.0, 200000.0));
        }
    }
}