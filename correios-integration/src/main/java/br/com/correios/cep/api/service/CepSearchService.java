package br.com.correios.cep.api.service;

import br.com.correios.cep.integration.domain.CepSearchDetails;

/**
 * Created by rpeixoto on 03/08/15.
 */
public interface CepSearchService {

    /**
     * Busca os detalhes de um CEP
     *
     * @param cep a ser utilizado na consulta
     * @return Detalhes do CEP buscado
     */
    CepSearchDetails findCepDetails(String cep);
}
