package br.com.manager.address.domain;

/**
 * Created by rpeixoto on 02/08/15.
 */
public class AddressRemoveResponse {

    private final String status;

    public AddressRemoveResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
