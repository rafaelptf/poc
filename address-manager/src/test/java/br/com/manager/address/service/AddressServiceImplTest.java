package br.com.manager.address.service;

import br.com.manager.address.domain.Address;
import br.com.manager.address.domain.CompleteAddress;
import br.com.manager.address.domain.UpdateAddress;
import br.com.manager.address.entity.AddressEntity;
import br.com.manager.address.exception.AddressNotFoundException;
import br.com.manager.address.exception.InvalidCepException;
import br.com.manager.address.repository.AddressRepository;
import br.com.manager.cep.integration.service.CepIntegrationService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * Created by rpeixoto on 07/08/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class AddressServiceImplTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private CepIntegrationService cepIntegrationService;

    @Mock
    private Converter<AddressEntity, Address> addressConverter;

    @InjectMocks
    private AddressServiceImpl addressService;

    @Test
    public void createNewAddressWithInvalidCep() throws Exception {

        final String cep = "04040050";
        final Address address = new Address();
        address.setCep(cep);

        when(cepIntegrationService.isValidCep(cep)).thenReturn(false);

        InvalidCepException invalidCepException = null;
        try {
            addressService.createNewAddress(address);
            Assert.fail("Deveria ter lancado excecao");
        } catch (InvalidCepException e) {
            invalidCepException = e;
        }

        assertThat(invalidCepException.getCep(), is(equalTo(cep)));
    }

    @Test
    public void createNewAddressWithValidCep() throws Exception {

        final String cep = "04040050";
        final Address address = new Address();
        address.setCep(cep);

        final AddressEntity addressEntity = new AddressEntity();
        addressEntity.setCep(cep);

        final AddressEntity returnAddressEntity = new AddressEntity();
        returnAddressEntity.setId(1l);

        when(cepIntegrationService.isValidCep(cep)).thenReturn(true);
        when(addressConverter.convertDomainToEntity(address)).thenReturn(addressEntity);
        when(addressRepository.save(addressEntity)).thenReturn(returnAddressEntity);

        final CompleteAddress newAddress = addressService.createNewAddress(address);

        verify(addressRepository).save(addressEntity);

        assertThat(newAddress, is(notNullValue()));
        assertThat(newAddress.getId(), is(equalTo(1l)));
        assertThat(newAddress.getCep(), is(equalTo(cep)));
        assertThat(newAddress.getStreet(), is(equalTo(address.getStreet())));
        assertThat(newAddress.getCity(), is(equalTo(address.getCity())));
        assertThat(newAddress.getState(), is(equalTo(address.getState())));
    }

    @Test
    public void findByIdWithCorrectAddressId() throws Exception {
        final long addressId = 1l;
        final AddressEntity addressEntity = new AddressEntity();
        final CompleteAddress completeAddress = new CompleteAddress();

        when(addressRepository.findActive(addressId)).thenReturn(addressEntity);
        when(addressConverter.convertEntityToDomain(addressEntity)).thenReturn(completeAddress);

        final CompleteAddress returnCompleteAddress = addressService.findById(addressId);
        assertThat(returnCompleteAddress, is(equalTo(completeAddress)));
    }

    @Test(expected = AddressNotFoundException.class)
    public void findByIdWithIncorrectAddressId() throws Exception {
        final long addressId = 5l;
        final AddressEntity addressEntity = new AddressEntity();
        final CompleteAddress completeAddress = new CompleteAddress();

        when(addressRepository.findActive(addressId)).thenReturn(null);
        when(addressConverter.convertEntityToDomain(addressEntity)).thenReturn(completeAddress);

        addressService.findById(addressId);
    }

    @Test
    public void removeAddressdWithCorrectAddressId() throws Exception {
        final long addressId = 1l;
        final AddressEntity addressEntity = new AddressEntity();
        addressEntity.setActive(true);

        when(addressRepository.findActive(addressId)).thenReturn(addressEntity);
        when(addressRepository.save(addressEntity)).thenReturn(addressEntity);

        final boolean isAddressRemoved = addressService.removeAddress(addressId);

        verify(addressRepository).save(addressEntity);

        assertThat(isAddressRemoved, is(equalTo(true)));
        assertThat(addressEntity.isActive(), is(equalTo(false)));
    }

    @Test(expected = AddressNotFoundException.class)
    public void removeAddressWithIncorrectAddressId() throws Exception {
        final long addressId = 5l;
        when(addressRepository.findActive(addressId)).thenReturn(null);

        addressService.removeAddress(addressId);
    }

    @Test
    public void updateAddressdWithCorrectAddressIdWithoutUpdatingCEP() throws Exception {
        final long addressId = 1l;
        final String cep = "040040050";
        final String newStreet = "FARIA LIMA";
        final int newNumber = 11;
        final String newDistrict = "JARDINS";
        final String newCity = "SAO PAULO";
        final String newState = "SP";

        final AddressEntity addressEntity = buildAddressEntity(cep, "RUA", 99, "BAIRRO", "CIDADE", "ESTADO");
        final UpdateAddress updateAddress = buildUpdateAddress(cep, newStreet, newNumber, newDistrict, newCity, newState);

        when(addressRepository.findActive(addressId)).thenReturn(addressEntity);
        when(addressRepository.save(addressEntity)).thenReturn(addressEntity);

        final boolean isAddressupdated = addressService.updateAddress(addressId, updateAddress);

        assertThat(isAddressupdated, is(equalTo(true)));
        assertThat(addressEntity.getCep(), is(equalTo(cep)));
        assertThat(addressEntity.getStreet(), is(equalTo(newStreet)));
        assertThat(addressEntity.getNumber(), is(equalTo(newNumber)));
        assertThat(addressEntity.getDistrict(), is(equalTo(newDistrict)));
        assertThat(addressEntity.getCity(), is(equalTo(newCity)));
        assertThat(addressEntity.getState(), is(equalTo(newState)));
    }

    @Test
    public void updateAddressdWithCorrectAddressIdAndValidCep() throws Exception {
        final long addressId = 1l;
        final String newCep = "040040050";
        final String newStreet = "FARIA LIMA";
        final int newNumber = 11;
        final String newDistrict = "JARDINS";
        final String newCity = "SAO PAULO";
        final String newState = "SP";

        final String entityCep = "00000000";

        final AddressEntity addressEntity = buildAddressEntity(entityCep, "RUA", 99, "BAIRRO", "CIDADE", "ESTADO");
        final UpdateAddress updateAddress = buildUpdateAddress(newCep, newStreet, newNumber, newDistrict, newCity, newState);

        when(addressRepository.findActive(addressId)).thenReturn(addressEntity);
        when(cepIntegrationService.isValidCep(newCep)).thenReturn(true);
        when(addressRepository.save(addressEntity)).thenReturn(addressEntity);

        final boolean isAddressUpdated = addressService.updateAddress(addressId, updateAddress);

        verify(cepIntegrationService, only()).isValidCep(newCep);
        verify(addressRepository).save(addressEntity);

        assertThat(isAddressUpdated, is(equalTo(true)));
        assertThat(addressEntity.getCep(), is(equalTo(newCep)));
        assertThat(addressEntity.getStreet(), is(equalTo(newStreet)));
        assertThat(addressEntity.getNumber(), is(equalTo(newNumber)));
        assertThat(addressEntity.getDistrict(), is(equalTo(newDistrict)));
        assertThat(addressEntity.getCity(), is(equalTo(newCity)));
        assertThat(addressEntity.getState(), is(equalTo(newState)));
    }

    @Test
    public void updateAddressOnlyCep() throws Exception {
        final long addressId = 1l;
        final String newCep = "040040050";
        final String entityCep = "00000000";

        final String street = "RUA";
        final int number = 99;
        final String district = "BAIRRO";
        final String city = "CIDADE";
        final String state = "ESTADO";

        final AddressEntity addressEntity = buildAddressEntity(entityCep, street, number, district, city, state);
        final UpdateAddress updateAddress = buildUpdateAddress(newCep, null, null, null, null, null);

        when(addressRepository.findActive(addressId)).thenReturn(addressEntity);
        when(cepIntegrationService.isValidCep(newCep)).thenReturn(true);
        when(addressRepository.save(addressEntity)).thenReturn(addressEntity);

        final boolean isAddressUpdated = addressService.updateAddress(addressId, updateAddress);

        verify(cepIntegrationService, only()).isValidCep(newCep);
        verify(addressRepository).save(addressEntity);

        assertThat(isAddressUpdated, is(equalTo(true)));
        assertThat(addressEntity.getCep(), is(equalTo(newCep)));
        assertThat(addressEntity.getStreet(), is(equalTo(street)));
        assertThat(addressEntity.getNumber(), is(equalTo(number)));
        assertThat(addressEntity.getDistrict(), is(equalTo(district)));
        assertThat(addressEntity.getCity(), is(equalTo(city)));
        assertThat(addressEntity.getState(), is(equalTo(state)));
    }

    @Test(expected = InvalidCepException.class)
    public void updateAddressdWithCorrectAddressIdAndInvalidCep() throws Exception {
        final long addressId = 1l;
        final String cep = "040040050";
        final String entityCep = "00000000";

        final AddressEntity addressEntity = buildAddressEntity(entityCep, "RUA", 99, "BAIRRO", "CIDADE", "ESTADO");
        final UpdateAddress address = buildUpdateAddress(cep, null, 0, null, null, null);

        when(addressRepository.findActive(addressId)).thenReturn(addressEntity);
        when(cepIntegrationService.isValidCep(cep)).thenReturn(false);

        addressService.updateAddress(addressId, address);

        verify(cepIntegrationService, only()).isValidCep(cep);
    }

    private AddressEntity buildAddressEntity(String cep, String street, int number, String district, String city, String state) {
        final AddressEntity addressEntity = new AddressEntity();
        addressEntity.setCep(cep);
        addressEntity.setStreet(street);
        addressEntity.setNumber(number);
        addressEntity.setDistrict(district);
        addressEntity.setCity(city);
        addressEntity.setState(state);
        return addressEntity;
    }

    private Address buildAddress(String cep, String street, int number, String district, String city, String state) {
        final Address address = new Address();
        address.setCep(cep);
        address.setStreet(street);
        address.setNumber(number);
        address.setDistrict(district);
        address.setCity(city);
        address.setState(state);
        return address;
    }

    private UpdateAddress buildUpdateAddress(String cep, String street, Integer number, String district, String city, String state) {
        final UpdateAddress address = new UpdateAddress();
        address.setCep(cep);
        address.setStreet(street);
        address.setNumber(number);
        address.setDistrict(district);
        address.setCity(city);
        address.setState(state);
        return address;
    }

    @Test(expected = AddressNotFoundException.class)
    public void updateAddressWithIncorrectAddressId() throws Exception {
        final long addressId = 5l;
        when(addressRepository.findActive(addressId)).thenReturn(null);

        addressService.updateAddress(addressId, new UpdateAddress());
    }

}
