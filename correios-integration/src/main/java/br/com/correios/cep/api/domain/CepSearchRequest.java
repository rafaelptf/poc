package br.com.correios.cep.api.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;

/**
 * Created by rpeixoto on 02/08/15.
 */
public class CepSearchRequest {

    private String cep;

    @NotEmpty(message = "{cepSearchRequest.cep.empty}")
    @Pattern(regexp = "\\d{5}-?\\d{3}", message = "{cepSearchRequest.cep.pattern}")
    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("cep", cep)
                .toString();
    }
}
