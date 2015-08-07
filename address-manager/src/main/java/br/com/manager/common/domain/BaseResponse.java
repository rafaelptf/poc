package br.com.manager.common.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by rpeixoto on 03/08/15.
 */
public class BaseResponse {

    private final Long code;
    private final String message;

    public BaseResponse(Long code, String message) {
        this.code = code;
        this.message = message;
    }

    public BaseResponse(BaseResponse baseResponse) {
        this(baseResponse.getCode(), baseResponse.getMessage());
    }

    public Long getCode() {
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
