package br.com.correios.cep.api.domain;

public class CepSearchResponseBuilder {
    private boolean cepFound;
    private String searchResult;
    private String cep;
    private String street;
    private String district;
    private String city;
    private String state;

    public static CepSearchResponseBuilder cepNotFound(String cepNotFoundMessage) {
        return new CepSearchResponseBuilder()
                .setCepFound(false)
                .setSearchResult(cepNotFoundMessage);
    }

    public static CepSearchResponseBuilder cepFound(String cepFoundMessage) {
        return new CepSearchResponseBuilder()
                .setCepFound(true)
                .setSearchResult(cepFoundMessage);
    }

    public CepSearchResponseBuilder setCepFound(boolean cepFound) {
        this.cepFound = cepFound;
        return this;
    }

    public CepSearchResponseBuilder setSearchResult(String searchResult) {
        this.searchResult = searchResult;
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
        return new CepSearchResponse(cepFound, searchResult, cep, street, district, city, state);
    }
}
