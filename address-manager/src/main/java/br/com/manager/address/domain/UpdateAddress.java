package br.com.manager.address.domain;

import br.com.manager.common.domain.WsResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.Pattern;

/**
 * Created by rpeixoto on 06/08/15.
 */
@JsonIgnoreProperties
/**
 * Classe quase igual a Address, porem esta classe contem anotacoes de validacao de campos diferente
 */
public class UpdateAddress extends WsResponse {

    protected String cep;
    protected String street;
    protected Integer number;
    protected String complement;
    protected String district;
    protected String city;
    protected String state;

    public UpdateAddress() {
    }

    @Pattern(regexp = "\\d{5}-?\\d{3}", message = "{cepSearchRequest.cep.pattern}")
    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        if (StringUtils.isNotBlank(cep)) {
            this.cep = StringUtils.remove(cep, "-");
        }
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

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
