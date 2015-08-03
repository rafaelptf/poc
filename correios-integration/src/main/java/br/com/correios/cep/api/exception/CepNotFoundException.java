package br.com.correios.cep.api.exception;

/**
 * Created by rpeixoto on 03/08/15.
 */
public class CepNotFoundException extends RuntimeException {

    private final String cep;

    public CepNotFoundException(String message, String cep) {
        super(message);
        this.cep = cep;
    }
}
