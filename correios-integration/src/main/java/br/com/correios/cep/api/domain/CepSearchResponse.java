package br.com.correios.cep.api.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by rpeixoto on 02/08/15.
 */
public class CepSearchResponse {

    private final boolean cepFound;
    private final String searchResult;
    private final String cep;
    private final String street;
    private final String district;
    private final String city;
    private final String state;

    public CepSearchResponse(boolean cepFound, String searchResult, String cep, String street, String district, String city, String state) {
        this.cepFound = cepFound;
        this.searchResult = searchResult;
        this.cep = cep;
        this.street = street;
        this.district = district;
        this.city = city;
        this.state = state;
    }

    public String getSearchResult() {
        return searchResult;
    }

    public String getCep() {
        return cep;
    }

    public String getStreet() {
        return street;
    }

    public String getDistrict() {
        return district;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("cepFound", cepFound)
                .append("searchResult", searchResult)
                .append("cep", cep)
                .append("street", street)
                .append("district", district)
                .append("city", city)
                .append("state", state)
                .toString();
    }
}
