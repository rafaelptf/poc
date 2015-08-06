package br.com.correios.cep.api.controller;

import br.com.correios.cep.api.domain.CepSearchResponse;
import br.com.correios.cep.api.exception.CepNotFoundException;
import br.com.correios.cep.api.service.CepSearchService;
import br.com.correios.cep.integration.domain.CepSearchDetails;
import br.com.correios.common.constants.MessageKey;
import br.com.correios.common.constants.WsErrors;
import br.com.correios.common.util.MessageHelper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.hamcrest.Matchers.*;

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
    private MessageHelper messageHelper;

    @Test
    public void findCepDetailsInvalidCep() throws Exception {
        final String cep = "04117180";
        final String cepNotFoundMsg = "CEP nao encontrado";

        Mockito.when(cepSearchService.findCepDetails(cep)).thenThrow(new CepNotFoundException("CEP nao encontrado", "00000-000"));
        Mockito.when(messageHelper.getWsErrorMessage(WsErrors.CEP_NOT_FOUND)).thenReturn(cepNotFoundMsg);

        final CepSearchResponse cepSearchResponse = cepSearchController.findCepDetails(cep, new MockHttpServletResponse());

        Assert.assertThat(cepSearchResponse, is(notNullValue()));
        Assert.assertThat(cepSearchResponse.isCepFound(), is(false));
        Assert.assertThat(cepSearchResponse.getSearchResult(), is(equalTo(cepNotFoundMsg)));
    }

    @Test
    public void findCepDetailsValidCep() throws Exception {
        final String cep = "04117180";
        final String cepFounMsg = "CEP encontrado";

        final CepSearchDetails cepSearchDetails = new CepSearchDetails("00000-000", "Rua", "Bairro", "Cidade", "Estado");
        Mockito.when(cepSearchService.findCepDetails(cep)).thenReturn(cepSearchDetails);
        Mockito.when(messageHelper.getMessage(MessageKey.CEP_FOUND)).thenReturn(cepFounMsg);

        final CepSearchResponse cepSearchResponse = cepSearchController.findCepDetails(cep, new MockHttpServletResponse());

        Assert.assertThat(cepSearchResponse, is(notNullValue()));
        Assert.assertThat(cepSearchResponse.isCepFound(), is(true));
        Assert.assertThat(cepSearchResponse.getSearchResult(), is(equalTo(cepFounMsg)));
        Assert.assertThat(cepSearchResponse.getCep(), is(equalTo("00000-000")));
        Assert.assertThat(cepSearchResponse.getStreet(), is(equalTo("Rua")));
        Assert.assertThat(cepSearchResponse.getDistrict(), is(equalTo("Bairro")));
        Assert.assertThat(cepSearchResponse.getCity(), is(equalTo("Cidade")));
        Assert.assertThat(cepSearchResponse.getState(), is(equalTo("Estado")));
    }
}
