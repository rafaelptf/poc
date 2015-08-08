package br.com.manager.address.service;

import br.com.manager.address.domain.Address;
import br.com.manager.address.domain.CompleteAddress;
import br.com.manager.address.domain.CompleteAddressList;

/**
 * Created by rpeixoto on 03/08/15.
 */
public interface AddressService {

    /**
     * Cria um novo endereco a partir de uma requisicao
     *
     * @param address Detalhes do endereço a ser inserido
     * @return Detalhes do endereco recem inserido, mais ID do banco de dados
     */
    CompleteAddress createNewAddress(Address address);

    /**
     * Busca um endereço ativo a partir do seu id
     *
     * @param addressId Id do endereço a ser consultado
     * @return Detalhes do endereço
     */
    Address findById(Long addressId);

    /**
     * Busca todos os enderecos disponveis no banco de dados, paginando a resposta.
     * Se nenhuma pagina for solicitada, entende que é a primeira página
     * O número de registros por página é definido no arquivo application.properties
     *
     * @param page Pagina solicitada
     * @return
     */
    CompleteAddressList findAll(Integer page);

    /**
     * Remove logicamente o registro. Tornando o inativo para a aplicacao
     *
     * @param addressId Id do endereco a ser removido
     * @return true, removido com sucesso
     *         false, caso contrario
     */
    boolean removeAddress(Long addressId);

    /**
     * Atualiza um endereco.
     *
     * Regras:
     *  So atualiza com sucesso caso o CEP novo seja válido
     *  Se nao alterou o CEP, nao valida novamente o mesmo, só valida o CEP se houve tentativa de alteraçao
     *
     * @param addressId Id do endereco a ser alterado
     * @param address dados do endereço para alteração
     * @return true, alterado com sucesso
     *         false, caso contrario
     */
    boolean updateAddress(Long addressId, Address address);
}
