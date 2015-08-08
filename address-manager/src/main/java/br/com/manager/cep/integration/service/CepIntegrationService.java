package br.com.manager.cep.integration.service;

/**
 * Created by rpeixoto on 05/08/15.
 */
public interface CepIntegrationService {

    /**
     * Valida o CEP utilizando o servico desenvolvido
     *
     * So retorna true caso o CEP seja encontrado em sua totalidade.
     * Exemplo:
     *  CEP: 04126-055 Nao valido, apesar do servico responder com sucesso o cep 04126-050
     *
     * @param cep
     * @return
     */
    boolean isValidCep(String cep);
}
