package br.com.correios.common.json;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by rpeixoto on 03/08/15.
 */
public class RequestValidationError extends BaseResponse {

    private FieldValidationError [] errors;

    public RequestValidationError(Long code, String message, FieldValidationError[] errors) {
        super(code, message);
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
