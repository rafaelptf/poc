package br.com.manager.address.service;

import br.com.manager.address.domain.*;
import br.com.manager.address.repository.AddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rpeixoto on 03/08/15.
 */
@Component
public class AddressServiceImpl implements AddressService {

    private final static Logger logger = LoggerFactory.getLogger(AddressServiceImpl.class);

    @Autowired
    private AddressRepository addressRepository;

    public CompleteAddress createNewAddress(AddressCreationRequest addressCreationRequest) {
        final Address address = addressCreationRequest.getAddress();
        final AddressEntity addressEntity = convertAddressToAddressEntity(address);
        addressRepository.save(addressEntity);
        return new CompleteAddress(address, addressEntity.getId());
    }

    @Override
    public CompleteAddress findById(Long addressId) {
        final AddressEntity addressEntity  = addressRepository.findOne(addressId);
        final CompleteAddress address = convertAddressEntityToAddress(addressEntity);
        return address;
    }

    @Override
    public List<CompleteAddress> findAll() {
        final Pageable pageable = new PageRequest(0, 10);
        final List<AddressEntity> addresses = addressRepository.findAll(pageable);

        final List<CompleteAddress> completeAddresses = convertAddressEntitytoAddress(addresses);
        return completeAddresses;
    }

    private CompleteAddress convertAddressEntityToAddress(AddressEntity addressEntity) {
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

    private List<CompleteAddress> convertAddressEntitytoAddress(List<AddressEntity> addresses) {
        final List<CompleteAddress> completeAddresses = new ArrayList<>(addresses.size());
        for (AddressEntity addressEntity : addresses) {
            final CompleteAddress completeAddress = convertAddressEntityToAddress(addressEntity);
            completeAddresses.add(completeAddress);
        }

        return completeAddresses;
    }

    private AddressEntity convertAddressToAddressEntity(Address address) {
        final AddressEntity addressEntity = new AddressEntity();
        addressEntity.setCep(address.getCep());
        addressEntity.setStreet(address.getStreet());
        addressEntity.setNumber(address.getNumber());
        addressEntity.setComplement(address.getComplement());
        addressEntity.setDistrict(address.getDistrict());
        addressEntity.setCity(address.getCity());
        addressEntity.setState(address.getState());
        return addressEntity;
    }
}


