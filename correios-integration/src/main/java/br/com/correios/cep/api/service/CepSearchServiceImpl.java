package br.com.correios.cep.api.service;

import br.com.correios.cep.integration.domain.CepSearchDetails;
import br.com.correios.cep.integration.service.CorreiosIntegrationService;
import br.com.correios.cep.api.exception.CepNotFoundException;
import br.com.correios.common.util.StringHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by rpeixoto on 03/08/15.
 */
@Component
public class CepSearchServiceImpl implements CepSearchService {

    private final static Logger logger = LoggerFactory.getLogger(CepSearchServiceImpl.class);

    @Autowired
    private StringHelper stringHelper;

    @Autowired
    private CorreiosIntegrationService correiosIntegrationService;

    @Override
    public CepSearchDetails findCepDetails(String cep) {
        return findCepDetailsRecursive(cep, 0);
    }

    /**
     * Busca o cep recursivamente, caso o serviço de integração com o correio nao ache o endereço para o CEP,
     * chama o próprio método, porém com o CEP alterado adicionando zero a direita
     *
     * O ponto de parada da recursividade é ter buscado todos as combinações de CEP possível, ou seja,
     * o CEP está igual a 00000-000
     *
     * @param cep
     * @param retry
     * @return
     */
    private CepSearchDetails findCepDetailsRecursive(final String cep, final int retry) {
        logger.debug("Buscando CEP. cep={} retry={}", cep, retry);
        try {
            final CepSearchDetails cepDetails = correiosIntegrationService.findCepDetails
                    (cep);

            logger.debug("Cep encontrado com sucesso. cep={} retry={} cepDetails={}", cep, retry, cepDetails);
            return cepDetails;
        } catch (CepNotFoundException ex) {
            int newRetry = retry + 1;

            //Deve buscar pelo CEP adicionando zeros a direita
            final String cepToFind = stringHelper.rightReplaceWithZeros(cep, newRetry);
            if (hasAlreadySearchedAllCepCombinations(cep, cepToFind)) {
                throw new CepNotFoundException("Cep nao encontrado apos todas tentativas",
                        cep);
            }

            return findCepDetailsRecursive(cepToFind, newRetry);
        }
    }

    /**
     * Verifica se o numero do CEP gerado é igual a um CEP composto de somente números zero, caso
     * afirmativo,
     * retorna true, indicando que não existe mais CEPs a serem buscados
     *
     * @param cepNumber
     * @param cepToFind
     * @return
     */
    private boolean hasAlreadySearchedAllCepCombinations(String cepNumber, String cepToFind) {
        final String cepWithOnlyZeros = StringUtils.repeat("0", cepNumber.length());
        if (StringUtils.equals(cepToFind, cepWithOnlyZeros)) {
            return true;
        }
        return false;
    }

}

