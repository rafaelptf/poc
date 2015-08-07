package br.com.correios.common.constants;

/**
 * Created by rpeixoto on 03/08/15.
 */
public enum WsResponseCode {

    //Range de 2000 a 3000: Mensagens de sucesso
    CEP_FOUND(2001l, "cep.found"),

    //Range de 4000 a 5000: Mensagens de erro que foram gerados decorrentes de valores invalidos na requisicao
    REQUEST_VALIDATION_ERROR(4001l, "request.validation.error"),
    HTTP_METHOD_NOT_ALLOWED_ERROR(4002l, "httpMethod.notAllowed"),
    CEP_NOT_FOUND(4003l, "cep.notFound"),
    UNSUPPORTED_MEDIA_TYPE(4004l, "unsupported.mediaType"),

    //Range de 5000 a 6000: Mensagens de erro decorrentes de problemas na propria aplicacao
    GENERIC_ERROR(5001l, "generic.error");

    private final Long code;
    private final String messageKey;

    WsResponseCode(Long code, String messageKey) {
        this.code = code;
        this.messageKey = messageKey;
    }

    public Long getCode() {
        return code;
    }

    public String getMessageKey() {
        return messageKey;
    }
}
