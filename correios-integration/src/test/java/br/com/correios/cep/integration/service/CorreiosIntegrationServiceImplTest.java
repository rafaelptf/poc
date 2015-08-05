package br.com.correios.cep.integration.service;

import br.com.correios.cep.api.exception.CepNotFoundException;
import br.com.correios.cep.integration.domain.CepSearchDetails;
import br.com.correios.cep.integration.domain.WsCorreriosCepSearchResponse;
import br.com.correios.common.util.RestTemplateWrapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Map;

import static org.hamcrest.Matchers.*;

/**
 * Created by rpeixoto on 03/08/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class CorreiosIntegrationServiceImplTest {

    @Mock
    private RestTemplateWrapper restTemplateWrapper;

    @InjectMocks
    private CorreiosIntegrationServiceImpl correiosIntegrationService;

    @Test(expected = CepNotFoundException.class)
    public void findCepDetailsWithInvalidCepShouldThrowException() throws Exception {

        final String cep = "0402050";
        final Map<String, String> vars = Collections.singletonMap("cep", cep);

        Mockito.when(restTemplateWrapper.getForObject("http://api.postmon.com.br/v1/cep/{cep}",
                WsCorreriosCepSearchResponse.class, vars))
                .thenReturn(null);

        correiosIntegrationService.findCepDetails(cep);
    }

    @Test
    public void findCepDetailsWithValidCepShouldReturnCepDetails() throws Exception {

        final String cep = "0402050";
        final Map<String, String> vars = Collections.singletonMap("cep", cep);

        final String street = "RUA";
        final String district = "BAIRRO";
        final String city = "CIDADE";
        final String state = "ESTADO";

        final WsCorreriosCepSearchResponse wsCorreriosCepSearchResponse = new WsCorreriosCepSearchResponse();
        wsCorreriosCepSearchResponse.setCep(cep);
        wsCorreriosCepSearchResponse.setLogradouro(street);
        wsCorreriosCepSearchResponse.setBairro(district);
        wsCorreriosCepSearchResponse.setCidade(city);
        wsCorreriosCepSearchResponse.setEstado(state);

        Mockito.when(restTemplateWrapper.getForObject("http://api.postmon.com.br/v1/cep/{cep}",
                WsCorreriosCepSearchResponse.class, vars))
                .thenReturn(wsCorreriosCepSearchResponse);

        final CepSearchDetails cepDetails = correiosIntegrationService.findCepDetails(cep);

        Assert.assertThat(cepDetails, is(notNullValue()));
        Assert.assertThat(cepDetails.getCep(), is(equalTo(cep)));
        Assert.assertThat(cepDetails.getStreet(), is(equalTo(street)));
        Assert.assertThat(cepDetails.getDistrict(), is(equalTo(district)));
        Assert.assertThat(cepDetails.getCity(), is(equalTo(city)));
        Assert.assertThat(cepDetails.getState(), is(equalTo(state)));


    }
}
