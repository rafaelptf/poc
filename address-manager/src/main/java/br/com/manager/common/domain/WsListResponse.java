package br.com.manager.common.domain;

import java.util.List;

/**
 * Created by rpeixoto on 07/08/15.
 */
public class WsListResponse<T> extends WsResponse {

    private final List<T> content;

    public WsListResponse(List<T> content) {
        this.content = content;
    }

    public List<T> getContent() {
        return content;
    }
}
