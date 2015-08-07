package br.com.manager.cep.integration.domain;

import br.com.manager.common.domain.FieldValidationError;

/**
 * Created by rpeixoto on 07/08/15.
 */
public class IntegrationBaseResponse {

    private Long code;
    private String message;
    private FieldValidationError[] fieldValidationErrors;

    public IntegrationBaseResponse() {
    }

    public Long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public FieldValidationError[] getFieldValidationErrors() {
        return fieldValidationErrors;
    }


}
