package br.com.manager.address.service;

import br.com.manager.address.domain.Address;
import br.com.manager.address.domain.AddressEntity;
import br.com.manager.address.domain.CompleteAddress;
import br.com.manager.address.domain.CompleteAddressList;
import br.com.manager.address.exception.AddressNotFoundException;
import br.com.manager.address.exception.InvalidCepException;
import br.com.manager.address.repository.AddressRepository;
import br.com.manager.cep.integration.service.CepIntegrationService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by rpeixoto on 03/08/15.
 */
@Component
public class AddressServiceImpl implements AddressService {

    private final static Logger logger = LoggerFactory.getLogger(AddressServiceImpl.class);

    @Value("${page.size}")
    private Integer pageSize;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CepIntegrationService cepIntegrationService;

    @Autowired
    private Converter<AddressEntity, Address> addressConverter;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompleteAddress createNewAddress(Address address) {
        logger.debug("Criando novo endereco. address={}", address);

        // Valida o CEP
        validateCep(address.getCep());

        //Converte para a entidade e salva
        final AddressEntity addressEntity = addressConverter.convertDomainToEntity(address);
        final AddressEntity savedAddress = addressRepository.save(addressEntity);

        // Loga e retorna
        final CompleteAddress completeAddress = new CompleteAddress(address, savedAddress.getId());
        logger.debug("Endereco inserido com sucesso. addressId={} completeAddress={}", completeAddress.getId(), completeAddress);
        return completeAddress;
    }

    /**
     * Valida o CEP utilizando o servico desenvolvido
     *
     * @param cep
     */
    private void validateCep(String cep) {
        final boolean validCep = cepIntegrationService.isValidCep(cep);
        if (!validCep) {
            throw new InvalidCepException("Cep invalido.", cep);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompleteAddress findById(Long addressId) {
        logger.debug("Buscando endereco pelo id. addressId={}", addressId);

        final AddressEntity addressEntity = findActiveAddress(addressId);
        final CompleteAddress address = addressConverter.convertEntityToDomain(addressEntity);

        logger.debug("Endereco encontrado. addressId={} completeAddress={}", addressId, address);
        return address;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompleteAddressList findAll(Integer page) {
        logger.debug("Buscando todos enderecos. page={}", page);

        final Pageable pageable = getSearchPage(page);
        final Slice<AddressEntity> pageSlice = addressRepository.findAllActive(pageable);

        final List<AddressEntity> addresses = pageSlice.getContent();
        final List<Address> completeAddresses = addressConverter.convertEntityToDomain(addresses);

        final CompleteAddressList completeAddressList = new CompleteAddressList(completeAddresses, pageSlice.hasNext());
        logger.debug("Enderecos encontrados. completeAddressListSize={}", completeAddressList.getCompleteAddresses().size());
        return completeAddressList;
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
        if (!isSameCep) {
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
        if (activeAddress == null) {
            throw new AddressNotFoundException("Endereco nao encontrado", addressId);
        }

        return activeAddress;
    }

    private Pageable getSearchPage(Integer page) {
        final int searchPage;
        if (page == null) {
            searchPage = 0;
        } else {
            searchPage = page - 1;
        }
        return new PageRequest(searchPage, pageSize);
    }
}


