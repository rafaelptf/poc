package br.com.manager.address.service;

import br.com.manager.address.domain.Address;
import br.com.manager.address.domain.AddressEntity;
import br.com.manager.address.domain.CompleteAddress;
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
import static org.mockito.Mockito.when;

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

        final CompleteAddress returnCompleteAddress = addressService.findById(addressId);
    }

}
