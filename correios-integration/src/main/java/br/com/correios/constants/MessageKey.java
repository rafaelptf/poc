package br.com.correios.constants;

/**
 * Created by rpeixoto on 03/08/15.
 */
public enum MessageKey {

    REQUEST_VALIDATION_ERROR("request.validation.error"),
    GENERIC_ERROR("generic.error"),
    HTTP_METHOD_NOT_ALLOWED_ERROR("httpMethod.notAllowed"),
    CEP_NOT_FOUND("cep.notFound"),
    CEP_FOUND("cep.found");

    private final String messageKey;

    MessageKey(String messageKey) {
        this.messageKey = messageKey;
    }

    public String getMessageKey() {
        return messageKey;
    }
}
