package br.com.manager.common.domain;

import java.util.List;

/**
 * Created by rpeixoto on 07/08/15.
 */
public class WsListResponse<T> extends WsResponse {

    private final List<T> content;
    private final String nextPageUrl;

    public WsListResponse(List<T> content, String nextPageUrl) {
        this.content = content;
        this.nextPageUrl = nextPageUrl;
    }

    public List<T> getContent() {
        return content;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }
}
