package br.com.manager.address.service;

import br.com.manager.address.domain.Address;
import br.com.manager.address.domain.AddressEntity;
import br.com.manager.address.domain.CompleteAddress;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rpeixoto on 07/08/15.
 */
@Component
public class AddressConverter implements Converter<AddressEntity, Address>{


    @Override
    public CompleteAddress convertEntityToDomain(AddressEntity addressEntity) {
        final CompleteAddress address = new CompleteAddress();
        address.setId(addressEntity.getId());
        address.setCep(addressEntity.getCep());
        address.setStreet(addressEntity.getStreet());
        address.setNumber(addressEntity.getNumber());
        address.setComplement(addressEntity.getComplement());
        address.setDistrict(addressEntity.getDistrict());
        address.setCity(addressEntity.getCity());
        address.setState(addressEntity.getState());
        return address;
    }


    @Override
    public List<Address> convertEntityToDomain(List<AddressEntity> addresses) {
        final List<Address> completeAddresses = new ArrayList<>(addresses.size());
        for (AddressEntity addressEntity : addresses) {
            final CompleteAddress completeAddress = convertEntityToDomain(addressEntity);
            completeAddresses.add(completeAddress);
        }

        return completeAddresses;
    }

    @Override
    public <P extends Address> AddressEntity convertDomainToEntity(P address) {
        final AddressEntity addressEntity = new AddressEntity();
        addressEntity.setCep(address.getCep());
        addressEntity.setStreet(address.getStreet());
        addressEntity.setNumber(address.getNumber());
        addressEntity.setComplement(address.getComplement());
        addressEntity.setDistrict(address.getDistrict());
        addressEntity.setCity(address.getCity());
        addressEntity.setState(address.getState());
        addressEntity.setActive(true);
        return addressEntity;
    }
}


