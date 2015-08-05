package br.com.manager.address.controller;

import br.com.manager.address.domain.Address;
import br.com.manager.address.domain.AddressCreationRequest;
import br.com.manager.address.domain.AddressCreationResponse;
import br.com.manager.address.domain.CompleteAddress;
import br.com.manager.address.service.AddressService;
import br.com.manager.common.util.MessageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import sun.security.provider.MD5;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by rpeixoto on 02/08/15.
 */
@RestController
public class AddressManagerController {

    private final static Logger logger = LoggerFactory.getLogger(AddressManagerController.class);

    @Autowired
    private MessageHelper messageHelper;

    @Autowired
    private AddressService addressService;

    @RequestMapping(value = "/address", method = RequestMethod.POST)
    public AddressCreationResponse createAddress(@RequestBody @Valid final AddressCreationRequest addressCreationRequest, Errors errors) {

        logger.debug("Adicionando endereco. addressCreationRequest={}", addressCreationRequest);

        final CompleteAddress newAddress = addressService.createNewAddress(addressCreationRequest);
        return new AddressCreationResponse("000", newAddress.getCep(), newAddress.getId());
    }

    @RequestMapping(value = "/address/{addressId}", method = RequestMethod.GET)
    public Address findAddress(@PathVariable("addressId") final Long addressId) {

        final Address address = addressService.findById(addressId);
        return address;
    }

    @RequestMapping(value = "/address", method = RequestMethod.GET)
    public List<CompleteAddress> findAddress() {

        final List<CompleteAddress> completeAddresses = addressService.findAll();
        return completeAddresses;
    }


//    protected CepSearchResponse createNewAddress(String cep) {
//        try {
//            final CepSearchDetails cepSearchDetails = cepSearchService.createNewAddress(cep);
//
//            final String cepFoundMessage = messageHelper.getMessage(MessageKey.CEP_FOUND);
//            return CepSearchResponseBuilder
//                    .cepFound(cepFoundMessage)
//                    .setCep(cepSearchDetails.getCep())
//                    .setStreet(cepSearchDetails.getStreet())
//                    .setDistrict(cepSearchDetails.getDistrict())
//                    .setCity(cepSearchDetails.getCity())
//                    .setState(cepSearchDetails.getState())
//                    .build();
//
//        } catch (CepNotFoundException e) {
//            logger.debug("CEP nao encontrado. cep={}", cep);
//            final String cepNotFoundMessage = messageHelper.getWsErrorMessage(WsErrors.CEP_NOT_FOUND);
//            return CepSearchResponseBuilder.cepNotFound(cepNotFoundMessage).build();
//        }
//    }
}
