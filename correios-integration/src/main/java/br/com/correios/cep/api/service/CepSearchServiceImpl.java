package br.com.correios.cep.api.service;

import br.com.correios.cep.integration.domain.CepSearchDetails;
import br.com.correios.cep.integration.service.CorreiosIntegrationServiceImpl;
import br.com.correios.cep.api.exception.CepNotFoundException;
import br.com.correios.util.StringHelper;
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
    private CorreiosIntegrationServiceImpl correiosIntegrationServiceImpl;

    @Override
    public CepSearchDetails findCepDetails(String cep) {
        return findCepDetailsRecursive(cep, 0);
    }

    private CepSearchDetails findCepDetailsRecursive(final String cepNumber, final int retry) {
        logger.debug("Buscando CEP. cepNumber={} retry={}", cepNumber, retry);
        try {
            final CepSearchDetails cepDetails = correiosIntegrationServiceImpl.findCepDetails
                    (cepNumber);

            logger.debug("Cep encontrado com sucesso. cepNumber={} retry={} cepDetails={}", cepNumber, retry, cepDetails);
            return cepDetails;
        } catch (CepNotFoundException ex) {
            int newRetry = retry + 1;

            //Deve buscar pelo CEP adicionando zeros a direita
            final String cepToFind = stringHelper.rightReplaceWithZeros(cepNumber, newRetry);
            if (hasAlreadySearchedAllCepCombinations(cepNumber, cepToFind)) {
                throw new CepNotFoundException("Cep nao encontrada apos todas tentativas",
                        cepNumber);
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

