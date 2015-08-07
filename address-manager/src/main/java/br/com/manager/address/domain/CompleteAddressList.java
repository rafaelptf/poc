package br.com.manager.address.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by rpeixoto on 02/08/15.
 */
@JsonIgnoreProperties
public class CompleteAddressList {

    private final List<Address> completeAddresses;
    private final boolean hasNextPage;

    public CompleteAddressList(List<Address> completeAddresses, boolean hasNextPage) {
        this.completeAddresses = completeAddresses;
        this.hasNextPage = hasNextPage;
    }

    public List<Address> getCompleteAddresses() {
        return completeAddresses;
    }

    public boolean hasNextPage() {
        return hasNextPage;
    }
}
