package br.com.manager.address.service;

import br.com.manager.address.domain.Address;
import br.com.manager.address.domain.AddressEntity;
import br.com.manager.address.domain.CompleteAddress;
import br.com.manager.address.exception.AddressNotFoundException;
import br.com.manager.address.exception.InvalidCepException;
import br.com.manager.address.repository.AddressRepository;
import br.com.manager.cep.integration.service.CepIntegrationService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private CepIntegrationService cepIntegrationService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompleteAddress createNewAddress(Address address) {

        validateCep(address.getCep());

        final AddressEntity addressEntity = convertAddressToAddressEntity(address);
        addressRepository.save(addressEntity);
        return new CompleteAddress(address, addressEntity.getId());
    }

    private void validateCep(String cep) {
        final boolean validCep = cepIntegrationService.isValidCep(cep);
        if(!validCep) {
            throw new InvalidCepException("Cep invalido.", cep);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompleteAddress findById(Long addressId) {
        final AddressEntity addressEntity = findActiveAddress(addressId);
        final CompleteAddress address = convertAddressEntityToAddress(addressEntity);
        return address;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<CompleteAddress> findAll() {
        final Pageable pageable = new PageRequest(0, 10);
        final List<AddressEntity> addresses = addressRepository.findAllActive(pageable);

        final List<CompleteAddress> completeAddresses = convertAddressEntitytoAddress(addresses);
        return completeAddresses;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, noRollbackFor = AddressNotFoundException.class)
    public boolean removeAddress(Long addressId) {
        final AddressEntity addressEntity = findActiveAddress(addressId);

        addressEntity.setActive(false);
        addressRepository.save(addressEntity);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, noRollbackFor = AddressNotFoundException.class)
    public boolean updateAddress(Long addressId, Address address) {
        final AddressEntity addressEntity = findActiveAddress(addressId);

        //Valida se o novo CEP é válido
        final boolean isSameCep = StringUtils.equalsIgnoreCase(addressEntity.getCep(), address.getCep());
        if(!isSameCep) {
            validateCep(address.getCep());
        }

        //Atualiza os campos do endereço
        addressEntity.setCep(address.getCep());
        addressEntity.setStreet(address.getStreet());
        addressEntity.setNumber(address.getNumber());
        addressEntity.setComplement(address.getComplement());
        addressEntity.setDistrict(address.getDistrict());
        addressEntity.setCity(address.getCity());
        addressEntity.setState(address.getState());
        addressRepository.save(addressEntity);
        return true;
    }

    private AddressEntity findActiveAddress(Long addressId) {
        final AddressEntity activeAddress = addressRepository.findActive(addressId);
        if(activeAddress == null) {
            throw new AddressNotFoundException("Endereco nao encontrado", addressId);
        }

        return activeAddress;
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
        addressEntity.setActive(true);
        return addressEntity;
    }
}


