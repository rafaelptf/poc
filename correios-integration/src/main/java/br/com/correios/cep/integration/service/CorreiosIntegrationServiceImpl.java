package br.com.correios.cep.integration.service;

import br.com.correios.cep.integration.domain.CepSearchDetails;
import br.com.correios.cep.integration.domain.WsCorreriosCepSearchResponse;
import br.com.correios.cep.api.exception.CepNotFoundException;
import br.com.correios.util.RestTemplateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

/**
 * Created by rpeixoto on 03/08/15.
 */
@Component
public class CorreiosIntegrationServiceImpl implements CorreiosIntegrationService {

    @Autowired
    private RestTemplateWrapper restTemplateWrapper;

    /**
     *  Busca o CEP num servico de terceiros.
     *
     * @param cep cep a ser buscado
     * @return
     * @throws CepNotFoundException
     */
    @Override
    public CepSearchDetails findCepDetails(String cep) throws CepNotFoundException {

        final Map<String, String> vars = Collections.singletonMap("cep", cep);

        final String correiosWSUrl = "http://api.postmon.com.br/v1/cep/{cep}";
        final WsCorreriosCepSearchResponse wsCorreriosCepSearchResponse = restTemplateWrapper
                .getForObject(correiosWSUrl, WsCorreriosCepSearchResponse.class, vars);

        if (wsCorreriosCepSearchResponse == null) {
            throw new CepNotFoundException("Cep nao encontrado no WS dos correios", cep);
        }

        final CepSearchDetails cepSearchDetails =
                new CepSearchDetails(wsCorreriosCepSearchResponse.getCep(),
                        wsCorreriosCepSearchResponse.getLogradouro(),
                        wsCorreriosCepSearchResponse.getBairro(),
                        wsCorreriosCepSearchResponse.getCidade(),
                        wsCorreriosCepSearchResponse.getEstado());

        return cepSearchDetails;
    }


}
