package br.com.correios.common.json;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by rpeixoto on 03/08/15.
 */
public class RequestError {

    private final String code;
    private final String message;

    public RequestError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("code", code)
                .append("message", message)
                .toString();
    }
}
