package br.com.correios.cep.api.controller;

import br.com.correios.cep.api.domain.CepSearchRequest;
import br.com.correios.cep.api.domain.CepSearchResponse;
import br.com.correios.cep.api.domain.CepSearchResponseBuilder;
import br.com.correios.cep.api.exception.CepNotFoundException;
import br.com.correios.cep.api.service.CepSearchService;
import br.com.correios.cep.api.service.CepSearchServiceImpl;
import br.com.correios.cep.integration.domain.CepSearchDetails;
import br.com.correios.common.constants.MessageKey;
import br.com.correios.common.constants.WsErrors;
import br.com.correios.common.util.MessageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by rpeixoto on 02/08/15.
 */
@RestController
public class CepSearchController {

    private final static Logger logger = LoggerFactory.getLogger(CepSearchServiceImpl.class);

    @Autowired
    private CepSearchService cepSearchService;

    @Autowired
    private MessageHelper messageHelper;

    @RequestMapping(value = "/cep/{cep}", method = RequestMethod.GET)
    public CepSearchResponse findCepDetailsByPathVariable(@PathVariable("cep") final String cep) {
        return findCepDetails(cep);
    }

    @RequestMapping(value = "/cep", method = RequestMethod.POST)
    public CepSearchResponse findCepDetailsByJson(@RequestBody @Valid final CepSearchRequest cepSearchRequest) {
        return findCepDetails(cepSearchRequest.getCep());
    }

    protected CepSearchResponse findCepDetails(String cep) {
        try {
            final CepSearchDetails cepSearchDetails = cepSearchService.findCepDetails(cep);

            final String cepFoundMessage = messageHelper.getMessage(MessageKey.CEP_FOUND);
            return CepSearchResponseBuilder
                    .cepFound(cepFoundMessage)
                    .setCep(cepSearchDetails.getCep())
                    .setStreet(cepSearchDetails.getStreet())
                    .setDistrict(cepSearchDetails.getDistrict())
                    .setCity(cepSearchDetails.getCity())
                    .setState(cepSearchDetails.getState())
                    .build();

        } catch (CepNotFoundException e) {
            logger.debug("CEP nao encontrado. cep={}", cep);
            final String cepNotFoundMessage = messageHelper.getWsErrorMessage(WsErrors.CEP_NOT_FOUND);
            return CepSearchResponseBuilder.cepNotFound(cepNotFoundMessage).build();
        }
    }
}
