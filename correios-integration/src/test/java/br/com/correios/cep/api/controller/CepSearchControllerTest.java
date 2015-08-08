package br.com.correios.cep.api.controller;

import br.com.correios.cep.api.domain.CepSearchResponse;
import br.com.correios.cep.api.exception.CepNotFoundException;
import br.com.correios.cep.api.service.CepSearchService;
import br.com.correios.cep.integration.domain.CepSearchDetails;
import br.com.correios.common.constants.WsResponseCode;
import br.com.correios.common.domain.WsResponse;
import br.com.correios.common.util.MessageSourceWrapper;
import br.com.correios.common.util.WsResponseBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

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
    private WsResponseBuilder wsResponseBuilder;

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

        final CepNotFoundException ex = new CepNotFoundException("Cep nao encontrado", "04040-050");
        final WsResponse cepSearchResponse = cepSearchController.cepNotFoundHandler(ex);

        verify(wsResponseBuilder, only()).getNoContetyWsResponse(WsResponseCode.CEP_NOT_FOUND);
    }

    @Test
    public void findCepDetailsValidCep() throws Exception {
        final String cepFounMsg = "CEP encontrado";

        final String cep = "00000-000";
        final String street = "Rua";
        final String district = "Bairro";
        final String city = "Cidade";
        final String state = "Estado";

        final CepSearchDetails cepSearchDetails = new CepSearchDetails(cep, street, district, city, state);
        final CepSearchResponse expectedCepSearchResponse = new CepSearchResponse(cep, street, district, city, state);

        when(cepSearchService.findCepDetails(cep)).thenReturn(cepSearchDetails);
        when(wsResponseBuilder.getWsResponse(WsResponseCode.CEP_FOUND, expectedCepSearchResponse)).thenReturn(expectedCepSearchResponse);

        final CepSearchResponse cepSearchResponse = cepSearchController.findCepDetails(cep, new MockHttpServletResponse());

        verify(wsResponseBuilder, only()).getWsResponse(WsResponseCode.CEP_FOUND, expectedCepSearchResponse);

        Assert.assertThat(cepSearchResponse, is(notNullValue()));
        Assert.assertThat(cepSearchResponse.getCep(), is(equalTo(cep)));
        Assert.assertThat(cepSearchResponse.getStreet(), is(equalTo(street)));
        Assert.assertThat(cepSearchResponse.getDistrict(), is(equalTo(district)));
        Assert.assertThat(cepSearchResponse.getCity(), is(equalTo(city)));
        Assert.assertThat(cepSearchResponse.getState(), is(equalTo(state)));
    }
}
