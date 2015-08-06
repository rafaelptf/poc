package br.com.correios.cep.integration.service;

import br.com.correios.cep.api.exception.CepNotFoundException;
import br.com.correios.cep.api.service.CepSearchServiceImpl;
import br.com.correios.cep.integration.domain.CepSearchDetails;
import br.com.correios.common.util.StringHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by rpeixoto on 03/08/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class CepSearchServiceImplTest {

    @Spy
    private StringHelper stringHelper = new StringHelper();

    @Mock
    private CorreiosIntegrationService correiosIntegrationService;

    @InjectMocks
    private CepSearchServiceImpl cepSearchService;

    @Test
    public void findCepDetailsWithCorrectCep() throws Exception {
        final String cepNumber = "04117181";

        final CepSearchDetails firstCepSearchDetails = new CepSearchDetails("04117180", "Rua", "Bairro", "Cidade", "Estado");

        when(correiosIntegrationService.findCepDetails(cepNumber)).thenReturn(firstCepSearchDetails);
        final CepSearchDetails cepDetails = cepSearchService.findCepDetails(cepNumber);

        assertThat(cepDetails, is(equalTo(firstCepSearchDetails)));
    }

    @Test
    public void findCepDetailsWithNoStreetForFirstSearch() throws Exception {
        final String cepNumber = "04117181";

        final CepNotFoundException cepNotFoundException = new CepNotFoundException("CEP nao encontrado", "00000-000");
        final CepSearchDetails secondCepSearchDetails = new CepSearchDetails("04117180", "Rua", "Bairro", "Cidade", "Estado");

        when(correiosIntegrationService.findCepDetails(cepNumber)).thenThrow(cepNotFoundException);
        when(correiosIntegrationService.findCepDetails("04117180")).thenReturn(secondCepSearchDetails);

        final CepSearchDetails cepDetails = cepSearchService.findCepDetails(cepNumber);

        Mockito.verify(stringHelper).rightReplaceWithZeroFirstNonZeroChar(cepNumber);

        assertThat(cepDetails, is(equalTo(secondCepSearchDetails)));
    }

    @Test
    public void findCepDetailsWithNoStreet() throws Exception {
        final String cepNumber = "421";
        final CepNotFoundException cepNotFoundException = new CepNotFoundException("CEP nao encontrado", "00000-000");

        when(correiosIntegrationService.findCepDetails(cepNumber)).thenThrow(cepNotFoundException);
        when(correiosIntegrationService.findCepDetails("420")).thenThrow(cepNotFoundException);
        when(correiosIntegrationService.findCepDetails("400")).thenThrow(cepNotFoundException);

        CepNotFoundException cepNotFoundExceptionResult = null;
        try {
            final CepSearchDetails cepDetails = cepSearchService.findCepDetails(cepNumber);
        } catch (CepNotFoundException e) {
            cepNotFoundExceptionResult = e;
        }

        Mockito.verify(stringHelper).rightReplaceWithZeroFirstNonZeroChar(cepNumber);
        Mockito.verify(stringHelper).rightReplaceWithZeroFirstNonZeroChar("420");
        Mockito.verify(stringHelper).rightReplaceWithZeroFirstNonZeroChar("400");

        assertThat(cepNotFoundExceptionResult, is(notNullValue()));
        assertThat(cepNotFoundExceptionResult.getMessage(), is(equalTo("Cep nao encontrado apos todas tentativas")));
    }
}
