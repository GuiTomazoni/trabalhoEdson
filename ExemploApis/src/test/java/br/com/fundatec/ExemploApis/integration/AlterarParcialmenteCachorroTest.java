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
public class AlterarParcialmenteCachorroTest {

	@LocalServerPort
	private int port;
	@Autowired
	private CachorroRepository cachorroRepository;
	@Autowired
	private PorteParametroRepository porteParametroRepository;

	private Cachorro cachorroParaAlterar;

	@Before
	public void setup() {
		RestAssured.port = port;
		RestAssured.baseURI = "http://localhost";
		cachorroRepository.deleteAll();
		porteParametroRepository.deleteAll();
		porteParametroRepository.save(new PorteParametro("Pequeno"));
		porteParametroRepository.save(new PorteParametro("Médio"));
		porteParametroRepository.save(new PorteParametro("Grande"));
		cachorroParaAlterar = new Cachorro(null, "Tobias", "Beagle", "Pequeno", 2);
		cachorroParaAlterar = cachorroRepository.save(cachorroParaAlterar);
	}

	@Test
	public void deveAlterarIdadeCachorro() {
		RestAssured.given().header(HttpHeaders.ACCEPT, MediaType.APPLICATION_PROBLEM_JSON_VALUE)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PROBLEM_JSON_VALUE)
				.body("{" + 
						"	\"idade\": 6" +
						"}")
				.when()
				.patch("/v1/cachorros/{id}", cachorroParaAlterar.getId())
				.then()
				.assertThat()
				.statusCode(HttpStatus.OK.value())
				.body("id", Matchers.equalTo(cachorroParaAlterar.getId().intValue()))
				.body("nome", Matchers.equalTo("Tobias"))
				.body("raca", Matchers.equalTo("Beagle"))
				.body("porte", Matchers.equalTo("Pequeno"))
				.body("idade", Matchers.equalTo(6));

		Cachorro cachorroAlterado = cachorroRepository.findById(cachorroParaAlterar.getId()).orElse(null);
		Assert.assertEquals("Tobias", cachorroAlterado.getNome());
		Assert.assertEquals("Beagle", cachorroAlterado.getRaca());
		Assert.assertEquals("Pequeno", cachorroAlterado.getPorte());
		Assert.assertEquals(6, cachorroAlterado.getIdade());

	}

}