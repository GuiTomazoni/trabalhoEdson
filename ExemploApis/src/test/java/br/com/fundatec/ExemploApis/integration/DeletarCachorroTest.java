package br.com.fundatec.ExemploApis.integration;

import org.apache.http.HttpHeaders;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.fundatec.ExemploApis.entity.Cachorro;
import br.com.fundatec.ExemploApis.entity.PorteParametro;
import br.com.fundatec.ExemploApis.repository.CachorroRepository;
import br.com.fundatec.ExemploApis.repository.PorteParametroRepository;
import io.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeletarCachorroTest {

	@LocalServerPort
	private int port;
	@Autowired
	private CachorroRepository cachorroRepository;
	@Autowired
	private PorteParametroRepository porteParametroRepository;
	
	@Before
	public void setup() {
		RestAssured.port = port;
		RestAssured.baseURI = "http://localhost";
		cachorroRepository.deleteAll();
		porteParametroRepository.deleteAll();
		porteParametroRepository.save(new PorteParametro("Pequeno"));
		porteParametroRepository.save(new PorteParametro("Médio"));
		porteParametroRepository.save(new PorteParametro("Grande"));
	}
	
	@Test
	public void deveDeletarCachorro() {
		Cachorro cachorroParaDeletar;
		cachorroParaDeletar = new Cachorro(null, "Tobias", "Beagle", "Pequeno", 2);
		cachorroParaDeletar = cachorroRepository.save(cachorroParaDeletar);
		
		RestAssured
		.given()
		.header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
		.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
		.when()
		.delete("/v1/cachorros/{id}", cachorroParaDeletar.getId())
		.then()
		.assertThat()
		.statusCode(HttpStatus.OK.value());
		
		Assert.assertTrue(cachorroRepository.count() == 0);
	}
	
	@Test
	public void deveFalharAoDeletarCachorroInexistente() {
		RestAssured
		.given()
		.header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
		.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
		.when()
		.delete("/v1/cachorros/{id}", 7)
		.then()
		.assertThat()
		.statusCode(HttpStatus.NOT_FOUND.value())
		.body("mensagem", Matchers.equalTo("Não existe cachorro com este id: 7"));
	}
}
