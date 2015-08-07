package br.com.manager.address.exception;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by rpeixoto on 06/08/15.
 */
public class AddressNotFoundException extends RuntimeException{

    private final Long addressId;

    public AddressNotFoundException(String message, Long addressId) {
        super(message);
        this.addressId = addressId;
    }

    public Long getAddressId() {
        return addressId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("message", getMessage())
                .append("addressId", addressId)
                .toString();
    }
}

