package com.github.rogerioja89.simulacaoinvestimentos.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rogerioja89.simulacaoinvestimentos.dto.ErroResponse;
import com.github.rogerioja89.simulacaoinvestimentos.dto.ProdutoValidadoResponse;
import com.github.rogerioja89.simulacaoinvestimentos.dto.ResultadoSimulacaoResponse;
import com.github.rogerioja89.simulacaoinvestimentos.dto.SimulacaoHistoricoResponse;
import com.github.rogerioja89.simulacaoinvestimentos.dto.SimulacaoRequest;
import com.github.rogerioja89.simulacaoinvestimentos.dto.SimulacaoResponse;
import com.github.rogerioja89.simulacaoinvestimentos.entity.Simulacao;
import com.github.rogerioja89.simulacaoinvestimentos.repository.SimulacaoRepository;
import com.github.rogerioja89.simulacaoinvestimentos.service.SimulacaoService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Path("/simulacoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SimulacaoResource {

    @Inject
    SimulacaoService simulacaoService;

    @Inject
    SimulacaoRepository simulacaoRepository;

    @Inject
    ObjectMapper objectMapper;

    @POST
    public Response criarSimulacao(String payload) {
        if (payload == null || payload.isBlank()) {
            return Response.status(422)
                    .entity(new ErroResponse(
                            "PAYLOAD_AUSENTE",
                            "Payload ausente para criação da simulação.",
                            "Envie um JSON no corpo com clienteId, valor, prazoMeses e tipoProduto."
                    ))
                    .build();
        }

        SimulacaoRequest request;
        try {
            request = objectMapper.readValue(payload, SimulacaoRequest.class);
        } catch (JsonProcessingException e) {
            return Response.status(422)
                    .entity(new ErroResponse(
                            "PAYLOAD_INVALIDO",
                            "Payload inválido para criação da simulação.",
                            "Verifique se o JSON está válido e se os tipos dos campos estão corretos."
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
                        produto.getId(),
                        produto.getNome(),
                        produto.getTipoProduto(),
                        produto.getRentabilidadeAnual(),
                        produto.getRisco()
                ),
                new ResultadoSimulacaoResponse(
                        BigDecimal.valueOf(simulacao.getValorFinal()).setScale(2, RoundingMode.HALF_UP),
                        simulacao.getPrazoMeses()
                ),
                simulacao.getDataSimulacao()
        );

        return Response.ok(response).build();
    }

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
                        simulacao.getId(),
                        simulacao.getClienteId(),
                        simulacao.getProdutoNome(),
                        simulacao.getTipoProduto(),
                        BigDecimal.valueOf(simulacao.getValorInvestido()).setScale(2, RoundingMode.HALF_UP),
                        simulacao.getPrazoMeses(),
                        simulacao.getRentabilidadeAplicada(),
                        BigDecimal.valueOf(simulacao.getValorFinal()).setScale(2, RoundingMode.HALF_UP),
                        simulacao.getDataSimulacao()
                ))
                .toList();

        return Response.ok(response).build();
    }
}