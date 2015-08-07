package br.com.manager.cep.integration.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by rpeixoto on 02/08/15.
 */
public class CepSearchRequest {

    private final String cep;

    public CepSearchRequest(String cep) {
        this.cep = cep;
    }

    public String getCep() {
        return cep;
    }

    public String toString() {
        return new ToStringBuilder(this)
                .append("cep", cep)
                .toString();
    }
}
