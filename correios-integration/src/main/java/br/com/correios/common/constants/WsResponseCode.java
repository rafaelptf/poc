package br.com.correios.common.constants;

/**
 * Created by rpeixoto on 03/08/15.
 */
public enum WsResponseCode {

    //Range de 2000 a 3000: Mensagens de sucesso
    CEP_FOUND(2001l, MessageKey.CEP_FOUND),

    //Range de 4000 a 5000: Mensagens de erro que foram gerados decorrentes de valores invalidos na requisicao
    REQUEST_VALIDATION_ERROR(4001l, MessageKey.REQUEST_VALIDATION_ERROR),
    HTTP_METHOD_NOT_ALLOWED_ERROR(4002l, MessageKey.HTTP_METHOD_NOT_ALLOWED_ERROR),
    CEP_NOT_FOUND(4003l, MessageKey.CEP_NOT_FOUND),

    //Range de 5000 a 6000: Mensagens de erro decorrentes de problemas na propria aplicacao
    GENERIC_ERROR(5001l, MessageKey.GENERIC_ERROR);

    private final Long code;
    private final MessageKey messageKey;

    WsResponseCode(Long code, MessageKey messageKey) {
        this.code = code;
        this.messageKey = messageKey;
    }

    public Long getCode() {
        return code;
    }

    public MessageKey getMessageKey() {
        return messageKey;
    }
}
