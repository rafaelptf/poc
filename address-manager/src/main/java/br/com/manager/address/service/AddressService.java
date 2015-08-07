package br.com.manager.address.service;

import br.com.manager.address.domain.Address;
import br.com.manager.address.domain.AddressCreationRequest;
import br.com.manager.address.domain.AddressCreationResponse;
import br.com.manager.address.domain.CompleteAddress;

import java.util.List;

/**
 * Created by rpeixoto on 03/08/15.
 */
public interface AddressService {

    CompleteAddress createNewAddress(Address address);

    Address findById(Long addressId);

    List<CompleteAddress> findAll();

    boolean removeAddress(Long addressId);

    boolean updateAddress(Long addressId, Address address);
}
