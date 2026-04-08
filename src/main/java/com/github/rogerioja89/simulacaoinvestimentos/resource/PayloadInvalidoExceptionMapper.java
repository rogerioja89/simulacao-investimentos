package com.github.rogerioja89.simulacaoinvestimentos.resource;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class PayloadInvalidoExceptionMapper implements ExceptionMapper<BadRequestException> {

    @Context
    UriInfo uriInfo;

    @Context
    Request request;

    @Override
    public Response toResponse(BadRequestException exception) {
        boolean isPostSimulacoes = request != null
                && "POST".equalsIgnoreCase(request.getMethod())
                && uriInfo != null
                && "simulacoes".equals(uriInfo.getPath());

        if (!isPostSimulacoes) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new SimulacaoResource.ErroResponse(
                            "REQUISICAO_INVALIDA",
                            "Requisição inválida.",
                            "Verifique os dados enviados e tente novamente."
                    ))
                    .build();
        }

        return Response.status(422)
                .entity(new SimulacaoResource.ErroResponse(
                        "PAYLOAD_INVALIDO",
                        "Payload inválido para criação da simulação.",
                        "Verifique se o JSON está válido e se os tipos dos campos estão corretos."
                ))
                .build();
    }
}

