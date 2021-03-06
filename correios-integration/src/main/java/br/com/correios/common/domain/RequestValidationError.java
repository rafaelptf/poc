package br.com.correios.common.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by rpeixoto on 03/08/15.
 */
public class RequestValidationError  {

    private FieldValidationError [] errors;

    public RequestValidationError(FieldValidationError[] errors) {
        this.errors = errors;
    }

    public FieldValidationError[] getErrors() {
        return errors;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("errors", errors)
                .toString();
    }
}
