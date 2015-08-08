package br.com.manager.cep.integration.service;

import br.com.manager.cep.integration.domain.CepSearchRequest;
import br.com.manager.cep.integration.domain.CepSearchResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.ConnectException;

import static br.com.manager.cep.integration.CepIntegrationConstants.SUCCESS_CEP_SEARCH_STATUS;

/**
 * Created by rpeixoto on 05/08/15.
 */
@Component
public class CepIntegrationServiceImpl implements CepIntegrationService {

    private final static Logger logger = LoggerFactory.getLogger(CepIntegrationServiceImpl.class);

    @Value("${cep.search.url}")
    private String cepSearchUrl;

    @Override
    public boolean isValidCep(String cep) {
        logger.debug("Validando CEP. cep={}", cep);
        final String plainCepRequestText = getPlainCepText(cep);

        //Realiza o post para o servico de CEP
        final CepSearchResponse cepSearchResponse = postForCepSearchResponse(plainCepRequestText);

        // Servico retornou sucesso, e o CEP é igual ao que tentou inserir
        final Long responseCode = cepSearchResponse.getCode();
        final String responseCep = cepSearchResponse.getCep();
        if (SUCCESS_CEP_SEARCH_STATUS.equals(responseCode) && responseCep.equals(plainCepRequestText)) {
            logger.debug("Cep valido. cep={}", cep);
            return true;
        }

        logger.debug("Cep invalido. cep={}", cep);
        return false;
    }

    /**
     * Realiza o post para o servico de busca de cep
     *
     * @param plainCepRequestText
     * @return
     */
    private CepSearchResponse postForCepSearchResponse(String plainCepRequestText) {

        //Obtem restTemplate customizado
        final RestTemplate restTemplate = getRestTemplate();

        final CepSearchResponse cepSearchResponse;
        try {
            cepSearchResponse = restTemplate.postForObject(cepSearchUrl,
                    new CepSearchRequest(plainCepRequestText),
                    CepSearchResponse.class);
        } catch (RestClientException e) {
            final Throwable rootCause = e.getRootCause();
            if (rootCause instanceof ConnectException) {
                logger.error("Erro ao conectar no servico de CEP. Instancia do servico de busca de CEPs esta rodando?", rootCause);
            }
            throw e;
        }
        return cepSearchResponse;
    }

    /**
     * Obtem RestTemplate customizado que nao da exception em caso de HTTP 404 e 400, fazendo assim o parse do responseBody.
     *
     * @return
     */
    private RestTemplate getRestTemplate() {
        final RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            protected boolean hasError(HttpStatus statusCode) {
                if (statusCode == HttpStatus.NOT_FOUND || statusCode == HttpStatus.BAD_REQUEST) {
                    return false;
                }

                return super.hasError(statusCode);
            }
        });
        return restTemplate;
    }

    /**
     * Caso o cep esteja no padrão 00000-000, remove o caracter "-"
     *
     * @param cep
     * @return
     */
    private String getPlainCepText(String cep) {
        if (StringUtils.contains(cep, "-")) {
            return StringUtils.remove(cep, "-");
        }
        return cep;
    }
}
