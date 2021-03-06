package br.com.manager.address.domain;

import br.com.manager.common.domain.WsResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by rpeixoto on 02/08/15.
 */
@JsonIgnoreProperties
public class Address extends WsResponse {

    protected String cep;
    protected String street;
    protected Integer number;
    protected String complement;
    protected String district;
    protected String city;
    protected String state;

    public Address() {
    }

    public Address(Address address) {
        this.cep = address.getCep();
        this.street = address.getStreet();
        this.number = address.getNumber();
        this.complement = address.getComplement();
        this.district = address.getDistrict();
        this.city = address.getCity();
        this.state = address.getState();
    }

    @NotBlank(message = "{cepSearchRequest.cep.empty}")
    @Pattern(regexp = "\\d{5}-?\\d{3}", message = "{cepSearchRequest.cep.pattern}")
    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = StringUtils.remove(cep, "-");
    }

    @NotBlank
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @NotNull
    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @NotBlank
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @NotBlank
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("cep", cep)
                .append("street", street)
                .append("number", number)
                .append("complement", complement)
                .append("district", district)
                .append("city", city)
                .append("state", state)
                .toString();
    }
}
