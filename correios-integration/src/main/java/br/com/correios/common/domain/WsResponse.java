package br.com.correios.common.domain;

/**
 * Created by rpeixoto on 07/08/15.
 */
public class WsResponse {

    private Long code;
    private String message;
    private FieldValidationError[] fieldValidationErrors;

    public WsResponse() {
    }

    public WsResponse(Long code, String message) {
        this(code, message, null);
    }

    public WsResponse(Long code, String message, FieldValidationError[] fieldValidationErrors) {
        this.code = code;
        this.message = message;
        this.fieldValidationErrors = fieldValidationErrors;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public FieldValidationError[] getFieldValidationErrors() {
        return fieldValidationErrors;
    }

    public void setFieldValidationErrors(FieldValidationError[] fieldValidationErrors) {
        this.fieldValidationErrors = fieldValidationErrors;
    }
}
