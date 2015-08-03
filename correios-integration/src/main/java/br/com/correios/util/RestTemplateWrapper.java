package br.com.correios.util;

import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

/**
 * Created by rpeixoto on 03/08/15.
 */
@Component
public class RestTemplateWrapper {

    public <T> T getForObject(String url, Class<T> responseType, Map<String, ?> urlVariables) throws RestClientException {

        final RestTemplate restTemplate = getRestTemplate();
        try {
            final T object = restTemplate.getForObject(url,
                    responseType,
                    urlVariables);
            return object;
        }
        catch (final HttpClientErrorException e) {
            if(e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return null;
            }
            throw e;
        }
    }

    private RestTemplate getRestTemplate() {
        final RestTemplate restTemplate = new RestTemplate();
        final ClientHttpRequestInterceptor userAgentChangerInterceptor = new ClientHttpRequestInterceptor() {
            @Override
            public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
                httpRequest.getHeaders().add("User-Agent", "Chrome");
                return clientHttpRequestExecution.execute(httpRequest, bytes);
            }
        };
        restTemplate.setInterceptors(Collections.singletonList(userAgentChangerInterceptor));
        return restTemplate;
    }

}
