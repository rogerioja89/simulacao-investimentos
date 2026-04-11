package com.github.rogerioja89.simulacaoinvestimentos.service;

import com.github.rogerioja89.simulacaoinvestimentos.entity.Produto;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SimulacaoValidator {

    public ResultadoValidacao validarEntrada(Long clienteId, Double valor, Integer prazoMeses, String tipoProduto) {
        if (clienteId == null || valor == null || prazoMeses == null || tipoProduto == null) {
            return ResultadoValidacao.falha(
                    "CAMPOS_OBRIGATORIOS_AUSENTES",
                    "Payload incompleto para criação da simulação.",
                    "Informe clienteId, valor, prazoMeses e tipoProduto."
            );
        }

        String tipoProdutoNormalizado = tipoProduto.trim();
        if (tipoProdutoNormalizado.isEmpty()) {
            return ResultadoValidacao.falha(
                    "TIPO_PRODUTO_INVALIDO",
                    "Tipo de produto inválido.",
                    "O campo tipoProduto não pode ser vazio."
            );
        }

        if (valor <= 0 || prazoMeses <= 0) {
            return ResultadoValidacao.falha(
                    "VALOR_OU_PRAZO_INVALIDO",
                    "Valor investido ou prazo inválido.",
                    "O valor deve ser maior que zero e o prazoMeses deve ser positivo."
            );
        }

        return ResultadoValidacao.sucesso(tipoProdutoNormalizado);
    }

    public ResultadoValidacao validarProduto(Produto produto, Double valor, Integer prazoMeses) {
        if (produto == null) {
            return ResultadoValidacao.falha(
                    "PRODUTO_NAO_ENCONTRADO",
                    "Não existe produto para o tipo informado.",
                    "Nenhum produto cadastrado para o tipo solicitado."
            );
        }

        boolean elegivel = valor >= produto.getValorMin()
                && valor <= produto.getValorMax()
                && prazoMeses >= produto.getPrazoMinMeses()
                && prazoMeses <= produto.getPrazoMaxMeses();

        if (!elegivel) {
            return ResultadoValidacao.falha(
                    "PRODUTO_NAO_ELEGIVEL",
                    "Os dados informados não atendem às regras do produto.",
                    "Faixas aceitas: valor entre " + produto.getValorMin() + " e " + produto.getValorMax()
                            + ", prazo entre " + produto.getPrazoMinMeses() + " e " + produto.getPrazoMaxMeses() + " meses."
            );
        }

        return ResultadoValidacao.sucesso(tipoProdutoTextoSeguro(produto));
    }

    private String tipoProdutoTextoSeguro(Produto produto) {
        return produto.getTipoProduto() == null ? null : produto.getTipoProduto().trim();
    }

    public record ResultadoValidacao(boolean sucesso, String valorNormalizado, ErroValidacao erro) {
        public static ResultadoValidacao sucesso(String valorNormalizado) {
            return new ResultadoValidacao(true, valorNormalizado, null);
        }

        public static ResultadoValidacao falha(String codigo, String mensagem, String detalhes) {
            return new ResultadoValidacao(false, null, new ErroValidacao(codigo, mensagem, detalhes));
        }
    }

    public record ErroValidacao(String codigo, String mensagem, String detalhes) {
    }
}
