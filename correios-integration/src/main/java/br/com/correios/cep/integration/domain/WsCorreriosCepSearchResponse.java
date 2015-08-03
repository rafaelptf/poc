package br.com.correios.cep.integration.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by rpeixoto on 03/08/15.
 */
public class WsCorreriosCepSearchResponse {

    private String bairro;
    private String cidade;
    private String cep;
    private String logradouro;
    private String estado;

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("bairro", bairro)
                .append("cidade", cidade)
                .append("cep", cep)
                .append("logradouro", logradouro)
                .append("estado", estado)
                .toString();
    }
}
