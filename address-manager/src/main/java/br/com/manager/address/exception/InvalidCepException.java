package br.com.manager.address.exception;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by rpeixoto on 06/08/15.
 */
public class InvalidCepException extends RuntimeException{

    private final String cep;

    public InvalidCepException(String message, String cep) {
        super(message);
        this.cep = cep;
    }

    public String getCep() {
        return cep;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("message", getMessage())
                .append("cep", cep)
                .toString();
    }
}

