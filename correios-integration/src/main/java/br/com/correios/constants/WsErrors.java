package br.com.correios.constants;

/**
 * Created by rpeixoto on 03/08/15.
 */
public enum WsErrors {

    REQUEST_VALIDATION_ERROR("00001", MessageKey.REQUEST_VALIDATION_ERROR),
    GENERIC_ERROR("00002", MessageKey.GENERIC_ERROR),
    HTTP_METHOD_NOT_ALLOWED_ERROR("00003", MessageKey.HTTP_METHOD_NOT_ALLOWED_ERROR),
    CEP_NOT_FOUND("00004", MessageKey.CEP_NOT_FOUND);

    private final String errorCode;
    private final MessageKey messageKey;

    WsErrors(String errorCode, MessageKey messageKey) {
        this.errorCode = errorCode;
        this.messageKey = messageKey;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public MessageKey getMessageKey() {
        return messageKey;
    }
}
