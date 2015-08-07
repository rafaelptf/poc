package br.com.manager.address.domain;

import br.com.manager.common.domain.WsResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by rpeixoto on 02/08/15.
 */
@JsonIgnoreProperties
public class Address extends WsResponse{

    private String cep;
    private String street;
    private Integer number;
    private String complement;
    private String district;
    private String city;
    private String state;

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

    public Address setStreet(String street) {
        this.street = street;
        return this;
    }

    @NotNull
    public Integer getNumber() {
        return number;
    }

    public Address setNumber(Integer number) {
        this.number = number;
        return this;
    }

    public String getComplement() {
        return complement;
    }

    public Address setComplement(String complement) {
        this.complement = complement;
        return this;
    }

    public String getDistrict() {
        return district;
    }

    public Address setDistrict(String district) {
        this.district = district;
        return this;
    }

    @NotBlank
    public String getCity() {
        return city;
    }

    public Address setCity(String city) {
        this.city = city;
        return this;
    }

    @NotBlank
    public String getState() {
        return state;
    }

    public Address setState(String state) {
        this.state = state;
        return this;
    }
}
