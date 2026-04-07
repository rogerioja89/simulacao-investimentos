package com.github.rogerioja89.simulacaoinvestimentos.resource;

import com.github.rogerioja89.simulacaoinvestimentos.entity.Simulacao;
import com.github.rogerioja89.simulacaoinvestimentos.service.SimulacaoService;
import com.github.rogerioja89.simulacaoinvestimentos.repository.SimulacaoRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

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
        Optional<Simulacao> simulacaoOpt = simulacaoService.criarSimulacao(
                request.clienteId,
                request.valor,
                request.prazoMeses,
                request.tipoProduto
        );

        if (simulacaoOpt.isEmpty()) {
            return Response.status(422) // código HTTP para Unprocessable Entity
                    .entity("Não foi possível criar a simulação. Verifique os dados informados.")
                    .build();
        }

        return Response.ok(simulacaoOpt.get()).build();
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
        return Response.ok(simulacoes).build();
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
}