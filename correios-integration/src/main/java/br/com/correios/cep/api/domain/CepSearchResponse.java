package br.com.correios.cep.api.domain;

import br.com.correios.common.domain.WsResponse;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by rpeixoto on 02/08/15.
 */
public class CepSearchResponse extends WsResponse{

    private final String cep;
    private final String street;
    private final String district;
    private final String city;
    private final String state;

    public CepSearchResponse(String cep, String street, String district, String city, String state) {
        this.cep = cep;
        this.street = street;
        this.district = district;
        this.city = city;
        this.state = state;
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
                .appendSuper(super.toString())
                .append("cep", cep)
                .append("street", street)
                .append("district", district)
                .append("city", city)
                .append("state", state)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        CepSearchResponse that = (CepSearchResponse) o;

        return new EqualsBuilder()
                .append(cep, that.cep)
                .append(street, that.street)
                .append(district, that.district)
                .append(city, that.city)
                .append(state, that.state)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(cep)
                .append(street)
                .append(district)
                .append(city)
                .append(state)
                .toHashCode();
    }
}
