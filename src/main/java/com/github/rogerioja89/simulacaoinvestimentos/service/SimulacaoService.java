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

    /**
     * Cria uma simulação de investimento.
     */
    @Transactional
    public ResultadoCriacaoSimulacao criarSimulacao(Long clienteId, Double valor, Integer prazoMeses, String tipoProduto) {
        // Validações básicas
        if (clienteId == null || valor == null || prazoMeses == null || tipoProduto == null) {
            return ResultadoCriacaoSimulacao.falha(
                    "CAMPOS_OBRIGATORIOS_AUSENTES",
                    "Payload incompleto para criação da simulação.",
                    "Informe clienteId, valor, prazoMeses e tipoProduto."
            );
        }
        String tipoProdutoNormalizado = tipoProduto.trim();
        if (tipoProdutoNormalizado.isEmpty()) {
            return ResultadoCriacaoSimulacao.falha(
                    "TIPO_PRODUTO_INVALIDO",
                    "Tipo de produto inválido.",
                    "O campo tipoProduto não pode ser vazio."
            );
        }
        if (valor <= 0 || prazoMeses <= 0) {
            return ResultadoCriacaoSimulacao.falha(
                    "VALOR_OU_PRAZO_INVALIDO",
                    "Valor investido ou prazo inválido.",
                    "O valor deve ser maior que zero e o prazoMeses deve ser positivo."
            );
        }

        // Busca produto compatível
        Produto produto = produtoRepository.findByTipoIgnoreCase(tipoProdutoNormalizado);
        if (produto == null) {
            return ResultadoCriacaoSimulacao.falha(
                    "PRODUTO_NAO_ENCONTRADO",
                    "Não existe produto para o tipo informado.",
                    "Nenhum produto cadastrado para tipoProduto=" + tipoProdutoNormalizado + "."
            );
        }

        // Valida se o produto é elegível
        boolean elegivel = valor >= produto.valorMin && valor <= produto.valorMax
                && prazoMeses >= produto.prazoMinMeses && prazoMeses <= produto.prazoMaxMeses;

        if (!elegivel) {
            return ResultadoCriacaoSimulacao.falha(
                    "PRODUTO_NAO_ELEGIVEL",
                    "Os dados informados não atendem às regras do produto.",
                    "Faixas aceitas: valor entre " + produto.valorMin + " e " + produto.valorMax
                            + ", prazo entre " + produto.prazoMinMeses + " e " + produto.prazoMaxMeses + " meses."
            );
        }

        // Cálculo em juros compostos com capitalização mensal.
        double valorFinal = valor * Math.pow(1 + produto.rentabilidadeAnual / 12.0, prazoMeses);

        // Cria a simulação
        Simulacao simulacao = new Simulacao(
                clienteId,
                produto.nome,
                produto.tipoProduto,
                valor,
                prazoMeses,
                produto.rentabilidadeAnual,
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