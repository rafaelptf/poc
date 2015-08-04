package br.com.correios.cep.integration.service;

import br.com.correios.cep.api.exception.CepNotFoundException;
import br.com.correios.cep.integration.domain.CepSearchDetails;

import java.io.IOException;

/**
 * Created by rpeixoto on 03/08/15.
 */
public interface CorreiosIntegrationService {

    /**
     * Busca detalhes de um endereço a partir do CEP
     *
     * @param cep cep a ser buscado
     * @return VO contendo detalhes do cep buscado
     * @throws CepNotFoundException Quando cep nao for encontrado
     */
    CepSearchDetails findCepDetails(String cep) throws CepNotFoundException;
}
