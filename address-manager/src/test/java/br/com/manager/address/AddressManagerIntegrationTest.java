package br.com.manager.address;

import br.com.manager.AddressManagerMainApp;
import br.com.manager.address.domain.Address;
import br.com.manager.address.domain.AddressCreationResponse;
import br.com.manager.address.domain.UpdateAddress;
import br.com.manager.address.entity.AddressEntity;
import br.com.manager.address.repository.AddressRepository;
import br.com.manager.common.domain.FieldValidationError;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by rpeixoto on 07/08/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AddressManagerMainApp.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class AddressManagerIntegrationTest {

    @Value("${local.server.port}")
    private int port;

    @Autowired
    private AddressRepository addressRepository;

    @Before
    public void setUp() {
        // Adicionando para onde o RestAssured deve apontar as requisições
        RestAssured.port = port;

        //Limpando o banco embarcado
        addressRepository.deleteAll();
    }

    @Test
    public void createNewAddressWithInvalidCep() {
        final Address address = new Address();
        address.setCep("041400500");
        address.setStreet("Faria Lima");
        address.setNumber(100);
        address.setCity("Sao Paulo");
        address.setState("SP");

        final Response post = given().contentType(ContentType.JSON)
                .body(address)
                .when()
                .post("/address");

        final AddressCreationResponse as = post.andReturn().body().as(AddressCreationResponse.class);


        final FieldValidationError[] fieldValidationErrors = as.getFieldValidationErrors();

        assertThat(as.getCode(), is(4001l));
        assertThat(fieldValidationErrors, notNullValue());
        assertThat(fieldValidationErrors[0].getFieldName(), is("cep"));
    }

    @Test
    public void findInvalidAddress() {
        final long addressId = 1;

        final Response get = given().contentType(ContentType.JSON)
                .when()
                .get("/address/{addressId}", addressId);

        validateDefaultAddressNotFoundResponse(get);
    }

    @Test
    public void findValidAddress() {

        final String street = "Faria Lima";
        final int number = 100;
        final String cep = "04140050";
        final String city = "Sao Paulo";
        final String state = "SP";

        //Gera uma instancia de endereço diretamente no banco de dados
        final AddressEntity addressEntity = buildAndSaveAddressEntity(street, number, cep, city, state);

        given().contentType(ContentType.JSON)
                .when()
                .get("/address/{addressId}", addressEntity.getId())
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("code", is(2004))
                .body("street", is(street))
                .body("number", is(number))
                .body("city", is(city))
                .body("state", is(state));
    }

    @Test
    public void updateInvalidAddress() {
        final long addressId = 1;

        final UpdateAddress updateAddress = new UpdateAddress();
        updateAddress.setCep("04040040");

        final Response put = given().contentType(ContentType.JSON)
                .body(updateAddress)
                .when()
                .put("/address/{addressId}", addressId);

        validateDefaultAddressNotFoundResponse(put);
    }

    @Test
    public void updateValidAddressWithNewStateOnly() {

        final String street = "Faria Lima";
        final int number = 100;
        final String cep = "04140050";
        final String city = "Sao Paulo";
        final String state = "SP";

        //Gera uma instancia de endereço diretamente no banco de dados
        final AddressEntity addressEntity = buildAndSaveAddressEntity(street, number, cep, city, state);

        //Atualizando somente estado
        final String newState = "RJ";
        final UpdateAddress updateAddress = new UpdateAddress();
        updateAddress.setState(newState);

        given().contentType(ContentType.JSON)
                .body(updateAddress)
                .when()
                .put("/address/{addressId}", addressEntity.getId())
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("code", is(2003));

        final AddressEntity active = addressRepository.findActive(addressEntity.getId());
        assertThat(active, is(notNullValue()));
        assertThat(active.getCep(), is(cep));
        assertThat(active.getStreet(), is(street));
        assertThat(active.getNumber(), is(number));
        assertThat(active.getCity(), is(city));
        assertThat(active.getState(), is(newState));
    }

    @Test
    public void deleteInvalidAddress() {
        final long addressId = 1;

        final Response delete = given().contentType(ContentType.JSON)
                .when()
                .delete("/address/{addressId}", addressId);

        validateDefaultAddressNotFoundResponse(delete);
    }

    @Test
    public void deleteValidAddress() {

        final String street = "Faria Lima";
        final int number = 100;
        final String cep = "04140050";
        final String city = "Sao Paulo";
        final String state = "SP";

        //Gera uma instancia de endereço diretamente no banco de dados
        final AddressEntity addressEntity = buildAndSaveAddressEntity(street, number, cep, city, state);

        given().contentType(ContentType.JSON)
                .when()
                .delete("/address/{addressId}", addressEntity.getId())
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("code", is(2002));


        final AddressEntity active = addressRepository.findActive(addressEntity.getId());
        assertThat(active, is(nullValue()));
    }

    private AddressEntity buildAndSaveAddressEntity(String street, int number, String cep, String city, String state) {
        final AddressEntity addressEntity = new AddressEntity();
        addressEntity.setActive(true);
        addressEntity.setCep(cep);
        addressEntity.setStreet(street);
        addressEntity.setNumber(number);
        addressEntity.setCity(city);
        addressEntity.setState(state);
        addressRepository.saveAndFlush(addressEntity);
        return addressEntity;
    }

    private void validateDefaultAddressNotFoundResponse(Response reponse) {
        reponse.then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .and()
                .body("code", is(4004));
    }


}
