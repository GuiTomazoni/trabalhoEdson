package br.com.fundatec.ExemploApis.integration;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.fundatec.ExemploApis.entity.Cachorro;
import br.com.fundatec.ExemploApis.repository.CachorroRepository;
import io.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ListarCachorroTest {
	@LocalServerPort
	private int port;
	
	@Autowired
	private CachorroRepository cachorroRepository;
	
	@Before
	public void setUp() {
		RestAssured.port = port;
		RestAssured.baseURI = "http://localhost";
	}
	
	@Test
	public void deveBuscarUmaListaDeCachorros() {
		cachorroRepository.save(new Cachorro( "Goku", "Vira-lata", "Grande", 3));
		
		RestAssured
		.given()
		.when()
		.get("/v1/cachorros")
		.then()
		.body("nome", Matchers.hasItems("Goku"))
		.body("raca", Matchers.hasItems("Vira-lata"))
		.body("porte", Matchers.hasItems("Grande"))
		.body("idade", Matchers.hasItems(3))
		.statusCode(HttpStatus.OK.value());
	}
}
