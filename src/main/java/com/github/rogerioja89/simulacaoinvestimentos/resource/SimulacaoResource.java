package com.github.rogerioja89.simulacaoinvestimentos.resource;

import com.github.rogerioja89.simulacaoinvestimentos.entity.Simulacao;
import com.github.rogerioja89.simulacaoinvestimentos.service.SimulacaoService;
import com.github.rogerioja89.simulacaoinvestimentos.repository.SimulacaoRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.List;

@Path("/simulacoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SimulacaoResource {

    @Inject
    SimulacaoService simulacaoService;

    @Inject
    SimulacaoRepository simulacaoRepository;

    /**
     * Endpoint para criar uma simulação
     */
    @POST
    public Response criarSimulacao(SimulacaoRequest request) {
        if (request == null) {
            return Response.status(422)
                    .entity(new ErroResponse(
                            "PAYLOAD_AUSENTE",
                            "Payload ausente para criação da simulação.",
                            "Envie um JSON no corpo com clienteId, valor, prazoMeses e tipoProduto."
                    ))
                    .build();
        }

        SimulacaoService.ResultadoCriacaoSimulacao resultado = simulacaoService.criarSimulacao(
                request.clienteId,
                request.valor,
                request.prazoMeses,
                request.tipoProduto
        );

        if (!resultado.comSucesso()) {
            SimulacaoService.ErroValidacaoSimulacao erro = resultado.erro();
            return Response.status(422)
                    .entity(new ErroResponse(erro.codigo(), erro.mensagem(), erro.detalhes()))
                    .build();
        }

        SimulacaoService.SimulacaoProcessada processada = resultado.simulacaoProcessada();
        Simulacao simulacao = processada.simulacao();
        var produto = processada.produtoValidado();

        SimulacaoResponse response = new SimulacaoResponse(
                new ProdutoValidadoResponse(
                        produto.id,
                        produto.nome,
                        produto.tipoProduto,
                        produto.rentabilidadeAnual,
                        produto.risco
                ),
                new ResultadoSimulacaoResponse(
                        BigDecimal.valueOf(simulacao.valorFinal).setScale(2, RoundingMode.HALF_UP),
                        simulacao.prazoMeses
                ),
                simulacao.dataSimulacao
        );

        return Response.ok(response).build();
    }

    /**
     * Endpoint para buscar histórico de simulações de um cliente
     */
    @GET
    public Response listarSimulacoes(@QueryParam("clienteId") Long clienteId) {
        if (clienteId == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("O parâmetro clienteId é obrigatório.")
                    .build();
        }

        List<Simulacao> simulacoes = simulacaoRepository.findByClienteId(clienteId);
        List<SimulacaoHistoricoResponse> response = simulacoes.stream()
                .map(simulacao -> new SimulacaoHistoricoResponse(
                        simulacao.id,
                        simulacao.clienteId,
                        simulacao.produtoNome,
                        simulacao.tipoProduto,
                        BigDecimal.valueOf(simulacao.valorInvestido).setScale(2, RoundingMode.HALF_UP),
                        simulacao.prazoMeses,
                        simulacao.rentabilidadeAplicada,
                        BigDecimal.valueOf(simulacao.valorFinal).setScale(2, RoundingMode.HALF_UP),
                        simulacao.dataSimulacao
                ))
                .toList();

        return Response.ok(response).build();
    }

    /**
     * DTO para requisição de simulação
     */
    public static class SimulacaoRequest {
        public Long clienteId;
        public Double valor;
        public Integer prazoMeses;
        public String tipoProduto;
    }

    public static class SimulacaoResponse {
        public ProdutoValidadoResponse produtoValidado;
        public ResultadoSimulacaoResponse resultadoSimulacao;
        public Instant dataSimulacao;

        public SimulacaoResponse(ProdutoValidadoResponse produtoValidado,
                                 ResultadoSimulacaoResponse resultadoSimulacao,
                                 Instant dataSimulacao) {
            this.produtoValidado = produtoValidado;
            this.resultadoSimulacao = resultadoSimulacao;
            this.dataSimulacao = dataSimulacao;
        }
    }

    public static class ProdutoValidadoResponse {
        public Long id;
        public String nome;
        public String tipo;
        public Double rentabilidade;
        public String risco;

        public ProdutoValidadoResponse(Long id, String nome, String tipo, Double rentabilidade, String risco) {
            this.id = id;
            this.nome = nome;
            this.tipo = tipo;
            this.rentabilidade = rentabilidade;
            this.risco = risco;
        }
    }

    public static class ResultadoSimulacaoResponse {
        public BigDecimal valorFinal;
        public Integer prazoMeses;

        public ResultadoSimulacaoResponse(BigDecimal valorFinal, Integer prazoMeses) {
            this.valorFinal = valorFinal;
            this.prazoMeses = prazoMeses;
        }
    }

    public static class ErroResponse {
        public String codigo;
        public String mensagem;
        public String detalhes;

        public ErroResponse(String codigo, String mensagem, String detalhes) {
            this.codigo = codigo;
            this.mensagem = mensagem;
            this.detalhes = detalhes;
        }
    }

    public static class SimulacaoHistoricoResponse {
        public Long id;
        public Long clienteId;
        public String produtoNome;
        public String tipoProduto;
        public BigDecimal valorInvestido;
        public Integer prazoMeses;
        public Double rentabilidadeAplicada;
        public BigDecimal valorFinal;
        public Instant dataSimulacao;

        public SimulacaoHistoricoResponse(Long id, Long clienteId, String produtoNome, String tipoProduto,
                                          BigDecimal valorInvestido, Integer prazoMeses, Double rentabilidadeAplicada,
                                          BigDecimal valorFinal, Instant dataSimulacao) {
            this.id = id;
            this.clienteId = clienteId;
            this.produtoNome = produtoNome;
            this.tipoProduto = tipoProduto;
            this.valorInvestido = valorInvestido;
            this.prazoMeses = prazoMeses;
            this.rentabilidadeAplicada = rentabilidadeAplicada;
            this.valorFinal = valorFinal;
            this.dataSimulacao = dataSimulacao;
        }
    }
}