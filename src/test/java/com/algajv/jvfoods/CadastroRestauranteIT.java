package com.algajv.jvfoods;


import com.algajv.jvfoods.domain.model.Cozinha;
import com.algajv.jvfoods.domain.model.Restaurante;
import com.algajv.jvfoods.domain.repository.CozinhaRepository;
import com.algajv.jvfoods.domain.repository.RestauranteRepository;
import com.algajv.jvfoods.util.DatabaseCleaner;
import com.algajv.jvfoods.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroRestauranteIT {

    private static final String ERRO_PROBLEM_TYPE = "Violação da regra de negócio";
    private static final String ERRO_DADOS_INVALIDOS = "Dados inválidos";

    @LocalServerPort
    private int port;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private RestauranteRepository repository;

    @Autowired
    private DatabaseCleaner databaseCleaner;


    private static final int idRestauranteInexistente = 900;
    private String jsonPostRestauranteCorreto;
    private String jsonPostRestauranteSemFrete;
    private String jsonPostRestauranteSemCozinha;
    private String jsonPostRestauranteComCozinhaInexistente;

    private Restaurante restauranteExistente;



    @Before
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port=port;
        RestAssured.basePath="/restaurantes";

        jsonPostRestauranteCorreto = ResourceUtils.getContentFromResource("/body_CadastroCorretoRestaurante.json");
        jsonPostRestauranteSemFrete = ResourceUtils.getContentFromResource("/body_CadastroRestaurante-SemTaxaFrete.json");
        jsonPostRestauranteSemCozinha = ResourceUtils.getContentFromResource("/body_CadastroRestaurante-SemCozinha.json");
        jsonPostRestauranteComCozinhaInexistente = ResourceUtils.getContentFromResource("/body_CadastroRestaurante-CozinhaInexistente.json");


        databaseCleaner.clearTables();
        prepararDados();
    }


    @Test
    public void shouldRetornarStatus200_whenConsultarRestaurantes() {
        RestAssured.given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void shouldRetornarStatus201_whenCadastrarRestaurante() {
        RestAssured.given()
                .body(jsonPostRestauranteCorreto)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void shouldRetornarStatus400_whenCadastrarRestauranteSemTaxaFrete() {
        RestAssured.given()
                .body(jsonPostRestauranteSemFrete)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("title", Matchers.equalTo(ERRO_DADOS_INVALIDOS));
    }

    @Test
    public void shouldRetornarStatus400_whenCadastrarRestauranteSemCozinha() {
        RestAssured.given()
                .body(jsonPostRestauranteSemCozinha)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("title", Matchers.equalTo(ERRO_DADOS_INVALIDOS));
    }

    @Test
    public void shouldRetornarStatus400_whenCadastrarRestauranteComCozinhaInexistente() {
        RestAssured.given()
                .body(jsonPostRestauranteComCozinhaInexistente)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("title", Matchers.equalTo(ERRO_PROBLEM_TYPE));
    }

    @Test
    public void shouldRetornarRespostaEStatusCorretos_whenConsultarRestauranteExistente() {
        RestAssured.given()
                .pathParam("restauranteId", restauranteExistente.getId())
                .accept(ContentType.JSON)
                .when()
                .get("/{restauranteId}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("nome", Matchers.equalTo(restauranteExistente.getNome()));
    }

    @Test
    public void shouldRetornarStatus404_whenConsultarRestauranteInexistente() {
        RestAssured.given()
                .pathParam("restauranteId", idRestauranteInexistente)
                .accept(ContentType.JSON)
                .when()
                .get("/{restauranteId}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    private void prepararDados() {
        Cozinha cozinhaBrasileira = new Cozinha();
        cozinhaBrasileira.setNome("Brasileira");
        cozinhaRepository.save(cozinhaBrasileira);

        Cozinha cozinhaAmericana = new Cozinha();
        cozinhaAmericana.setNome("Americana");
        cozinhaRepository.save(cozinhaAmericana);

        restauranteExistente = new Restaurante();
        restauranteExistente.setNome("Burger Top");
        restauranteExistente.setTaxaFrete(new BigDecimal(10));
        restauranteExistente.setCozinha(cozinhaAmericana);
        repository.save(restauranteExistente);

        Restaurante comidaMineiraRestaurante = new Restaurante();
        comidaMineiraRestaurante.setNome("Comida Mineira");
        comidaMineiraRestaurante.setTaxaFrete(new BigDecimal(10));
        comidaMineiraRestaurante.setCozinha(cozinhaBrasileira);
        repository.save(comidaMineiraRestaurante);
    }

}
