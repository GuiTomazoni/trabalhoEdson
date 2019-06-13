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
import br.com.fundatec.ExemploApis.entity.PorteParametro;
import br.com.fundatec.ExemploApis.repository.CachorroRepository;
import br.com.fundatec.ExemploApis.repository.PorteParametroRepository;
import io.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ConsultarCachorroTest {

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
	}
	
	@Test
	public void deveRetornarUmCachorro() {
		Cachorro cachorro = new Cachorro(null, " Son Goku", "Sayajin Dog", "Grande", 4);
		cachorroRepository.save(cachorro);
		
		RestAssured
		.when()
		.get("/v1/cachorros/{id}", cachorro.getId())
		.then()
		.assertThat()
		.statusCode(HttpStatus.OK.value())
		.body("id", Matchers.equalTo(cachorro.getId().intValue()))
		.body("nome", Matchers.equalTo(cachorro.getNome()))
		.body("raca", Matchers.equalTo(cachorro.getRaca()))
		.body("porte", Matchers.equalTo(cachorro.getPorte()))
		.body("idade", Matchers.equalTo(cachorro.getIdade()));
		
	}
	
	@Test
	public void deveRetornarErroAoConsultarCachorroInexistente() {
		RestAssured
		.when()
		.get("/v1/cachorros/{id}", 1903)
		.then()
		.assertThat()
		.statusCode(HttpStatus.NOT_FOUND.value())
		.body("mensagem", Matchers.equalTo("Não encontrou cachorro para o id: 1903"));
		
		
	}
}
