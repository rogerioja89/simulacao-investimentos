package com.github.rogerioja89.simulacaoinvestimentos.service;

import com.github.rogerioja89.simulacaoinvestimentos.entity.Produto;
import com.github.rogerioja89.simulacaoinvestimentos.entity.Simulacao;
import com.github.rogerioja89.simulacaoinvestimentos.repository.ProdutoRepository;
import com.github.rogerioja89.simulacaoinvestimentos.repository.SimulacaoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@ApplicationScoped
public class SimulacaoService {

    @Inject
    ProdutoRepository produtoRepository;

    @Inject
    SimulacaoRepository simulacaoRepository;

    /**
     * Cria uma simulação de investimento.
     */
    @Transactional
    public Optional<Simulacao> criarSimulacao(Long clienteId, Double valor, Integer prazoMeses, String tipoProduto) {
        // Validações básicas
        if (clienteId == null || valor == null || prazoMeses == null || tipoProduto == null) {
            return Optional.empty(); // campos obrigatórios ausentes
        }
        if (valor <= 0 || prazoMeses <= 0) {
            return Optional.empty(); // valores inválidos
        }

        // Busca produto compatível
        Produto produto = produtoRepository.find("tipoProduto", tipoProduto).firstResult();
        if (produto == null) {
            return Optional.empty(); // produto não encontrado
        }

        // Valida se o produto é elegível
        boolean elegivel = valor >= produto.valorMin && valor <= produto.valorMax
                && prazoMeses >= produto.prazoMinMeses && prazoMeses <= produto.prazoMaxMeses;

        if (!elegivel) {
            return Optional.empty(); // produto não atende aos critérios
        }

        // Cálculo do valor final
        double valorFinal = valor * Math.pow(1 + produto.rentabilidadeAnual / 12, prazoMeses);

        // Cria a simulação
        Simulacao simulacao = new Simulacao(
                clienteId,
                produto.nome,
                produto.tipoProduto,
                valor,
                prazoMeses,
                produto.rentabilidadeAnual,
                valorFinal,
                LocalDateTime.now()
        );

        simulacaoRepository.persist(simulacao);

        return Optional.of(simulacao);
    }
}