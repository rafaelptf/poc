package br.com.manager.address.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by rpeixoto on 02/08/15.
 */
public class AddressCreationRequest {

    @Valid
    @NotNull
    private Address address;

    public Address getAddress() {
        return address;
    }

    public AddressCreationRequest setAddress(Address address) {
        this.address = address;
        return this;
    }
}
