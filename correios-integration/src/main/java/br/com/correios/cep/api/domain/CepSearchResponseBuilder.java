package br.com.correios.cep.api.domain;

public class CepSearchResponseBuilder {

    private String cep;
    private String street;
    private String district;
    private String city;
    private String state;

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
        return new CepSearchResponse(cep, street, district, city, state);
    }
}
