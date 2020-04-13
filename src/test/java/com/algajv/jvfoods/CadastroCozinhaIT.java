package com.algajv.jvfoods;


import com.algajv.jvfoods.domain.model.Cozinha;
import com.algajv.jvfoods.domain.repository.CozinhaRepository;
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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroCozinhaIT {

    @LocalServerPort
    private int port;

    @Autowired
    private CozinhaRepository repository;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    private int quantidadeCozinhas;
    private static final int idCozinhaInexistente = 900;
    private Cozinha cozinhaExistente;
    private String jsonPostCozinha;


    @Before
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port=port;
        RestAssured.basePath="/cozinhas";

        jsonPostCozinha = ResourceUtils.getContentFromResource("/body_CadastroCorretoCozinha.json");

        databaseCleaner.clearTables();
        prepararDados();
    }


    @Test
    public void shouldRetornarStatus200_whenConsultarCozinha() {

        RestAssured.given()
                    .accept(ContentType.JSON)
                .when()
                    .get()
                .then()
                    .statusCode(200);
    }

    // validando o CORPO da resposta
    @Test
    public void shouldTer2Cozinhas_whenConsultarCozinhas() {
        RestAssured.given()
                    .accept(ContentType.JSON)
                .when()
                    .get()
                .then()
                    .body("", Matchers.hasSize(quantidadeCozinhas));      // verificando se há 4 ocorrências de objetos
    }

    @Test
    public void shouldRetornar201_whenCadastrarCozinha() {
        RestAssured.given()
                    .body(jsonPostCozinha)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .post()
                .then()
                    .statusCode(201);

    }

    @Test
    public void shouldRetornarRespostaEStatusCorretos_whenConsultarCozinhaExistente() {
        RestAssured.given()
                    .pathParam("cozinhaId", cozinhaExistente.getId())
                    .accept(ContentType.JSON)
                .when()
                    .get("/{cozinhaId}")
                .then()
                    .statusCode(200)
                    .body("nome", Matchers.equalTo(cozinhaExistente.getNome()));

    }

    @Test
    public void shouldRetornar404_whenConsultarCozinhaInexistente() {
        RestAssured.given()
                .pathParam("cozinhaId", idCozinhaInexistente)
                .accept(ContentType.JSON)
                .when()
                .get("/{cozinhaId}")
                .then()
                .statusCode(404);

    }

    private void prepararDados() {
        Cozinha cozinha = new Cozinha();
        cozinha.setNome("Tailandesa");
        repository.save(cozinha);

        cozinhaExistente = new Cozinha();
        cozinhaExistente.setNome("Norueguesa");
        repository.save(cozinhaExistente);

        quantidadeCozinhas = repository.findAll().size();
    }

}
