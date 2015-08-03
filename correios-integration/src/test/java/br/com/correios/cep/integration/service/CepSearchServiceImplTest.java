package br.com.correios.cep.integration.service;

import br.com.correios.cep.integration.domain.CepSearchDetails;
import br.com.correios.cep.api.service.CepSearchService;
import br.com.correios.util.StringHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.when;

/**
 * Created by rpeixoto on 03/08/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class CepSearchServiceImplTest {

    @Spy
    private StringHelper stringHelper = new StringHelper();

    @Mock
    private CorreiosIntegrationServiceImpl correiosResource;

    @InjectMocks
    private CepSearchService cepSearchService;

    @Test
    public void findCepDetailsWithCorrectCep() throws Exception {
        final String cepNumber = "04126181";

        final CepSearchDetails firstCepSearchDetails = new CepSearchDetails("04126180", "Rua", "Bairro", "Cidade", "Estado");

        when(correiosResource.findCepDetails(cepNumber)).thenReturn(firstCepSearchDetails);
        final CepSearchDetails cepDetails = cepSearchService.findCepDetails(cepNumber);

        assertThat(cepDetails, is(equalTo(firstCepSearchDetails)));
    }

    @Test
    public void findCepDetailsWithNoStreetForFirstSearch() throws Exception {
        final String cepNumber = "04126181";

        final CepSearchDetails firstCepSearchDetails = new CepSearchDetails(null, null, null, null, null);
        final CepSearchDetails secondCepSearchDetails = new CepSearchDetails("04126180", "Rua", "Bairro", "Cidade", "Estado");

        when(correiosResource.findCepDetails(cepNumber)).thenReturn(firstCepSearchDetails);
        when(correiosResource.findCepDetails("04126180")).thenReturn(secondCepSearchDetails);

        final CepSearchDetails cepDetails = cepSearchService.findCepDetails(cepNumber);

        Mockito.verify(stringHelper).rightReplaceWithZeros(cepNumber, 1);

        assertThat(cepDetails, is(equalTo(secondCepSearchDetails)));
    }

    @Test
    public void findCepDetailsWithNoStreet() throws Exception {
        final String cepNumber = "421";

        final CepSearchDetails firstCepSearchDetails = new CepSearchDetails(null, null, null, null, null);
        final CepSearchDetails secondCepSearchDetails = new CepSearchDetails(null, null, null, null, null);
        final CepSearchDetails thirdCepSearchDetails = new CepSearchDetails(null, null, null, null, null);

        when(correiosResource.findCepDetails(cepNumber)).thenReturn(firstCepSearchDetails);
        when(correiosResource.findCepDetails("420")).thenReturn(secondCepSearchDetails);
        when(correiosResource.findCepDetails("400")).thenReturn(thirdCepSearchDetails);

        final CepSearchDetails cepDetails = cepSearchService.findCepDetails(cepNumber);

        Mockito.verify(stringHelper).rightReplaceWithZeros(cepNumber, 1);
        Mockito.verify(stringHelper).rightReplaceWithZeros("420", 2);
        Mockito.verify(stringHelper).rightReplaceWithZeros("400", 3);

        assertThat(cepDetails, is(nullValue()));
    }
}
