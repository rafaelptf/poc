package br.com.correios.cep.api.domain;

import br.com.correios.common.constants.WsResponseCode;

public class CepSearchResponseBuilder {

    private Long code;
    private String message;
    private String cep;
    private String street;
    private String district;
    private String city;
    private String state;

    public static CepSearchResponseBuilder cepNotFound(String cepNotFoundMessage) {
        return new CepSearchResponseBuilder()
                .setCode(WsResponseCode.CEP_NOT_FOUND.getCode())
                .setMessage(cepNotFoundMessage);
    }

    public static CepSearchResponseBuilder cepFound(String cepFoundMessage) {
        return new CepSearchResponseBuilder()
                .setCode(WsResponseCode.CEP_FOUND.getCode())
                .setMessage(cepFoundMessage);
    }

    public CepSearchResponseBuilder setCode(Long code) {
        this.code = code;
        return this;
    }

    public CepSearchResponseBuilder setMessage(String message) {
        this.message = message;
        return this;
    }

    public CepSearchResponseBuilder setCep(String cep) {
        this.cep = cep;
        return this;
    }

    public CepSearchResponseBuilder setStreet(String street) {
        this.street = street;
        return this;
    }

    public CepSearchResponseBuilder setDistrict(String district) {
        this.district = district;
        return this;
    }

    public CepSearchResponseBuilder setCity(String city) {
        this.city = city;
        return this;
    }

    public CepSearchResponseBuilder setState(String state) {
        this.state = state;
        return this;
    }

    public CepSearchResponse build() {
        return new CepSearchResponse(code, message, cep, street, district, city, state);
    }
}
