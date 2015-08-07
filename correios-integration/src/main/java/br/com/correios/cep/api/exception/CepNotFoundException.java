package br.com.correios.cep.api.exception;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by rpeixoto on 03/08/15.
 */
public class CepNotFoundException extends RuntimeException {

    private final String cep;

    public CepNotFoundException(String message, String cep) {
        super(message);
        this.cep = cep;
    }

    public String getCep() {
        return cep;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("cep", cep)
                .toString();
    }
}
