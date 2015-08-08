package br.com.manager.address.controller;

import br.com.manager.address.domain.*;
import br.com.manager.address.exception.AddressNotFoundException;
import br.com.manager.address.exception.InvalidCepException;
import br.com.manager.address.service.AddressService;
import br.com.manager.common.constants.WsResponseCode;
import br.com.manager.common.domain.WsListResponse;
import br.com.manager.common.domain.WsResponse;
import br.com.manager.common.util.WsResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by rpeixoto on 02/08/15.
 */
@RestController
public class AddressManagerController {

    private final static Logger logger = LoggerFactory.getLogger(AddressManagerController.class);

    @Autowired
    private AddressService addressService;

    @Autowired
    private WsResponseBuilder wsResponseBuilder;

    @Value("${application.base.url}")
    private String applicationBaseUrl;

    @RequestMapping(value = "/address", method = RequestMethod.POST)
    public AddressCreationResponse createAddress(@RequestBody @Valid final Address address) {

        //Chama o servico
        final CompleteAddress newAddress = addressService.createNewAddress(address);

        //Monta a resposta
        final AddressCreationResponse addressCreationResponse = new AddressCreationResponse(newAddress.getCep(), newAddress.getId());
        return wsResponseBuilder.getWsResponse(WsResponseCode.ADDRESS_CREATE_SUCCESS, addressCreationResponse);
    }

    @RequestMapping(value = "/address", method = RequestMethod.GET)
    public WsListResponse<List<CompleteAddress>> findAddress(@RequestParam(value = "page", required = false) Integer page) {

        final CompleteAddressList completeAddressList = addressService.findAll(page);
        final String nextPageUrl = getNextPageUrl(page, completeAddressList);

        //Monta a resposta
        final WsListResponse completeAddressWsListResponse = new WsListResponse<>(completeAddressList.getCompleteAddresses(), nextPageUrl);
        return wsResponseBuilder.getWsResponse(WsResponseCode.ADDRESS_FIND_SUCCESS, completeAddressWsListResponse);
    }

    private String getNextPageUrl(@RequestParam(value = "page", required = false) Integer page, CompleteAddressList completeAddressList) {

        if (!completeAddressList.hasNextPage()) {
            return null;
        }

        final int nextPageIndex;
        if (page == null) {
            nextPageIndex = 2;
        } else {
            nextPageIndex = page + 1;
        }


        final UriComponentsBuilder nextPageUrlBuilder = UriComponentsBuilder.fromHttpUrl(applicationBaseUrl);
        nextPageUrlBuilder.path("/address");
        nextPageUrlBuilder.queryParam("page", nextPageIndex);
        return nextPageUrlBuilder.toUriString();
    }

    @RequestMapping(value = "/address/{addressId}", method = RequestMethod.GET)
    public Address findAddress(@PathVariable("addressId") final Long addressId) {
        final Address address = addressService.findById(addressId);

        //Monta a resposta
        return wsResponseBuilder.getWsResponse(WsResponseCode.ADDRESS_FIND_SUCCESS, address);
    }

    @RequestMapping(value = "/address/{addressId}", method = RequestMethod.DELETE)
    public WsResponse removeAddress(@PathVariable("addressId") final Long addressId) {
        addressService.removeAddress(addressId);
        return wsResponseBuilder.getNoContetyWsResponse(WsResponseCode.ADDRESS_REMOVE_SUCCESS);
    }

    @RequestMapping(value = "/address/{addressId}", method = RequestMethod.PUT)
    public WsResponse updateAddress(@PathVariable("addressId") final Long addressId,
                                    @RequestBody @Valid final UpdateAddress address) {
        addressService.updateAddress(addressId, address);
        return wsResponseBuilder.getNoContetyWsResponse(WsResponseCode.ADDRESS_UPDATE_SUCCESS);
    }

    @ExceptionHandler(AddressNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public WsResponse addressNotFoundExceptionHandler(AddressNotFoundException ex) {
        logger.debug("Endereco nao encontrado. addressId={}", ex.getAddressId());
        return wsResponseBuilder.getNoContetyWsResponse(WsResponseCode.ADDRESS_NOT_FOUND, ex.getAddressId());
    }

    @ExceptionHandler(InvalidCepException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public WsResponse invalidCepExceptionHandler(InvalidCepException ex) {
        logger.debug("CEP nao encontrado. cep={}", ex.getCep());
        return wsResponseBuilder.getNoContetyWsResponse(WsResponseCode.ADDRESS_INVALID_CEP, ex.getCep());
    }
}
