package br.com.manager.address.service;

import br.com.manager.address.domain.Address;
import br.com.manager.address.domain.UpdateAddress;
import br.com.manager.address.entity.AddressEntity;
import br.com.manager.address.domain.CompleteAddress;
import br.com.manager.address.domain.CompleteAddressList;
import br.com.manager.address.exception.AddressNotFoundException;
import br.com.manager.address.exception.InvalidCepException;
import br.com.manager.address.repository.AddressRepository;
import br.com.manager.cep.integration.service.CepIntegrationService;
import br.com.manager.common.util.FunctionUtil;
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

        //Obtem o endereco ativo
        final AddressEntity addressEntity = findActiveAddress(addressId);

        // Converte e retorna
        final CompleteAddress address = addressConverter.convertEntityToDomain(addressEntity);
        logger.debug("Endereco encontrado. addressId={} completeAddress={}", addressId, address);
        return address;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompleteAddressList findAll(Integer page) {
        logger.debug("Buscando todos enderecos. page={}", page);

        // Pagina a busca de enderecos
        final Pageable pageable = getSearchPage(page);
        final Slice<AddressEntity> pageSlice = addressRepository.findAllActive(pageable);

        //Obtem a pagina atual e verifica e converte
        final List<AddressEntity> addresses = pageSlice.getContent();
        final List<Address> completeAddresses = addressConverter.convertEntityToDomain(addresses);

        //Adiciona ao objeto de retorno
        final CompleteAddressList completeAddressList = new CompleteAddressList(completeAddresses, pageSlice.hasNext());
        logger.debug("Enderecos encontrados. completeAddressListSize={}", completeAddressList.getCompleteAddresses().size());
        return completeAddressList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, noRollbackFor = AddressNotFoundException.class)
    public boolean removeAddress(Long addressId) {
        logger.debug("Removendo endereco. addressId={}", addressId);

        //Obtem o endereco ativo
        final AddressEntity addressEntity = findActiveAddress(addressId);

        //Desativa ele
        addressEntity.setActive(false);

        //Atualiza ele no banco de dados e retorna que foi atualizado com sucesso
        addressRepository.save(addressEntity);

        logger.debug("Endereco removido com sucesso. addressId={}", addressId);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, noRollbackFor = AddressNotFoundException.class)
    public boolean updateAddress(Long addressId, UpdateAddress address) {
        logger.debug("Atualizando endereco. addressId={} address={}", addressId, address);

        //Obtem o endereco ativo
        final AddressEntity addressEntity = findActiveAddress(addressId);

        //Valida se o novo CEP é válido, caso tenha recebido cep
        if(StringUtils.isNotEmpty(address.getCep())) {
            final boolean isSameCep = StringUtils.equalsIgnoreCase(addressEntity.getCep(), address.getCep());
            if (!isSameCep) {
                validateCep(address.getCep());
            }
        }

        //Atualiza somente os campos do endereco que vieram preenchidos
        FunctionUtil.updateStringIfNotEmpty(address::getCep, addressEntity::setCep);
        FunctionUtil.updateStringIfNotEmpty(address::getStreet, addressEntity::setStreet);
        FunctionUtil.updateIntegerIfNotNull(address::getNumber, addressEntity::setNumber);
        FunctionUtil.updateStringIfNotEmpty(address::getComplement, addressEntity::setComplement);
        FunctionUtil.updateStringIfNotEmpty(address::getDistrict, addressEntity::setDistrict);
        FunctionUtil.updateStringIfNotEmpty(address::getCity, addressEntity::setCity);
        FunctionUtil.updateStringIfNotEmpty(address::getState, addressEntity::setState);

        //Atualiza o banco de dados e retorna
        addressRepository.save(addressEntity);

        logger.debug("Endereco atualizado com sucesso. addressId={}", addressId);
        return true;
    }

    /**
     * Busca o endereco ativo no banco de dados, caso nao encontre, retorna excecao indicado o erro
     *
     * @param addressId
     * @return
     */
    private AddressEntity findActiveAddress(Long addressId) {
        final AddressEntity activeAddress = addressRepository.findActive(addressId);
        if (activeAddress == null) {
            throw new AddressNotFoundException("Endereco nao encontrado", addressId);
        }

        return activeAddress;
    }

    /**
     * Obtem o objeto referente a paginacao
     *
     * @param page
     * @return
     */
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


