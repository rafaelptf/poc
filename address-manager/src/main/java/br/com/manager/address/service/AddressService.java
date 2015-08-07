package br.com.manager.address.service;

import br.com.manager.address.domain.Address;
import br.com.manager.address.domain.CompleteAddress;
import br.com.manager.address.domain.CompleteAddressList;

/**
 * Created by rpeixoto on 03/08/15.
 */
public interface AddressService {

    CompleteAddress createNewAddress(Address address);

    Address findById(Long addressId);

    CompleteAddressList findAll(Integer page);

    boolean removeAddress(Long addressId);

    boolean updateAddress(Long addressId, Address address);
}
