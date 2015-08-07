package br.com.correios.cep.api.controller;

import br.com.correios.cep.api.domain.CepSearchResponse;
import br.com.correios.cep.api.exception.CepNotFoundException;
import br.com.correios.cep.api.service.CepSearchService;
import br.com.correios.cep.integration.domain.CepSearchDetails;
import br.com.correios.common.constants.WsResponseCode;
import br.com.correios.common.util.MessageSourceWrapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

/**
 * Created by rpeixoto on 03/08/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class CepSearchControllerTest {

    @InjectMocks
    private CepSearchController cepSearchController;

    @Mock
    private CepSearchService cepSearchService;

    @Mock
    private MessageSourceWrapper messageSourceWrapper;

    @Test
    public void findCepDetailsInvalidCep() throws Exception {
        final String cep = "04117180";

        when(cepSearchService.findCepDetails(cep)).thenThrow(new CepNotFoundException("CEP nao encontrado", cep));

        CepNotFoundException cepNotFoundException = null;
        try {
            cepSearchController.findCepDetails(cep, new MockHttpServletResponse());
        } catch (CepNotFoundException e) {
            cepNotFoundException = e;
        }

        Assert.assertThat(cepNotFoundException, is(notNullValue()));
        Assert.assertThat(cepNotFoundException.getCep(), is(equalTo(cep)));
    }

    @Test
    public void cepNotFoundHandlerInvalidCep() throws Exception {

        final String cepNotFoundMsg = "CEP nao encontrado";
        final CepNotFoundException ex = new CepNotFoundException(cepNotFoundMsg, "04040-050");

        when(messageSourceWrapper.getWsResponseMessage(WsResponseCode.CEP_NOT_FOUND)).thenReturn(cepNotFoundMsg);

        final CepSearchResponse cepSearchResponse = cepSearchController.cepNotFoundHandler(ex);

        Assert.assertThat(cepSearchResponse, is(notNullValue()));
        Assert.assertThat(cepSearchResponse.getCode(), is(equalTo(4003l)));
        Assert.assertThat(cepSearchResponse.getMessage(), is(equalTo(cepNotFoundMsg)));
    }

    @Test
    public void findCepDetailsValidCep() throws Exception {
        final String cep = "04117180";
        final String cepFounMsg = "CEP encontrado";

        final CepSearchDetails cepSearchDetails = new CepSearchDetails("00000-000", "Rua", "Bairro", "Cidade", "Estado");
        when(cepSearchService.findCepDetails(cep)).thenReturn(cepSearchDetails);
        when(messageSourceWrapper.getWsResponseMessage(WsResponseCode.CEP_FOUND)).thenReturn(cepFounMsg);

        final CepSearchResponse cepSearchResponse = cepSearchController.findCepDetails(cep, new MockHttpServletResponse());

        Assert.assertThat(cepSearchResponse, is(notNullValue()));
        Assert.assertThat(cepSearchResponse.getCode(), is(equalTo(2001l)));
        Assert.assertThat(cepSearchResponse.getMessage(), is(equalTo(cepFounMsg)));
        Assert.assertThat(cepSearchResponse.getCep(), is(equalTo("00000-000")));
        Assert.assertThat(cepSearchResponse.getStreet(), is(equalTo("Rua")));
        Assert.assertThat(cepSearchResponse.getDistrict(), is(equalTo("Bairro")));
        Assert.assertThat(cepSearchResponse.getCity(), is(equalTo("Cidade")));
        Assert.assertThat(cepSearchResponse.getState(), is(equalTo("Estado")));
    }
}
