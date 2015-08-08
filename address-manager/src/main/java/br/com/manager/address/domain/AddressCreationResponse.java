package br.com.manager.address.domain;

import br.com.manager.common.domain.WsResponse;

/**
 * Created by rpeixoto on 02/08/15.
 */
public class AddressCreationResponse extends WsResponse{

    private final String cep;
    private final Long addressId;

    /**
     * Construtor utilizado pelo RestAssured
     */
    private AddressCreationResponse() {
        this.cep = null;
        this.addressId = null;
    }

    public AddressCreationResponse(String cep, Long addressId) {
        this.cep = cep;
        this.addressId = addressId;
    }

    public String getCep() {
        return cep;
    }

    public Long getAddressId() {
        return addressId;
    }
}
