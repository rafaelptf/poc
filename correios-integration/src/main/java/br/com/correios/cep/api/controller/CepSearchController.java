package br.com.correios.cep.api.controller;

import br.com.correios.cep.api.domain.CepSearchRequest;
import br.com.correios.cep.api.domain.CepSearchResponse;
import br.com.correios.cep.api.domain.CepSearchResponseBuilder;
import br.com.correios.cep.api.exception.CepNotFoundException;
import br.com.correios.cep.api.service.CepSearchService;
import br.com.correios.cep.api.service.CepSearchServiceImpl;
import br.com.correios.cep.integration.domain.CepSearchDetails;
import br.com.correios.common.constants.WsResponseCode;
import br.com.correios.common.util.MessageSourceWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
    private MessageSourceWrapper messageSourceWrapper;

    @RequestMapping(value = "/cep/{cep}", method = RequestMethod.GET)
    public CepSearchResponse findCepDetailsByPathVariable(@PathVariable("cep") @Valid final String cep,
                                                          final HttpServletResponse response) {
        return findCepDetails(cep, response);
    }

    @RequestMapping(value = "/cep", method = RequestMethod.POST)
    public CepSearchResponse findCepDetailsByJson(@RequestBody @Valid final CepSearchRequest cepSearchRequest,
                                                  final HttpServletResponse response) {
        return findCepDetails(cepSearchRequest.getCep(), response);
    }

    protected CepSearchResponse findCepDetails(String cep, HttpServletResponse response) {
        final CepSearchDetails cepSearchDetails = cepSearchService.findCepDetails(cep);

        final String cepFoundMessage = messageSourceWrapper.getWsResponseMessage(WsResponseCode.CEP_FOUND);
        return CepSearchResponseBuilder
                .cepFound(cepFoundMessage)
                .setCep(cepSearchDetails.getCep())
                .setStreet(cepSearchDetails.getStreet())
                .setDistrict(cepSearchDetails.getDistrict())
                .setCity(cepSearchDetails.getCity())
                .setState(cepSearchDetails.getState())
                .build();

    }


    @ExceptionHandler(CepNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public CepSearchResponse cepNotFoundHandler(CepNotFoundException ex) {
        logger.debug("CEP nao encontrado. cep={}", ex.getCep());

        final String cepNotFoundMessage = messageSourceWrapper.getWsResponseMessage(WsResponseCode.CEP_NOT_FOUND);
        return CepSearchResponseBuilder.cepNotFound(cepNotFoundMessage).build();
    }
}
