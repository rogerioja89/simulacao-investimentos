package com.github.rogerioja89.simulacaoinvestimentos.resource.dto;

public class ErroResponse {
    public String codigo;
    public String mensagem;
    public String detalhes;

    public ErroResponse(String codigo, String mensagem, String detalhes) {
        this.codigo = codigo;
        this.mensagem = mensagem;
        this.detalhes = detalhes;
    }
}
