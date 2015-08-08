package br.com.manager.address.service;

import br.com.manager.address.domain.Address;
import br.com.manager.address.entity.AddressEntity;
import br.com.manager.address.domain.CompleteAddress;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by rpeixoto on 07/08/15.
 */
public class AddressConverterTest {

    private AddressConverter addressConverter;

    @Before
    public void setUp() {
        addressConverter = new AddressConverter();
    }

    @Test
    public void convertEntityToDomain() throws Exception {
        final String cep = "041041";
        final String street = "Faria Lima";
        final int number = 99;
        final String complement = "apto 50";
        final String district = "Jardins";
        final String city = "Sao Paulo";
        final String state = "SP";

        final AddressEntity addressEntity = buildAddressEntity(cep, street, number, complement, district, city, state);

        final CompleteAddress completeAddress = addressConverter.convertEntityToDomain(addressEntity);

        assertThat(completeAddress.getCep(), is(equalTo(cep)));
        assertThat(completeAddress.getStreet(), is(equalTo(street)));
        assertThat(completeAddress.getNumber(), is(equalTo(number)));
        assertThat(completeAddress.getComplement(), is(equalTo(complement)));
        assertThat(completeAddress.getDistrict(), is(equalTo(district)));
        assertThat(completeAddress.getCity(), is(equalTo(city)));
        assertThat(completeAddress.getState(), is(equalTo(state)));
    }

    @Test
    public void convertDomainToEntity() throws Exception {
        final String cep = "041041";
        final String street = "Faria Lima";
        final int number = 99;
        final String complement = "apto 50";
        final String district = "Jardins";
        final String city = "Sao Paulo";
        final String state = "SP";

        final Address address = buildAddress(cep, street, number, complement, district, city, state);

        final AddressEntity addressEntity = addressConverter.convertDomainToEntity(address);

        assertThat(addressEntity.getCep(), is(equalTo(cep)));
        assertThat(addressEntity.getStreet(), is(equalTo(street)));
        assertThat(addressEntity.getNumber(), is(equalTo(number)));
        assertThat(addressEntity.getComplement(), is(equalTo(complement)));
        assertThat(addressEntity.getDistrict(), is(equalTo(district)));
        assertThat(addressEntity.getCity(), is(equalTo(city)));
        assertThat(addressEntity.getState(), is(equalTo(state)));
    }

    private AddressEntity buildAddressEntity(String cep, String street, int number, String complement, String district, String city, String state) {
        final AddressEntity addressEntity = new AddressEntity();
        addressEntity.setCep(cep);
        addressEntity.setStreet(street);
        addressEntity.setNumber(number);
        addressEntity.setComplement(complement);
        addressEntity.setDistrict(district);
        addressEntity.setCity(city);
        addressEntity.setState(state);
        return addressEntity;
    }

    private Address buildAddress(String cep, String street, int number, String complement, String district, String city, String state) {
        final Address address = new Address();
        address.setCep(cep);
        address.setStreet(street);
        address.setNumber(number);
        address.setComplement(complement);
        address.setDistrict(district);
        address.setCity(city);
        address.setState(state);
        return address;
    }


}
