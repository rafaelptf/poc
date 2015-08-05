package br.com.manager.address.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by rpeixoto on 02/08/15.
 */
public class AddressCreationResponse {

    private final String status;
    private final String cep;
    private final Long addressId;

    public AddressCreationResponse(String status, String cep, Long addressId) {
        this.status = status;
        this.cep = cep;
        this.addressId = addressId;
    }

    public String getStatus() {
        return status;
    }

    public String getCep() {
        return cep;
    }

    public Long getAddressId() {
        return addressId;
    }
}
