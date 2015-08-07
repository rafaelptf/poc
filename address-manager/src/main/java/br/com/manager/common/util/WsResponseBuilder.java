package br.com.manager.common.util;

import br.com.manager.common.constants.WsResponseCode;
import br.com.manager.common.domain.FieldValidationError;
import br.com.manager.common.domain.WsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by rpeixoto on 07/08/15.
 */
@Component
public class WsResponseBuilder {

    @Autowired
    private MessageSourceWrapper messageSourceWrapper;

    public WsResponse getNoContetyWsResponse(WsResponseCode wsResponseCode) {
        return getNoContetyWsResponse(wsResponseCode, null);
    }

    public WsResponse getNoContetyWsResponse(WsResponseCode wsResponseCode, Object... args) {
        return getWsResponse(wsResponseCode, null, new WsResponse(), args);
    }

    public WsResponse getFieldValidationErrorWsResponse(WsResponseCode wsResponseCode,
                                                        FieldValidationError[] fieldValidationErrors,
                                                        Object... args) {
        return getWsResponse(wsResponseCode, fieldValidationErrors, new WsResponse(), args);
    }

    public <R extends WsResponse> R getWsResponse(WsResponseCode wsResponseCode, R content) {
        return getWsResponse(wsResponseCode, null, content, null);
    }

    public <R extends WsResponse> R getWsResponse(WsResponseCode wsResponseCode, FieldValidationError[] fieldValidationErrors, R content, Object... args) {
        final String message = messageSourceWrapper.getWsResponseMessage(wsResponseCode, args);
        content.setMessage(message);
        content.setCode(wsResponseCode.getCode());
        content.setFieldValidationErrors(fieldValidationErrors);
        return content;
    }
}
