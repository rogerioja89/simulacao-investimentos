package com.github.rogerioja89.simulacaoinvestimentos.repository;

import com.github.rogerioja89.simulacaoinvestimentos.entity.Produto;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProdutoRepository implements PanacheRepository<Produto> {
    // Aqui você pode adicionar consultas específicas se precisar
    // Exemplo: buscar produto por tipo
    public Produto findByTipo(String tipoProduto) {
        return find("tipoProduto", tipoProduto).firstResult();
    }

    public Produto findByTipoIgnoreCase(String tipoProduto) {
        return find("lower(tipoProduto) = ?1", tipoProduto.toLowerCase()).firstResult();
    }
}