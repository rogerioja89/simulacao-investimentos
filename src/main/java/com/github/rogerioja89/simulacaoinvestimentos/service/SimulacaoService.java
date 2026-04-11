package com.github.rogerioja89.simulacaoinvestimentos.service;

import com.github.rogerioja89.simulacaoinvestimentos.entity.Produto;
import com.github.rogerioja89.simulacaoinvestimentos.entity.Simulacao;
import com.github.rogerioja89.simulacaoinvestimentos.repository.ProdutoRepository;
import com.github.rogerioja89.simulacaoinvestimentos.repository.SimulacaoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@ApplicationScoped
public class SimulacaoService {

    @Inject
    ProdutoRepository produtoRepository;

    @Inject
    SimulacaoRepository simulacaoRepository;

    @Inject
    CalculadoraValorFinal calculadoraValorFinal;

    @Inject
    SimulacaoValidator simulacaoValidator;

    /**
     * Cria uma simulação de investimento.
     */
    @Transactional
    public ResultadoCriacaoSimulacao criarSimulacao(Long clienteId, Double valor, Integer prazoMeses, String tipoProduto) {
        SimulacaoValidator.ResultadoValidacao validacaoEntrada =
                simulacaoValidator.validarEntrada(clienteId, valor, prazoMeses, tipoProduto);

        if (!validacaoEntrada.sucesso()) {
            SimulacaoValidator.ErroValidacao erro = validacaoEntrada.erro();
            return ResultadoCriacaoSimulacao.falha(erro.codigo(), erro.mensagem(), erro.detalhes());
        }

        String tipoProdutoNormalizado = validacaoEntrada.valorNormalizado();

        Produto produto = produtoRepository.findByTipo(tipoProdutoNormalizado);

        SimulacaoValidator.ResultadoValidacao validacaoProduto =
                simulacaoValidator.validarProduto(produto, valor, prazoMeses);

        if (!validacaoProduto.sucesso()) {
            SimulacaoValidator.ErroValidacao erro = validacaoProduto.erro();
            return ResultadoCriacaoSimulacao.falha(erro.codigo(), erro.mensagem(), erro.detalhes());
        }

        double valorFinal = calculadoraValorFinal.calcular(
                valor,
                produto.getRentabilidadeAnual(),
                prazoMeses
        );

        Simulacao simulacao = new Simulacao(
                clienteId,
                produto.getNome(),
                produto.getTipoProduto(),
                valor,
                prazoMeses,
                produto.getRentabilidadeAnual(),
                valorFinal,
                Instant.now().truncatedTo(ChronoUnit.SECONDS)
        );

        simulacaoRepository.persist(simulacao);

        return ResultadoCriacaoSimulacao.sucesso(new SimulacaoProcessada(produto, simulacao));
    }

    public record SimulacaoProcessada(Produto produtoValidado, Simulacao simulacao) {
    }

    public record ErroValidacaoSimulacao(String codigo, String mensagem, String detalhes) {
    }

    public record ResultadoCriacaoSimulacao(SimulacaoProcessada simulacaoProcessada, ErroValidacaoSimulacao erro) {
        public static ResultadoCriacaoSimulacao sucesso(SimulacaoProcessada simulacaoProcessada) {
            return new ResultadoCriacaoSimulacao(simulacaoProcessada, null);
        }

        public static ResultadoCriacaoSimulacao falha(String codigo, String mensagem, String detalhes) {
            return new ResultadoCriacaoSimulacao(null, new ErroValidacaoSimulacao(codigo, mensagem, detalhes));
        }

        public boolean comSucesso() {
            return simulacaoProcessada != null;
        }
    }
}