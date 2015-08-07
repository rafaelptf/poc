package br.com.correios.cep.api.domain;

import br.com.correios.common.json.BaseResponse;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by rpeixoto on 02/08/15.
 */
public class CepSearchResponse extends BaseResponse {

    private final String cep;
    private final String street;
    private final String district;
    private final String city;
    private final String state;

    public CepSearchResponse(Long code, String message, String cep, String street, String district, String city, String state) {
        super(code, message);
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
}
